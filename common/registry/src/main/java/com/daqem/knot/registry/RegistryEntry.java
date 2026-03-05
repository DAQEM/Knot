package com.daqem.knot.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public interface RegistryEntry<T> extends Supplier<T> {

    /**
     * @return The identifier this entry is registered under.
     */
    ResourceLocation getId();

    /**
     * @return The specific ResourceKey for this entry (e.g. key for "knot:test_item" in minecraft:item).
     */
    ResourceKey<T> getKey();

    /**
     * @return The registered object. Throws an exception if called before the object is fully registered.
     */
    @Override
    T get();

    /**
     * @return The registered object, or null if it hasn't been registered yet.
     */
    default T getOrNull() {
        try {
            return get();
        } catch (IllegalStateException e) {
            return null;
        }
    }
}