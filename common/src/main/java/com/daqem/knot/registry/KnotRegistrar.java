package com.daqem.knot.registry;

import net.minecraft.core.Registry;

/**
 * Service interface for creating registry wrappers.
 * <p>
 * Accessed via {@link com.daqem.knot.Knot#REGISTRAR} or {@link com.daqem.knot.Knot#register(Registry)}.
 * </p>
 */
public interface KnotRegistrar {

    /**
     * Creates a platform-specific registry wrapper.
     *
     * @param registry The vanilla registry.
     * @param modId    The mod ID for the registry.
     * @param <T>      The type of registry entry.
     * @return A new KnotRegistry instance.
     */
    <T> KnotRegistry<T> createRegistry(Registry<T> registry, String modId);
}