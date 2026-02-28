package com.daqem.knot.api.world.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;

public interface IAbstractCookingRecipe {

    ItemStack knot$getResult();
    IIngredient knot$getIngredient();
    AbstractCookingRecipe knot$getRecipe();

}
