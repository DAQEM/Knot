package com.daqem.knot.events.mixin.common.entity.player;

import com.daqem.knot.events.common.entity.player.PlayerEvent;
import net.minecraft.advancements.triggers.FishingRodHookedTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(FishingRodHookedTrigger.class)
public abstract class FishingRodHookedTriggerMixin {

    @Inject(method = "trigger", at = @At("HEAD"))
    private void trigger(ServerPlayer player, ItemStack rod, FishingHook hook, Collection<ItemStack> items, CallbackInfo ci) {
        for (ItemStack stack : items) {
            PlayerEvent.FISH_UP_ITEM.invoker().onFishUpItem(player, stack);
        }
    }
}
