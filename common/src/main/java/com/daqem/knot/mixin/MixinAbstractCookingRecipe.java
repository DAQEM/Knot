package com.daqem.knot.mixin;

import com.daqem.knot.world.item.IAbstractCookingRecipe;
import com.daqem.knot.world.item.IIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractCookingRecipe.class)
public abstract class MixinAbstractCookingRecipe extends SingleItemRecipe implements IAbstractCookingRecipe {

    public MixinAbstractCookingRecipe(String string, Ingredient ingredient, ItemStack itemStack) {
        super(string, ingredient, itemStack);
    }

    @Override
    public ItemStack knot$getResult() {
        return this.result();
    }

    @Override
    public IIngredient knot$getIngredient() {
        Ingredient ingredient = this.input();
        //noinspection ConstantValue
        if ((Object) ingredient instanceof IIngredient knotIngredient) {
            return knotIngredient;
        }
        return null;
    }

    @Override
    public AbstractCookingRecipe knot$getRecipe() {
        return (AbstractCookingRecipe) (Object) this;
    }
}
