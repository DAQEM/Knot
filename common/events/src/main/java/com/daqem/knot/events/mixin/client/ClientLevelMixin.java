package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.client.ClientLevelLifecycleEvent;
import com.daqem.knot.events.client.ClientTickEvent;
import com.daqem.knot.events.common.entity.EntityEvent;
import com.daqem.knot.events.common.TickEvent;
import com.daqem.knot.api.world.level.IClientLevel;
import net.minecraft.client.multiplayer.CacheSlot;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level implements CacheSlot.Cleaner<@NotNull ClientLevel>, IClientLevel {

    protected ClientLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void knot$onInit(CallbackInfo ci) {
        ClientLevelLifecycleEvent.CLIENT_LEVEL_LOAD.invoker().onClientLevelLoad((ClientLevel) (Object) this);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void knot$preTick(CallbackInfo ci) {
        ClientTickEvent.CLIENT_LEVEL_PRE.invoker().tick((ClientLevel) (Object) this);
        TickEvent.LEVEL_PRE.invoker().tick(this);
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void knot$postTick(CallbackInfo ci) {
        ClientTickEvent.CLIENT_LEVEL_POST.invoker().tick((ClientLevel) (Object) this);
        TickEvent.LEVEL_POST.invoker().tick(this);
    }

    @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
    private void knot$onAddEntity(Entity entity, CallbackInfo ci) {
        if (EntityEvent.ADD.invoker().onAddEntity(entity, (ClientLevel) (Object) this).cancelsEvent()) {
            ci.cancel();
        }
    }
}
