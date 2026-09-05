package com.daqem.knot.api.client.render.item.condition;

import com.daqem.knot.api.client.render.item.ItemOverrideCondition;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;

public record EnchantmentCondition(ResourceKey<Enchantment> enchantment, int minLevel, int maxLevel) implements ItemOverrideCondition {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("knot", "enchantment");
    public static final MapCodec<EnchantmentCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceKey.codec(Registries.ENCHANTMENT).fieldOf("enchantment").forGetter(EnchantmentCondition::enchantment),
            Codec.INT.optionalFieldOf("min_level", 1).forGetter(EnchantmentCondition::minLevel),
            Codec.INT.optionalFieldOf("max_level", Integer.MAX_VALUE).forGetter(EnchantmentCondition::maxLevel)
    ).apply(instance, EnchantmentCondition::new));

    @Override
    public Identifier getTypeId() { return ID; }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (var entry : enchantments.entrySet()) {
            if (entry.getKey().is(enchantment)) {
                int lvl = entry.getIntValue();
                return lvl >= minLevel && lvl <= maxLevel;
            }
        }
        return false;
    }
}