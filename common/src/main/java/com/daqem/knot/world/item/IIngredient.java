package com.daqem.knot.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface IIngredient {

    List<Item> knot$getItems();

    Ingredient knot$getIngredient();
}
