package com.daqem.knot.fabric.events.mixin;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.block.BlockEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {

    @Shadow
    @Final
    protected ServerPlayer player;

    @Shadow
    protected ServerLevel level;

    @Inject(at = @At("HEAD"), method = "destroyBlock", cancellable = true)
    private void knot$onDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = this.level.getBlockState(pos);
        EventResult result = BlockEvent.BREAK_BLOCK.invoker().onBreakBlock(this.level, pos, state, this.player);
        if (result.cancelsEvent()) {
            cir.setReturnValue(false);
        } else {
            if (state.getBlock() instanceof CropBlock) {
                EventResult result1 = BlockEvent.HARVEST_CROP.invoker().onHarvestCrop(this.level, pos, state, this.player);
                if (result1.cancelsEvent()) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}