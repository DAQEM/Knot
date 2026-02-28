package com.daqem.knot.registry;

public interface Registrar {

    /**
     * Creates a platform-specific registry wrapper.
     *
     * @param registry The vanilla registry.
     * @param modId    The mod ID for the registry.
     * @param <T>      The type of registry entry.
     * @return A new KnotRegistry instance.
     */
    <T> Registry<T> createRegistry(net.minecraft.core.Registry<T> registry, String modId);
}