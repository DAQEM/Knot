package com.daqem.knot.events.mixin.server;

import com.daqem.knot.events.server.ServerLightningEvent;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin {

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",
                    ordinal = 1
            )
    )
    public List<Entity> knot$handleLightningStrike(List<Entity> original) {
        LightningBolt bolt = (LightningBolt) (Object) this;
        if (!bolt.isRemoved() && !bolt.level().isClientSide()) {
            ServerLightningEvent.STRIKE.invoker().onStrike(bolt, bolt.level(), bolt.position(), original);
        }
        return original;
    }
}