package com.daqem.knot.mixin;

import com.daqem.knot.KnotMod;
import com.daqem.knot.event.KnotExplosionEvent;
import com.daqem.knot.event.common.KnotTickEvent;
import com.daqem.knot.event.lifecycle.KnotLevelLifecycleEvent;
import com.daqem.knot.event.server.KnotServersideTickEvent;
import com.daqem.knot.world.level.IServerLevel;
import com.daqem.knot.world.level.KnotScheduledTask;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntityGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProgressListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level implements ServerEntityGetter, WorldGenLevel, IServerLevel {

    @Unique
    private final Queue<KnotScheduledTask> knot$taskInbox = new ConcurrentLinkedQueue<>();

    @Unique
    private final PriorityQueue<KnotScheduledTask> knot$scheduledTasks = new PriorityQueue<>(Comparator.comparingLong(KnotScheduledTask::executionTime));

    protected MixinServerLevel(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Unique
    @Override
    public void knot$schedule(int delayTicks, Runnable action) {
        // We calculate the target time based on current game time + delay
        long targetTime = this.getGameTime() + Math.max(0, delayTicks);
        this.knot$taskInbox.add(new KnotScheduledTask(targetTime, action));
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void knot$preTick(CallbackInfo ci) {
        KnotServersideTickEvent.SERVER_LEVEL_PRE.invoker().tick((ServerLevel) (Object) this);
        KnotTickEvent.LEVEL_PRE.invoker().tick(this);

        // 1. Drain inbox to the schedule (Thread-safe transfer)
        KnotScheduledTask incoming;
        while ((incoming = this.knot$taskInbox.poll()) != null) {
            this.knot$scheduledTasks.add(incoming);
        }

        // 2. Run tasks that are due
        long currentTime = this.getGameTime();

        // While there are tasks, and the next task's time is now or in the past
        while (!this.knot$scheduledTasks.isEmpty() && this.knot$scheduledTasks.peek().executionTime() <= currentTime) {
            KnotScheduledTask task = this.knot$scheduledTasks.poll();
            if (task != null) {
                try {
                    task.action().run();
                } catch (Exception e) {
                    KnotMod.API.error("Error executing delayed task in ServerLevel", e);
                }
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void knot$postTick(CallbackInfo ci) {
        KnotServersideTickEvent.SERVER_LEVEL_POST.invoker().tick((ServerLevel) (Object) this);
        KnotTickEvent.LEVEL_POST.invoker().tick(this);
    }

    @Inject(method = "save", at = @At("HEAD"))
    private void knot$onServerLevelSave(ProgressListener progressListener, boolean flush, boolean skipSave, CallbackInfo ci) {
        KnotLevelLifecycleEvent.SERVER_LEVEL_SAVE.invoker().onServerLevelSave((ServerLevel) (Object) this);
    }

    @Inject(
            method = "explode",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ServerExplosion;explode()I"),
            cancellable = true
    )
    private void knot$onExplodePre(CallbackInfo ci, @Local ServerExplosion explosion) {
        if (KnotExplosionEvent.PRE.invoker().onPreExplosion((ServerLevel) (Object) this, explosion).cancelsEvent()) {
            ci.cancel();
        }
    }
}