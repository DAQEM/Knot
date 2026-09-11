package com.daqem.knot.api.client.render.item.condition;

import com.daqem.knot.api.client.render.item.ItemOverrideCondition;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record CustomNameCondition(String name, boolean regex) implements ItemOverrideCondition {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("knot", "custom_name");
    public static final MapCodec<CustomNameCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(CustomNameCondition::name),
            Codec.BOOL.optionalFieldOf("regex", false).forGetter(CustomNameCondition::regex)
    ).apply(instance, CustomNameCondition::new));

    @Override
    public Identifier getTypeId() { return ID; }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        Component nameComponent = stack.get(DataComponents.CUSTOM_NAME);
        if (nameComponent == null) return false;
        String plainName = nameComponent.getString();
        return regex ? plainName.matches(name) : plainName.equals(name);
    }
}