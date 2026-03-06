package com.daqem.knot.events.mixin.common.entity;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.entity.EntityEvent;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;",
            shift = At.Shift.BEFORE
    ), method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    private void addEffect(MobEffectInstance effect, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        final LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof ServerPlayer serverPlayer) {
            EventResult eventResult = PlayerEvent.ADD_EFFECT.invoker().onAddEffect(serverPlayer, effect, entity);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "jumpFromGround")
    private void knot$onJump(CallbackInfo ci) {
        if ((Object) this instanceof Player player) {
            PlayerEvent.JUMP.invoker().onJump(player);
        }
    }

    @Inject(at = @At("HEAD"), method = "causeFallDamage", cancellable = true)
    private void knot$onLand(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            EventResult eventResult = PlayerEvent.LAND_ON_GROUND.invoker().onLandOnGround(player, this.fallDistance);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void knot$onDie(DamageSource source, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof ServerPlayer player) {
            EventResult result = EntityEvent.PLAYER_DEATH.invoker().onPlayerDeath(player, source);
            if (result.cancelsEvent()) {
                ci.cancel();
                return;
            }
        }

        if (source.getEntity() instanceof ServerPlayer player) {
            EventResult result = EntityEvent.PLAYER_KILL_ENTITY.invoker().onPlayerKillEntity(player, entity, source);
            if (result.cancelsEvent()) {
                ci.cancel();
            }
        }
    }
}