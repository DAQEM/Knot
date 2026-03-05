package com.daqem.knot.events.mixin.common.entity.player;

import com.daqem.knot.api.world.entity.IAbstractArrow;
import com.daqem.knot.events.common.item.ItemEvent;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin extends Entity {

    public ProjectileMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "shootFromRotation(Lnet/minecraft/world/entity/Entity;FFFFF)V")
    private void shootFromRotation(Entity entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (entity instanceof ServerPlayer serverPlayer) {
            Projectile projectile = (Projectile) (Object) this;
            if (projectile instanceof ThrowableItemProjectile throwableItemProjectile) {
                ItemEvent.THROW_ITEM.invoker().onThrowItem(serverPlayer, throwableItemProjectile);
            } else if (projectile instanceof IAbstractArrow abstractArrow) {
                PlayerEvent.SHOOT_PROJECTILE.invoker().onShootProjectile(serverPlayer, abstractArrow);
            }
        }
    }
}
