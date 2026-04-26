package com.daqem.knot.fabric.events.mixin;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.item.ItemEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Blocking;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    @Shadow @Final private Fluid content;

    @Inject(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/BucketItem;emptyContents(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/BlockHitResult;)Z"
            ),
            cancellable = true
    )
    private void knot$onEmptyBucket(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, @Local(name = "itemStack") ItemStack itemStack, @Local(name = "blockPos3") BlockPos blockPos3) {
        EventResult result = ItemEvent.EMPTY_BUCKET.invoker().onEmptyBucket(player, itemStack, level, blockPos3, this.content.defaultFluidState().createLegacyBlock());

        if (result.cancelsEvent()) {
            cir.setReturnValue(InteractionResultHolder.fail(itemStack));
        }
    }
}