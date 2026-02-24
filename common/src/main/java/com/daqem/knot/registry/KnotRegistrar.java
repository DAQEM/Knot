package com.daqem.knot.registry;

import net.minecraft.core.Registry;
import java.util.ServiceLoader;

public interface KnotRegistrar {

    KnotRegistrar PROVIDER = ServiceLoader.load(KnotRegistrar.class)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No KnotRegistrar implementation found on the classpath!"));

    <T> KnotRegistry<T> createRegistry(Registry<T> registry, String modId);
}