package com.daqem.knot.fabric.events.mixin;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.item.ItemEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Unique
    private MutableInt knot$damage = null;

    @Inject(
            method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"),
            order = 900,
            cancellable = true)
    private void onHurtAndBreak(int damage, ServerLevel serverLevel, ServerPlayer serverPlayer, Consumer<Item> consumer, CallbackInfo ci) {
        MutableInt mutableDamage = new MutableInt(damage);
        EventResult eventResult = ItemEvent.HURT_ITEM.invoker().onHurtItem(serverPlayer, (ItemStack) (Object) this, mutableDamage);
        if (eventResult.cancelsEvent()) {
            ci.cancel();
            return;
        }
        if (mutableDamage.getValue() != damage) {
            this.knot$damage = mutableDamage;
        }
    }

    @ModifyVariable(
            method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"),
            argsOnly = true,
            order = 1100
    )
    private int modifyDamage(int i) {
        try {
            if (this.knot$damage != null) {
                return this.knot$damage.getValue();
            }
            return i;
        } finally {
            this.knot$damage = null;
        }
    }

    @Inject(
            method = "applyDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"
            )
    )
    private void knot$onItemBreak(int i, ServerPlayer serverPlayer, Consumer<Item> consumer, CallbackInfo ci) {
        ItemEvent.ITEM_BREAK.invoker().onItemBreak(serverPlayer, (ItemStack) (Object) this);
    }
}
