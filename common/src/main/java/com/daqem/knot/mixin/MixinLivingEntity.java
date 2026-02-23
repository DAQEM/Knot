package com.daqem.knot.mixin;

import com.daqem.knot.event.KnotPlayerEvent;
import com.daqem.knot.event.EventResult;
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
public abstract class MixinLivingEntity extends Entity {

    public MixinLivingEntity(EntityType<?> entityType, Level level) {
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
            EventResult eventResult = KnotPlayerEvent.ADD_EFFECT.invoker().onAddEffect(serverPlayer, effect, entity);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "jumpFromGround")
    private void knot$onJump(CallbackInfo ci) {
        if ((Object) this instanceof Player player) {
            KnotPlayerEvent.JUMP.invoker().onJump(player);
        }
    }

    @Inject(at = @At("HEAD"), method = "causeFallDamage", cancellable = true)
    private void knot$onLand(double d, float f, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            EventResult eventResult = KnotPlayerEvent.LAND_ON_GROUND.invoker().onLandOnGround(player, this.fallDistance);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
            }
        }
    }
}