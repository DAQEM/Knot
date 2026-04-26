package com.daqem.knot.api.mixin;

import com.daqem.knot.api.world.item.IIngredient;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(Ingredient.class)
public abstract class IngredientMixin implements Predicate<ItemStack>, IIngredient {

    @Shadow
    @Final
    private Ingredient.Value[] values;

    @Override
    public List<Item> knot$getItems() {
        return Stream.of(this.values)
                .flatMap(value -> value.getItems().stream())
                .map(ItemStack::getItem)
                .toList();
    }

    @Override
    public Ingredient knot$getIngredient() {
        return (Ingredient) (Object) this;
    }
}
