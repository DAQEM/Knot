package com.daqem.knot.api.client.render.item.condition;

import com.daqem.knot.api.client.render.item.ItemOverrideCondition;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record StackSizeCondition(int min, int max) implements ItemOverrideCondition {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("knot", "stack_size");
    public static final MapCodec<StackSizeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.optionalFieldOf("min", 1).forGetter(StackSizeCondition::min),
            Codec.INT.optionalFieldOf("max", 99).forGetter(StackSizeCondition::max)
    ).apply(instance, StackSizeCondition::new));

    @Override
    public Identifier getTypeId() { return ID; }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        int count = stack.getCount();
        return count >= min && count <= max;
    }
}