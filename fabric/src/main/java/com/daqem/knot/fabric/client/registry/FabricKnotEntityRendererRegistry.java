package com.daqem.knot.fabric.client.registry;

import com.daqem.knot.client.registry.KnotEntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class FabricKnotEntityRendererRegistry implements KnotEntityRendererRegistry {

    @Override
    public <T extends Entity> void registerRenderer(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider) {
        EntityRenderers.register(type.get(), provider);
    }
}