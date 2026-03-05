package com.daqem.knot.fabric.registry;

import com.daqem.knot.registry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class FabricRegistryEntry<T> implements RegistryEntry<T> {

    private final ResourceLocation id;
    private final ResourceKey<T> key;
    private final Supplier<T> factory;
    private T value;

    public FabricRegistryEntry(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation id, Supplier<T> factory) {
        this.id = id;
        this.key = ResourceKey.create(registryKey, id); // Create key immediately
        this.factory = factory;
    }

    public Supplier<T> getFactory() {
        return factory;
    }

    public void resolve(T value) {
        this.value = value;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public ResourceKey<T> getKey() {
        return key;
    }

    @Override
    public T get() {
        if (value == null) {
            throw new IllegalStateException("Registry entry " + id + " has not been registered yet!");
        }
        return value;
    }
}