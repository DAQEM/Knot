package com.daqem.knot.neoforge.events.mixin;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends Item {

    public AxeItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "evaluateNewBlockState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V",
                    ordinal = 0
            ),
            cancellable = true
    )
    private void onEvaluateLogStrip(Level level, BlockPos blockPos, Player player, BlockState blockState, UseOnContext context, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (player != null) {
            EventResult eventResult = PlayerEvent.STRIP_LOG.invoker().onStripLog(
                    player,
                    context.getHand(),
                    context.getItemInHand(),
                    blockPos,
                    blockState,
                    level
            );

            if (eventResult.cancelsEvent()) {
                cir.setReturnValue(Optional.empty());
            }
        }
    }
}