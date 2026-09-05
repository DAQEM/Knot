package com.daqem.knot.test.neoforge.datagen;

import com.daqem.knot.neoforge.registry.recipe.NeoForgeKnotComponentsIngredient;
import com.daqem.knot.test.registry.TestItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TestRecipeProvider extends RecipeProvider {

    protected TestRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        // 1. Build the ItemEnchantments component for Lure I
        var enchantmentsLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        var lureHolder = enchantmentsLookup.getOrThrow(Enchantments.LURE);

        ItemEnchantments.Mutable lure = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        lure.set(lureHolder, 1);

        DataComponentPatch lure1Patch = DataComponentPatch.builder()
                .set(DataComponents.STORED_ENCHANTMENTS, lure.toImmutable())
                .build();

        // 2. Build the recipe using your custom ingredient
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, TestItems.TEST_ITEM.get())
                .requires(NeoForgeKnotComponentsIngredient.of(Items.ENCHANTED_BOOK, lure1Patch))
                .requires(Items.LAPIS_LAZULI)
                .unlockedBy("has_lapis", has(Items.LAPIS_LAZULI))
                .save(output, "knot_test:test_components_recipe");
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput output) {
            return new TestRecipeProvider(provider, output);
        }

        @Override
        public @NonNull String getName() {
            return "Recipes";
        }
    }
}