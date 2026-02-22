package com.daqem.frame.world.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;

public interface IAbstractCookingRecipe {

    ItemStack frame$getResult();
    IIngredient frame$getIngredient();
    AbstractCookingRecipe frame$getRecipe();

}
