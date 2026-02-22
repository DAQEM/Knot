package com.daqem.frame.mixin;

import com.daqem.frame.event.common.FrameTickEvent;
import com.daqem.frame.event.server.FrameServersideTickEvent;
import com.daqem.frame.world.level.FrameScheduledTask;
import com.daqem.frame.world.level.IServerLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntityGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger frame$LOGGER = LoggerFactory.getLogger("Frame");

    @Unique
    private final Queue<FrameScheduledTask> frame$taskInbox = new ConcurrentLinkedQueue<>();

    @Unique
    private final PriorityQueue<FrameScheduledTask> frame$scheduledTasks = new PriorityQueue<>(Comparator.comparingLong(FrameScheduledTask::executionTime));

    protected MixinServerLevel(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Unique
    @Override
    public void frame$schedule(int delayTicks, Runnable action) {
        // We calculate the target time based on current game time + delay
        long targetTime = this.getGameTime() + Math.max(0, delayTicks);
        this.frame$taskInbox.add(new FrameScheduledTask(targetTime, action));
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void frame$preTick(CallbackInfo ci) {
        FrameServersideTickEvent.SERVER_LEVEL_PRE.invoker().tick((ServerLevel) (Object) this);
        FrameTickEvent.LEVEL_PRE.invoker().tick(this);

        // 1. Drain inbox to the schedule (Thread-safe transfer)
        FrameScheduledTask incoming;
        while ((incoming = this.frame$taskInbox.poll()) != null) {
            this.frame$scheduledTasks.add(incoming);
        }

        // 2. Run tasks that are due
        long currentTime = this.getGameTime();

        // While there are tasks, and the next task's time is now or in the past
        while (!this.frame$scheduledTasks.isEmpty() && this.frame$scheduledTasks.peek().executionTime() <= currentTime) {
            FrameScheduledTask task = this.frame$scheduledTasks.poll();
            if (task != null) {
                try {
                    task.action().run();
                } catch (Exception e) {
                    frame$LOGGER.error("Error executing delayed task in ServerLevel", e);
                }
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void frame$postTick(CallbackInfo ci) {
        FrameServersideTickEvent.SERVER_LEVEL_POST.invoker().tick((ServerLevel) (Object) this);
        FrameTickEvent.LEVEL_POST.invoker().tick(this);
    }
}