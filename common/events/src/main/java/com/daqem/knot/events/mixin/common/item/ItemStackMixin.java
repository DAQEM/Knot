package com.daqem.knot.events.mixin.common.item;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import com.daqem.knot.events.common.item.ItemEvent;
import net.minecraft.references.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract UseAnim getUseAnimation();

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    private void knot$use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        EventResult eventResult = ItemEvent.USE_ITEM.invoker().onUseItem(level, player, interactionHand, (ItemStack) (Object) this);
        if (eventResult.cancelsEvent()) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }

    @Inject(at = @At("HEAD"), method = "finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;", cancellable = true)
    private void knot$finishUsingItem(Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        if (entity instanceof Player player) {
            if (this.getUseAnimation() == UseAnim.EAT) {
                EventResult eventResult = PlayerEvent.EAT.invoker().onEat(player, (ItemStack) (Object) this);
                if (eventResult.cancelsEvent()) {
                    cir.cancel();
                }
            }
            if (this.getUseAnimation() == UseAnim.DRINK) {
                EventResult eventResult = PlayerEvent.DRINK.invoker().onDrink(player, (ItemStack) (Object) this);
                if (eventResult.cancelsEvent()) {
                    cir.cancel();
                }
            }
        }
    }
}