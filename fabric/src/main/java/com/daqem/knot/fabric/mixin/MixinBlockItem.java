package com.daqem.knot.fabric.mixin;

import com.daqem.knot.event.EventResult;
import com.daqem.knot.event.KnotBlockEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockItem.class)
public abstract class MixinBlockItem {

    @Inject(
            method = "place",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/BlockItem;placeBlock(Lnet/minecraft/world/item/context/BlockPlaceContext;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void knot$onPlace(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<InteractionResult> cir, BlockPlaceContext context, BlockState blockState) {
        EventResult result = KnotBlockEvent.PLACE_BLOCK.invoker().onPlaceBlock(context.getLevel(), context.getClickedPos(), blockState, context.getPlayer());
        if (result.cancelsEvent()) {
            cir.setReturnValue(InteractionResult.FAIL);
        } else {
            if (blockState.getBlock() instanceof CropBlock) {
                EventResult result1 = KnotBlockEvent.PLANT_CROP.invoker().onPlantCrop(context.getLevel(), context.getClickedPos(), blockState, context.getPlayer());
                if (result1.cancelsEvent()) {
                    cir.setReturnValue(InteractionResult.FAIL);
                }
            }
        }
    }
}