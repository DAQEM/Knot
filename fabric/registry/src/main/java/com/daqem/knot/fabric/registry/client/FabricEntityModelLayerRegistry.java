package com.daqem.knot.fabric.registry.client;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.function.Supplier;

public class FabricEntityModelLayerRegistry implements com.daqem.knot.registry.client.EntityModelLayerRegistry {

    @Override
    public void register(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
        ModelLayerRegistry.registerModelLayer(location, definition::get);
    }
}