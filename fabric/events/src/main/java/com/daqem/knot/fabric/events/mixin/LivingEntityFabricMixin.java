package com.daqem.knot.fabric.events.mixin;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.entity.EntityEvent;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFabricMixin {

    @Unique
    private MutableFloat knot$damage = null;

    @Inject(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z",
                    shift = At.Shift.BEFORE
            ),
            order = 900,
            cancellable = true
    )
    private void onHurtServer(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.knot$damage = new MutableFloat(amount);
        final LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof ServerPlayer serverPlayer) {
            EventResult eventResult = PlayerEvent.ENTITY_HURT_PLAYER.invoker().onEntityHurtPlayer(serverPlayer, source, this.knot$damage);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
                return;
            }
        }
        if (source.getEntity() instanceof ServerPlayer serverPlayer) {
            EventResult eventResult = EntityEvent.PLAYER_HURT_ENTITY.invoker().onPlayerHurtEntity(serverPlayer, self, source, this.knot$damage);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
                return;
            }
        }
        if (self instanceof ServerPlayer defender && source.getEntity() instanceof ServerPlayer attacker) {
            EventResult eventResult = PlayerEvent.PLAYER_HURT_PLAYER.invoker().onPlayerHurtPlayer(attacker, defender, source, this.knot$damage);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;hurtCurrentlyUsedShield(F)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void knot$onBlock(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, float blockedAmount, boolean bl, float g) {
        if ((LivingEntity) (Object) this instanceof Player player && blockedAmount > 0.0F) {
            PlayerEvent.BLOCK_WITH_SHIELD.invoker().onBlockWithShield(player, source, amount);
        }
    }

    @ModifyVariable(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z",
                    shift = At.Shift.BEFORE
            ),
            order = 1100,
            argsOnly = true
    )
    private float modifyDamage(float amount) {
        if (this.knot$damage != null) {
            return this.knot$damage.getValue();
        }
        return amount;
    }
}
