package com.daqem.frame.mixin.client;

import com.daqem.frame.event.client.FrameClientsideTickEvent;
import com.daqem.frame.event.common.FrameTickEvent;
import com.daqem.frame.world.level.IClientLevel;
import net.minecraft.client.multiplayer.CacheSlot;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel extends Level implements CacheSlot.Cleaner<@NotNull ClientLevel>, IClientLevel {

    protected MixinClientLevel(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void frame$preTick(CallbackInfo ci) {
        FrameClientsideTickEvent.CLIENT_LEVEL_PRE.invoker().tick((ClientLevel) (Object) this);
        FrameTickEvent.LEVEL_PRE.invoker().tick(this);
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void frame$postTick(CallbackInfo ci) {
        FrameClientsideTickEvent.CLIENT_LEVEL_POST.invoker().tick((ClientLevel) (Object) this);
        FrameTickEvent.LEVEL_POST.invoker().tick(this);
    }
}
