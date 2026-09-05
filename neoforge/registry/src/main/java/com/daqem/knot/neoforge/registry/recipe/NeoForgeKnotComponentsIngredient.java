package com.daqem.knot.neoforge.registry.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.stream.Stream;

public class NeoForgeKnotComponentsIngredient implements ICustomIngredient {

    private final Ingredient base;
    private final DataComponentPatch components;

    public static final MapCodec<NeoForgeKnotComponentsIngredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("base").forGetter(i -> i.base),
            DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(i -> i.components),
            // Forces DataGen to write "fabric:type": "knot:components" into the JSON while remaining optional on read
            Codec.STRING.optionalFieldOf("fabric:type").forGetter(_ -> Optional.of("knot:components"))
    ).apply(inst, (base, components, _) -> new NeoForgeKnotComponentsIngredient(base, components)));

    public static final StreamCodec<RegistryFriendlyByteBuf, NeoForgeKnotComponentsIngredient> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, i -> i.base,
            DataComponentPatch.STREAM_CODEC, i -> i.components,
            NeoForgeKnotComponentsIngredient::new
    );

    public static final IngredientType<NeoForgeKnotComponentsIngredient> TYPE = new IngredientType<>(CODEC, STREAM_CODEC);

    public NeoForgeKnotComponentsIngredient(Ingredient base, DataComponentPatch components) {
        this.base = base;
        this.components = components;
    }

    public static Ingredient of(Ingredient base, DataComponentPatch components) {
        return new NeoForgeKnotComponentsIngredient(base, components).toVanilla();
    }

    public static Ingredient of(ItemLike item, DataComponentPatch components) {
        return of(Ingredient.of(item), components);
    }

    public static Ingredient of(HolderSet<Item> tag, DataComponentPatch components) {
        return of(Ingredient.of(tag), components);
    }

    @Override
    public boolean test(@NonNull ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (!base.test(stack)) return false;

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
    public boolean isSimple() {
        return false;
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
    public @NonNull IngredientType<?> getType() {
        return TYPE;
    }
}