package com.daqem.knot.mixin;

import com.daqem.knot.event.KnotPlayerEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class MixinFishingHook {

    @Inject(at = @At("HEAD"), method = "retrieve(Lnet/minecraft/world/item/ItemStack;)I")
    private void retrieve(ItemStack itemStack, CallbackInfoReturnable<Integer> info) {
        FishingHook fishingHook = (FishingHook)(Object)this;
        if (fishingHook.getPlayerOwner() instanceof Player player) {
            KnotPlayerEvent.ROD_REEL_IN.invoker().onRodReelIn(player, fishingHook);
        }
    }
}
