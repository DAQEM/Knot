package com.daqem.knot.events.mixin.common.item;

import com.daqem.knot.events.common.item.ItemEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    @Inject(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;isClientSide()Z",
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void knot$onFillBucket(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir, ItemStack itemStack, BlockHitResult hitResult, BlockPos pos, Direction direction, BlockPos directionOffsetPos, BlockState clicked, BlockPos placePos, BlockState blockState, BucketPickup bucketPickupBlock, ItemStack taken, ItemStack result) {
        ItemEvent.FILL_BUCKET.invoker().onFillBucket(player, taken, level, pos, blockState);
    }
}