package com.daqem.knot.events.mixin.compat.apothic_enchanting;

import com.daqem.knot.events.compat.apothic_enchanting.ApothicEnchantingCompat;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "dev.shadowsoffire.apothic_enchanting.table.ApothEnchantmentMenu", remap = false)
public class ApothEnchantmentMenuMixin {

    @Inject(method = "clickMenuButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ContainerLevelAccess;execute(Ljava/util/function/BiConsumer;)V"))
    private void knot$captureCost(Player player, int id, CallbackInfoReturnable<Boolean> cir) {
        // id is the slot index (0, 1, or 2). The actual level cost is id + 1.
        ApothicEnchantingCompat.getInstance().setCapturedCost(id + 1);
    }

    @Inject(method = "clickMenuButton", at = @At("RETURN"))
    private void knot$clearCost(Player player, int id, CallbackInfoReturnable<Boolean> cir) {
        ApothicEnchantingCompat.getInstance().clearCapturedCost();
    }
}