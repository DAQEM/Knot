package com.daqem.knot.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;
import java.util.function.Supplier;

public interface KnotRegistry<T> {

    static <T> KnotRegistry<T> create(Registry<T> registry, String modId) {
        return KnotRegistrar.PROVIDER.createRegistry(registry, modId);
    }

    /**
     * @return The Mod ID bound to this registry.
     */
    String getModId();

    /**
     * @return The vanilla ResourceKey for this specific registry (e.g., Registries.ITEM)
     */
    ResourceKey<? extends Registry<T>> getRegistryKey();

    /**
     * Registers an entry using a standard supplier.
     * Useful for objects that do not require an ID during instantiation.
     */
    <I extends T> RegistryEntry<I> register(String name, Supplier<I> factory);

    /**
     * Registers an entry using a function that provides the generated ResourceKey.
     * Perfect for 1.21.2+ Items and Blocks which require `.setId(key)`.
     */
    default <I extends T> RegistryEntry<I> register(String name, Function<ResourceKey<T>, I> factory) {
        Identifier id = Identifier.fromNamespaceAndPath(getModId(), name);
        ResourceKey<T> key = ResourceKey.create(getRegistryKey(), id);
        return register(name, () -> factory.apply(key));
    }

    void register();
}