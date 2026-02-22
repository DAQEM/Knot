package com.daqem.frame.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface IIngredient {

    List<Item> frame$getItems();

    Ingredient frame$getIngredient();
}
