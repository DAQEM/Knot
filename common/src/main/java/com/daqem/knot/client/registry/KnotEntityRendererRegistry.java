package com.daqem.knot.client.registry;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface KnotEntityRendererRegistry {

    KnotEntityRendererRegistry PROVIDER = ServiceLoader.load(KnotEntityRendererRegistry.class)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No KnotEntityRendererRegistry implementation found on the classpath!"));

    /**
     * Registers an entity renderer for a custom EntityType.
     * This must ONLY be called on the client side.
     *
     * @param type     The registered EntityType supplier.
     * @param provider The renderer provider (e.g., PigRenderer::new).
     */
    static <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider) {
        PROVIDER.registerRenderer(type, provider);
    }

    <T extends Entity> void registerRenderer(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider);
}