package com.daqem.knot.neoforge.events.mixin;

import com.daqem.knot.events.server.ServerExplosionEvent;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ServerExplosion.class)
public abstract class ServerExplosionMixin {

    @Shadow public abstract ServerLevel level();

    @ModifyExpressionValue(
            method = "hurtEntities(Ljava/util/List;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"
            )
    )
    private List<Entity> knot$onExplosionDetonate(List<Entity> original) {
        ServerExplosionEvent.DETONATE.invoker().onDetonate(level(), (ServerExplosion) (Object) this, original);
        return original;
    }
}