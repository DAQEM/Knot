package com.daqem.knot.events.mixin.common.entity;

import com.daqem.knot.events.common.entity.EntityEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MerchantResultSlot.class)
public abstract class MerchantResultSlotMixin {

    @Shadow @Final private Merchant merchant;

    @Inject(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/resources/Identifier;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void knot$onTradeWithVillager(Player player, ItemStack itemStack, CallbackInfo ci, MerchantOffer merchantOffer, ItemStack itemStack2, ItemStack itemStack3) {
        EntityEvent.TRADE_WITH_VILLAGER.invoker().onTradeWithVillager(player, this.merchant, merchantOffer, itemStack);
    }
}