package com.daqem.frame.fabric.mixin;

import com.daqem.frame.event.FrameEntityEvent;
import com.daqem.frame.event.FramePlayerEvent;
import com.daqem.frame.event.EventResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public class LivingEntityFabricMixin {

    @Unique
    private MutableFloat frame$damage = null;

    @Inject(
            method = "hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z",
                    shift = At.Shift.BEFORE
            ),
            order = 900,
            cancellable = true
    )
    private void onHurtServer(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        this.frame$damage = new MutableFloat(f);
        final LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof ServerPlayer serverPlayer) {
            EventResult eventResult = FramePlayerEvent.ENTITY_HURT_PLAYER.invoker().onEntityHurtPlayer(serverPlayer, damageSource, this.frame$damage);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
                return;
            }
        }
        if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
            EventResult eventResult = FrameEntityEvent.PLAYER_HURT_ENTITY.invoker().onPlayerHurtEntity(serverPlayer, self, damageSource, this.frame$damage);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
                return;
            }
        }
        if (self instanceof ServerPlayer defender && damageSource.getEntity() instanceof ServerPlayer attacker) {
            EventResult eventResult = FramePlayerEvent.PLAYER_HURT_PLAYER.invoker().onPlayerHurtPlayer(attacker, defender, damageSource, this.frame$damage);
            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(
            method = "hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;applyItemBlocking(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)F",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void frame$onBlock(ServerLevel serverLevel, DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir, float blockedAmount, ItemStack itemStack) {
        if ((LivingEntity) (Object) this instanceof Player player && blockedAmount > 0.0F) {
            FramePlayerEvent.BLOCK_WITH_SHIELD.invoker().onBlockWithShield(player, damageSource, amount);
        }
    }

    @ModifyVariable(
            method = "hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z",
                    shift = At.Shift.BEFORE
            ),
            order = 1100,
            argsOnly = true
    )
    private float modifyDamage(float f) {
        if (this.frame$damage != null) {
            return this.frame$damage.getValue();
        }
        return f;
    }
}
