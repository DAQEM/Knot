package com.daqem.knot.events.mixin.common.block;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.block.BlockEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FarmlandBlock.class)
public abstract class FarmBlockMixin {

    @WrapOperation(
            method = "fallOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;D)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/FarmlandBlock;turnToDirt(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"
            )
    )
    private void knot$onFarmlandTrample(Entity entity, BlockState state, Level level, BlockPos pos, Operation<Void> original) {
        EventResult result = BlockEvent.FARMLAND_TRAMPLE.invoker().onFarmlandTrample(level, pos, state, entity.fallDistance, entity);
        if (!result.cancelsEvent()) {
            original.call(entity, state, level, pos);
        }
    }
}