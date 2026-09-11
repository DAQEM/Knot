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

public record DamageCondition(float minPercent, float maxPercent) implements ItemOverrideCondition {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("knot", "damage");
    public static final MapCodec<DamageCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.FLOAT.optionalFieldOf("min_percent", 0.0f).forGetter(DamageCondition::minPercent),
            Codec.FLOAT.optionalFieldOf("max_percent", 100.0f).forGetter(DamageCondition::maxPercent)
    ).apply(instance, DamageCondition::new));

    @Override
    public Identifier getTypeId() { return ID; }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        if (!stack.isDamageableItem()) return false;
        float percent = ((float) stack.getDamageValue() / stack.getMaxDamage()) * 100f;
        return percent >= minPercent && percent <= maxPercent;
    }
}