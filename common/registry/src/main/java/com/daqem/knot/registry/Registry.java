package com.daqem.knot.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Registry<T> {

    /**
     * @return The Mod ID bound to this registry.
     */
    String getModId();

    /**
     * @return The vanilla ResourceKey for this specific registry (e.g., Registries.ITEM).
     */
    ResourceKey<? extends net.minecraft.core.Registry<T>> getRegistryKey();

    /**
     * Registers an entry using a standard supplier.
     * Useful for objects that do not require an ID during instantiation.
     *
     * @param name    The path for the identifier.
     * @param factory A supplier creating the object.
     * @param <I>     The specific type of the object.
     * @return A {@link RegistryEntry} holding the registered object.
     */
    <I extends T> RegistryEntry<I> register(String name, Supplier<I> factory);

    /**
     * Registers an entry using a function that provides the generated ResourceKey.
     * Perfect for 1.21.2+ Items and Blocks which require {@code .setId(key)}.
     *
     * @param name    The path for the identifier.
     * @param factory A function creating the object, accepting its assigned ResourceKey.
     * @param <I>     The specific type of the object.
     * @return A {@link RegistryEntry} holding the registered object.
     */
    default <I extends T> RegistryEntry<I> register(String name, Function<ResourceKey<T>, I> factory) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(getModId(), name);
        ResourceKey<T> key = ResourceKey.create(getRegistryKey(), id);
        return register(name, () -> factory.apply(key));
    }

    /**
     * Finalizes registration.
     * This should be called during your mod's initialization phase.
     */
    void register();
}