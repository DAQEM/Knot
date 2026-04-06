package com.daqem.knot.api.mixin;

import com.daqem.knot.api.world.item.IAbstractCookingRecipe;
import com.daqem.knot.api.world.item.IIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractCookingRecipe.class)
public abstract class AbstractCookingRecipeMixin extends SingleItemRecipe implements IAbstractCookingRecipe {

    public AbstractCookingRecipeMixin(CommonInfo commonInfo, Ingredient input, ItemStackTemplate result) {
        super(commonInfo, input, result);
    }

    @Override
    public ItemStack knot$getResult() {
        return this.result().create();
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
