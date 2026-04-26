package com.daqem.knot.api.mixin;

import com.daqem.knot.api.world.item.IAbstractCookingRecipe;
import com.daqem.knot.api.world.item.IIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractCookingRecipe.class)
public abstract class AbstractCookingRecipeMixin implements Recipe<SingleRecipeInput>, IAbstractCookingRecipe {

    @Shadow
    @Final
    protected ItemStack result;

    @Shadow
    @Final
    protected Ingredient ingredient;

    @Override
    public ItemStack knot$getResult() {
        return this.result;
    }

    @Override
    public IIngredient knot$getIngredient() {
        Ingredient ingredient = this.ingredient;
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
