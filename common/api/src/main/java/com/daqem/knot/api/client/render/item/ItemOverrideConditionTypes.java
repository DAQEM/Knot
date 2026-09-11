package com.daqem.knot.api.client.render.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ItemOverrideConditionTypes {
    private static final Map<Identifier, MapCodec<? extends ItemOverrideCondition>> CODECS = new HashMap<>();

    public static <T extends ItemOverrideCondition> MapCodec<T> register(Identifier id, MapCodec<T> codec) {
        CODECS.put(id, codec);
        return codec;
    }

    public static final Codec<ItemOverrideCondition> CODEC = Identifier.CODEC.dispatch(
            ItemOverrideCondition::getTypeId,
            CODECS::get
    );
}