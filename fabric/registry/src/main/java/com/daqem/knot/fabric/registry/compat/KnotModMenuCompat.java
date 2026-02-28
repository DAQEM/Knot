package com.daqem.knot.fabric.registry.compat;

import com.daqem.knot.fabric.registry.client.FabricConfigScreenRegistry;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import java.util.Map;
import java.util.stream.Collectors;

public class KnotModMenuCompat implements ModMenuApi {

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        return FabricConfigScreenRegistry.FACTORIES.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()::apply));
    }
}