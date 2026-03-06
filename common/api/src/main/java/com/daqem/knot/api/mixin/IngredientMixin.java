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

import java.util.List;
import java.util.function.Predicate;

@Mixin(Ingredient.class)
public abstract class IngredientMixin implements Predicate<ItemStack>, IIngredient {

    @Shadow
    @Final
    private HolderSet<Item> values;

    @Override
    public List<Item> knot$getItems() {
        return this.values.stream().map(Holder::value).toList();
    }

    @Override
    public Ingredient knot$getIngredient() {
        return (Ingredient) (Object) this;
    }
}
