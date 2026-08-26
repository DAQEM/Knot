package com.daqem.knot.neoforge.events.mixin;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.block.BlockEvent;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
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

    @ModifyReturnValue(
            method = "getDestroyProgress",
            at = @At("RETURN")
    )
    private float onGetDestroyProgress(float original, BlockState state, Player player, BlockGetter level, BlockPos pos) {
        // If the block is unbreakable or progress is already 0, do nothing
        if (original <= 0.0F) {
            return original;
        }

        // Get the player's raw block breaking speed to pass into the event
        float originalSpeed = player.getDestroySpeed(state, pos);
        MutableFloat speed = new MutableFloat(originalSpeed);

        EventResult eventResult = BlockEvent.GET_DESTROY_SPEED.invoker().onGetDestroySpeed(
                player,
                state,
                pos,
                player.getMainHandItem(),
                speed
        );

        // Handle cancellation
        if (eventResult.cancelsEvent()) {
            return 0.0F;
        }

        // If the speed was modified, calculate the multiplier and apply it to the final progress
        if (speed.floatValue() != originalSpeed && originalSpeed > 0.0F) {
            float multiplier = speed.floatValue() / originalSpeed;
            return original * multiplier;
        }

        return original;
    }
}
