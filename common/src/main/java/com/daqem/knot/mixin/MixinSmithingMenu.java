package com.daqem.knot.mixin;

import com.daqem.knot.event.KnotItemEvent;
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
public abstract class MixinSmithingMenu extends ItemCombinerMenu {

    public MixinSmithingMenu(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
        super(menuType, i, inventory, containerLevelAccess, itemCombinerMenuSlotDefinition);
    }

    @Inject( method = "onTake", at = @At("HEAD"))
    private void knot$onRecipeCrafted(Player player, ItemStack itemStack, CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (this.resultSlots.getRecipeUsed() instanceof RecipeHolder<?> recipeHolder) {
                KnotItemEvent.CRAFT_ITEM.invoker().onCraftItem(serverPlayer, recipeHolder.value(), itemStack);
            }
        }
    }
}
