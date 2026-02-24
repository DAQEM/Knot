package com.daqem.knot.fabric.registry;

import com.daqem.knot.registry.KnotRegistry;
import com.daqem.knot.registry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FabricKnotRegistry<T> implements KnotRegistry<T> {

    private final Registry<T> registry;
    private final String modId;
    private final Map<Identifier, FabricRegistryEntry<T>> entries = new LinkedHashMap<>();
    private boolean registered = false;

    public FabricKnotRegistry(Registry<T> registry, String modId) {
        this.registry = registry;
        this.modId = modId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <I extends T> RegistryEntry<I> register(String name, Supplier<I> factory) {
        if (registered) {
            throw new IllegalStateException("Cannot register new entries after registry has been initialized.");
        }
        Identifier id = Identifier.fromNamespaceAndPath(modId, name);
        ResourceKey<? extends Registry<I>> specificRegistryKey = (ResourceKey<? extends Registry<I>>) this.registry.key();
        FabricRegistryEntry<I> entry = new FabricRegistryEntry<>(specificRegistryKey, id, factory);
        entries.put(id, (FabricRegistryEntry<T>) entry);
        return entry;
    }

    @Override
    public void register() {
        if (registered) return;
        registered = true;

        entries.forEach((id, entry) -> {
            T instance = entry.getFactory().get();
            Registry.register(this.registry, id, instance);
            entry.resolve(instance);
        });
    }

    @Override
    public String getModId() {
        return this.modId;
    }

    @Override
    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return this.registry.key();
    }
}