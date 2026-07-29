package com.daqem.knot.neoforge.events.mixin;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.block.BlockEvent;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {

    @ModifyExpressionValue(
            method = "getDestroyProgress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getDestroySpeed(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F"
            )
    )
    private float onGetDestroyProgress(float original, BlockState state, Player player, BlockGetter level, BlockPos pos) {
        // 1. Wrap the original speed (e.g., 1.0 for hand, 6.0 for iron tool)
        MutableFloat speed = new MutableFloat(original);

        // 2. Invoke your event
        // Note: 'view' is likely your BlockGetter/Level
        EventResult eventResult = BlockEvent.GET_DESTROY_SPEED.invoker().onGetDestroySpeed(
                player,
                state,
                pos,
                player.getMainHandItem(),
                speed
        );

        // 3. Handle cancellation
        if (eventResult.cancelsEvent()) {
            return 0.0F; // Returning 0 speed generally prevents mining progress
        }

        // 4. Return the modified value
        return speed.floatValue();
    }
}
