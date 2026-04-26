package com.daqem.knot.events.mixin.common.item;

import com.daqem.knot.events.common.item.ItemEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu {

    public SmithingMenuMixin(@Nullable MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(type, containerId, playerInventory, access);
    }

    @Inject( method = "onTake", at = @At("HEAD"))
    private void knot$onRecipeCrafted(Player player, ItemStack itemStack, CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (this.resultSlots.getRecipeUsed() instanceof RecipeHolder<?> recipeHolder) {
                ItemEvent.CRAFT_ITEM.invoker().onCraftItem(serverPlayer, recipeHolder.value(), itemStack);
            }
        }
    }
}
