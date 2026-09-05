package com.daqem.knot.fabric.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

public class FabricKnotComponentsIngredient implements CustomIngredient {

    private final Ingredient base;
    private final DataComponentPatch components;

    public FabricKnotComponentsIngredient(Ingredient base, DataComponentPatch components) {
        this.base = base;
        this.components = components;
    }

    @Override
    public boolean test(@NonNull ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (!base.test(stack)) return false;

        // Non-strict matching: check if the components in the patch match the stack
        for (var entry : components.entrySet()) {
            var type = entry.getKey();
            var value = entry.getValue();

            if (value.isPresent()) {
                if (!stack.has(type) || !stack.get(type).equals(value.get())) return false;
            } else {
                if (stack.has(type)) return false;
            }
        }
        return true;
    }

    @Override
    public @NonNull Stream<Holder<Item>> items() {
        return base.items();
    }

    @Override
    public boolean requiresTesting() {
        return true;
    }

    @Override
    public @NonNull SlotDisplay display() {
        // Explicitly map as <SlotDisplay> to satisfy the List<SlotDisplay> requirement
        return new SlotDisplay.Composite(base.items().<SlotDisplay>map(holder -> {
            ItemStackTemplate template = new ItemStackTemplate(holder, 1, components);
            return new SlotDisplay.ItemStackSlotDisplay(template);
        }).toList());
    }

    @Override
    public @NonNull CustomIngredientSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements CustomIngredientSerializer<FabricKnotComponentsIngredient> {
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = Identifier.fromNamespaceAndPath("knot", "components");

        private static final MapCodec<FabricKnotComponentsIngredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("base").forGetter(i -> i.base),
                DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(i -> i.components)
        ).apply(inst, FabricKnotComponentsIngredient::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, FabricKnotComponentsIngredient> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, i -> i.base,
                DataComponentPatch.STREAM_CODEC, i -> i.components,
                FabricKnotComponentsIngredient::new
        );

        @Override
        public @NonNull Identifier getIdentifier() {
            return ID;
        }

        @Override
        public @NonNull MapCodec<FabricKnotComponentsIngredient> getCodec() {
            return CODEC;
        }

        @Override
        public @NonNull StreamCodec<RegistryFriendlyByteBuf, FabricKnotComponentsIngredient> getStreamCodec() {
            return STREAM_CODEC;
        }
    }
}