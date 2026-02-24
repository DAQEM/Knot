package com.daqem.knot.mixin;

import com.daqem.knot.event.KnotItemEvent;
import com.daqem.knot.event.EventResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    private void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        EventResult eventResult = KnotItemEvent.USE_ITEM.invoker().onUseItem(level, player, interactionHand, (ItemStack) (Object) this);
        if (eventResult.cancelsEvent()) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}