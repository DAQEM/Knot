package com.daqem.knot.registry.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public interface EntityRendererRegistry {

    /**
     * Registers an entity renderer for a custom EntityType.
     * This must ONLY be called on the client side.
     *
     * @param type     The registered EntityType supplier.
     * @param provider The renderer provider (e.g., PigRenderer::new).
     * @param <T>      The entity type.
     */
    <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider);
}