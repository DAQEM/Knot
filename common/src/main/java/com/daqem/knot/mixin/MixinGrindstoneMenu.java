package com.daqem.knot.mixin;

import com.daqem.knot.event.KnotPlayerEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.inventory.GrindstoneMenu$4")
public abstract class MixinGrindstoneMenu extends Slot {

    @Shadow protected abstract int getExperienceAmount(Level par1);

    public MixinGrindstoneMenu(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Inject(
            method = "onTake",
            at = @At("HEAD")
    )
    private void onTake(Player player, ItemStack itemStack, CallbackInfo ci) {
        KnotPlayerEvent.GRIND_ITEM.invoker().onGrindItem(player, itemStack, getExperienceAmount(player.level()));
    }
}
