package com.daqem.knot.neoforge.registry;

import com.daqem.knot.registry.RegistryEntry;
import net.minecraft.resources.Identifier;
import java.util.function.Supplier;

public class NeoForgeRegistryEntry<T> implements RegistryEntry<T> {

    private final Identifier id;
    private final Supplier<T> factory;
    private T value;

    public NeoForgeRegistryEntry(Identifier id, Supplier<T> factory) {
        this.id = id;
        this.factory = factory;
    }

    public Supplier<T> getFactory() {
        return factory;
    }

    public void resolve(T value) {
        this.value = value;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public T get() {
        if (value == null) {
            throw new IllegalStateException("Registry entry " + id + " has not been registered yet!");
        }
        return value;
    }
}