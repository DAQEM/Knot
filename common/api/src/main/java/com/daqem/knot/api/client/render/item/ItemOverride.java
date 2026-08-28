package com.daqem.knot.api.client.render.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;

public record ItemOverride(
        List<Holder<Item>> items,
        Identifier model,
        List<ItemOverrideCondition> conditions,
        int priority
) {

    public static final Codec<ItemOverride> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(BuiltInRegistries.ITEM.holderByNameCodec()).fieldOf("items").forGetter(ItemOverride::items),
            Identifier.CODEC.fieldOf("model").forGetter(ItemOverride::model),
            ItemOverrideConditionTypes.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(ItemOverride::conditions),
            Codec.INT.optionalFieldOf("priority", 0).forGetter(ItemOverride::priority)
    ).apply(instance, ItemOverride::new));

}