package com.daqem.knot.fabric.events.mixin.compat.rightclickharvest;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.block.BlockEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "io.github.jamalam360.rightclickharvest.fabric.RightClickHarvestPlatformImpl")
public class RightClickHarvestPlatformImplMixin {

    @Inject(method = "postBreakEvent", at = @At("HEAD"), cancellable = true)
    private static void knot$onPostBreakEvent(Level level, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof ServerPlayer serverPlayer && level instanceof ServerLevel serverLevel) {
            EventResult breakResult = BlockEvent.BREAK_BLOCK.invoker().onBreakBlock(serverLevel, pos, state, serverPlayer);
            if (breakResult.cancelsEvent()) {
                cir.setReturnValue(true);
                return;
            }

            if (state.getBlock() instanceof CropBlock) {
                EventResult harvestResult = BlockEvent.HARVEST_CROP.invoker().onHarvestCrop(serverLevel, pos, state, serverPlayer);
                if (harvestResult.cancelsEvent()) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "postPlaceEvent", at = @At("HEAD"), cancellable = true)
    private static void knot$onPostPlaceEvent(Level level, BlockPos pos, Player player, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof ServerPlayer serverPlayer && level instanceof ServerLevel serverLevel) {
            BlockState state = level.getBlockState(pos);

            EventResult placeResult = BlockEvent.PLACE_BLOCK.invoker().onPlaceBlock(serverLevel, pos, state, serverPlayer);
            if (placeResult.cancelsEvent()) {
                cir.setReturnValue(true);
                return;
            }

            if (state.getBlock() instanceof CropBlock) {
                EventResult plantResult = BlockEvent.PLANT_CROP.invoker().onPlantCrop(serverLevel, pos, state, serverPlayer);
                if (plantResult.cancelsEvent()) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}