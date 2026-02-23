package com.daqem.knot.mixin;

import com.daqem.knot.event.KnotPlayerEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandMenu.PotionSlot.class)
public abstract class MixinPotionSlot extends Slot {

    public MixinPotionSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Inject(at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/advancements/criterion/BrewedPotionTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/core/Holder;)V",
            shift = At.Shift.BEFORE
    ), method = "onTake")
    private void onTake(Player player, ItemStack itemStack, CallbackInfo ci) {
        if (this.container instanceof BrewingStandBlockEntity brewingStandBlockEntity) {
            KnotPlayerEvent.BREW_POTION.invoker().onBrewPotion(player, itemStack, brewingStandBlockEntity);
        }
    }
}
