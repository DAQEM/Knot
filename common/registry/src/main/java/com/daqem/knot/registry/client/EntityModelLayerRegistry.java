package com.daqem.knot.registry.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import java.util.function.Supplier;

public interface EntityModelLayerRegistry {

    void register(ModelLayerLocation location, Supplier<LayerDefinition> definition);

}