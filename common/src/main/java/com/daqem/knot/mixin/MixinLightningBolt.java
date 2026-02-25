package com.daqem.knot.mixin;

import com.daqem.knot.event.KnotLightningEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LightningBolt.class)
public abstract class MixinLightningBolt {

    @Inject(method = "tick", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",
            ordinal = 1,
            shift = At.Shift.BY,
            by = 1
    ))
    public void knot$handleLightningStrike(CallbackInfo ci, @Local List<Entity> list) {
        LightningBolt bolt = (LightningBolt) (Object) this;
        if (!bolt.isRemoved() && !bolt.level().isClientSide()) {
            KnotLightningEvent.STRIKE.invoker().onStrike(bolt, bolt.level(), bolt.position(), list);
        }
    }
}