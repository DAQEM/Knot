package com.daqem.frame.mixin;

import com.daqem.frame.event.FrameBlockEvent;
import com.daqem.frame.event.EventResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase {

    @Inject(at = @At("RETURN"), method = "useItemOn", cancellable = true)
    public void use(ItemStack itemStack, Level level, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        BlockState state = level.getBlockState(blockHitResult.getBlockPos());
        EventResult eventResult = FrameBlockEvent.RIGHT_CLICK_BLOCK.invoker().onRightClickBlock(itemStack, level, player, interactionHand, state, blockHitResult.getBlockPos());
        if (eventResult.cancelsEvent()) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
