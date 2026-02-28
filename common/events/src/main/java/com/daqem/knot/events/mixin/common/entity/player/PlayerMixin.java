package com.daqem.knot.events.mixin.common.entity.player;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.entity.EntityEvent;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("RETURN"), method = "getCurrentItemAttackStrengthDelay()F", cancellable = true)
    private void getCurrentItemAttackStrengthDelay(CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        MutableFloat speed = new MutableFloat(cir.getReturnValue());
        EventResult eventResult = PlayerEvent.GET_ATTACK_SPEED.invoker().onGetAttackSpeed(
                player,
                player.getWeaponItem(),
                speed
        );
        if (eventResult.cancelsEvent()) {
            cir.setReturnValue(0F);
        } else if (!Objects.equals(speed.floatValue(), cir.getReturnValue())) {
            cir.setReturnValue(speed.floatValue());
        }
    }

    @Inject(method = "interactOn", at = @At("HEAD"), cancellable = true)
    private void knot$onInteractOn(Entity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        EventResult result = EntityEvent.INTERACT_WITH_ENTITY.invoker().onInteractWithEntity((Player) (Object) this, entity, hand);
        if (result.cancelsEvent()) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}