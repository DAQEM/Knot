package com.daqem.frame.mixin;

import com.daqem.frame.world.item.IAbstractCookingRecipe;
import com.daqem.frame.world.item.IIngredient;
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
    public ItemStack frame$getResult() {
        return this.result();
    }

    @Override
    public IIngredient frame$getIngredient() {
        Ingredient ingredient = this.input();
        //noinspection ConstantValue
        if ((Object) ingredient instanceof IIngredient frameIngredient) {
            return frameIngredient;
        }
        return null;
    }

    @Override
    public AbstractCookingRecipe frame$getRecipe() {
        return (AbstractCookingRecipe) (Object) this;
    }
}
