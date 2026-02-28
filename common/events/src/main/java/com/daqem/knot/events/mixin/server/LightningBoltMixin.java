package com.daqem.knot.events.mixin.server;

import com.daqem.knot.events.server.ServerLightningEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin {

    @Inject(method = "tick", at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;iterator()Ljava/util/Iterator;",
            ordinal = 1
    ))
    public void knot$handleLightningStrike(CallbackInfo ci, @Local List<Entity> list) {
        LightningBolt bolt = (LightningBolt) (Object) this;
        if (!bolt.isRemoved() && !bolt.level().isClientSide()) {
            ServerLightningEvent.STRIKE.invoker().onStrike(bolt, bolt.level(), bolt.position(), list);
        }
    }
}