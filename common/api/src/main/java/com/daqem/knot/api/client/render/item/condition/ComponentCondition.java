package com.daqem.knot.api.client.render.item.condition;

import com.daqem.knot.api.client.render.item.ItemOverrideCondition;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public record ComponentCondition<T>(DataComponentType<T> componentType, Optional<T> matchValue) implements ItemOverrideCondition {

    public static final Identifier ID = Identifier.fromNamespaceAndPath("knot", "component");

    public static final MapCodec<ComponentCondition<?>> CODEC = new MapCodec<>() {
        @Override
        public <V> Stream<V> keys(DynamicOps<V> ops) {
            return Stream.of(ops.createString("component"), ops.createString("value"));
        }

        @Override
        public <V> DataResult<ComponentCondition<?>> decode(DynamicOps<V> ops, MapLike<V> input) {
            V componentObj = input.get("component");
            if (componentObj == null) {
                return DataResult.error(() -> "Missing 'component' field");
            }

            return Identifier.CODEC.decode(ops, componentObj).flatMap(idPair -> {
                Identifier id = idPair.getFirst();

                var typeHolder = BuiltInRegistries.DATA_COMPONENT_TYPE.get(id);
                if (typeHolder.isEmpty()) {
                    return DataResult.error(() -> "Unknown component: " + id);
                }
                DataComponentType<?> type = typeHolder.get().value();

                V valueObj = input.get("value");
                if (valueObj == null) {
                    return DataResult.success(createEmptyCondition(type));
                }

                Codec<?> valueCodec = type.codec();
                if (valueCodec == null) {
                    return DataResult.error(() -> "Component " + id + " is transient and cannot be matched by value");
                }

                return decodeValue(valueCodec, ops, valueObj).map(val -> createCondition(type, val));
            });
        }

        private <V, E> DataResult<E> decodeValue(Codec<E> codec, DynamicOps<V> ops, V valueObj) {
            return codec.decode(ops, valueObj).map(Pair::getFirst);
        }

        @Override
        public <V> RecordBuilder<V> encode(ComponentCondition<?> input, DynamicOps<V> ops, RecordBuilder<V> prefix) {
            Identifier id = Objects.requireNonNull(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(input.componentType()), "Unregistered component type");
            prefix.add("component", Identifier.CODEC.encodeStart(ops, id).getOrThrow());

            if (input.matchValue().isPresent()) {
                encodeValue(input, ops, prefix);
            }
            return prefix;
        }

        private <V, E> void encodeValue(ComponentCondition<E> input, DynamicOps<V> ops, RecordBuilder<V> prefix) {
            Codec<E> codec = input.componentType().codecOrThrow();
            prefix.add("value", codec.encodeStart(ops, input.matchValue().orElseThrow()).getOrThrow());
        }
    };

    @SuppressWarnings("unchecked")
    private static <T> ComponentCondition<T> createCondition(DataComponentType<?> type, Object value) {
        return new ComponentCondition<>((DataComponentType<T>) type, Optional.of((T) value));
    }

    @SuppressWarnings("unchecked")
    private static <T> ComponentCondition<T> createEmptyCondition(DataComponentType<?> type) {
        return new ComponentCondition<>((DataComponentType<T>) type, Optional.empty());
    }

    @Override
    public Identifier getTypeId() {
        return ID;
    }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        if (!stack.has(componentType)) {
            return false;
        }

        if (matchValue.isEmpty()) {
            return true;
        }

        T componentValue = stack.get(componentType);
        return matchValue.get().equals(componentValue);
    }
}