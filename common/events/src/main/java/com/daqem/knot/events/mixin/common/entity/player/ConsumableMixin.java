package com.daqem.knot.events.mixin.common.entity.player;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Consumable.class)
public abstract class ConsumableMixin {

    @Shadow public abstract ItemUseAnimation animation();

    @Inject(at = @At("HEAD"), method = "onConsume", cancellable = true)
    private void onConsume(Level level, LivingEntity livingEntity, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
        if (livingEntity instanceof Player player) {
            if (this.animation() == ItemUseAnimation.EAT) {
                EventResult eventResult = PlayerEvent.EAT.invoker().onEat(player, itemStack);
                if (eventResult.cancelsEvent()) {
                    cir.setReturnValue(itemStack);
                }
            }
            if (this.animation() == ItemUseAnimation.DRINK) {
                EventResult eventResult = PlayerEvent.DRINK.invoker().onDrink(player, itemStack);
                if (eventResult.cancelsEvent()) {
                    cir.setReturnValue(itemStack);
                }
            }
        }
    }
}
