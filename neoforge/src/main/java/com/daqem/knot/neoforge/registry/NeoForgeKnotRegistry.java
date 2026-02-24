package com.daqem.knot.neoforge.registry;

import com.daqem.knot.registry.KnotRegistry;
import com.daqem.knot.registry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NeoForgeKnotRegistry<T> implements KnotRegistry<T> {

    private final ResourceKey<? extends Registry<T>> registryKey;
    private final String modId;
    private final Map<Identifier, NeoForgeRegistryEntry<T>> entries = new LinkedHashMap<>();

    public NeoForgeKnotRegistry(Registry<T> registry, String modId) {
        this.registryKey = registry.key();
        this.modId = modId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <I extends T> RegistryEntry<I> register(String name, Supplier<I> factory) {
        Identifier id = Identifier.fromNamespaceAndPath(modId, name);
        NeoForgeRegistryEntry<I> entry = new NeoForgeRegistryEntry<>(id, factory);
        entries.put(id, (NeoForgeRegistryEntry<T>) entry);
        return entry;
    }

    @Override
    public void register() {
        // In NeoForge, the act of registration is passively picked up during the RegisterEvent hook handled by the Registrar.
        // This is safe to leave empty, maintaining unified API parity.
    }

    public void onRegisterEvent(RegisterEvent event) {
        event.register(this.registryKey, helper -> {
            entries.forEach((id, entry) -> {
                T instance = entry.getFactory().get();
                helper.register(id, instance);
                entry.resolve(instance);
            });
        });
    }

    @Override
    public String getModId() {
        return this.modId;
    }

    @Override
    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return this.registryKey;
    }
}