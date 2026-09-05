package com.daqem.knot.api.client.render.item.condition;

import com.daqem.knot.api.client.render.item.ItemOverrideCondition;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record DisplayContextCondition(ItemDisplayContext context) implements ItemOverrideCondition {

    public static final Identifier ID = Identifier.fromNamespaceAndPath("knot", "display_context");

    public static final MapCodec<DisplayContextCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            StringRepresentable.fromEnum(ItemDisplayContext::values).fieldOf("context").forGetter(DisplayContextCondition::context)
    ).apply(instance, DisplayContextCondition::new));

    @Override
    public Identifier getTypeId() {
        return ID;
    }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        return this.context == displayContext;
    }
}