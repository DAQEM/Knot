package com.daqem.knot.mixin;

import com.daqem.knot.event.KnotExplosionEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerExplosion.class)
public abstract class MixinServerExplosion {

    @Shadow public abstract ServerLevel level();

    @Inject(method = "hurtEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;", ordinal = 0))
    private void knot$onExplosionDetonate(CallbackInfo ci, @Local List<Entity> list) {
        KnotExplosionEvent.DETONATE.invoker().onDetonate(level(), (ServerExplosion) (Object) this, list);
    }
}