package com.daqem.knot.client.registry;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

/**
 * Service interface for registering entity renderers.
 * <p>
 * Accessed via {@link com.daqem.knot.Knot#ENTITY_RENDERER} or {@link com.daqem.knot.Knot#registerEntityRenderer}.
 * </p>
 */
public interface KnotEntityRendererRegistry {

    /**
     * Registers an entity renderer for a custom EntityType.
     * This must ONLY be called on the client side.
     *
     * @param type     The registered EntityType supplier.
     * @param provider The renderer provider (e.g., PigRenderer::new).
     * @param <T>      The entity type.
     */
    <T extends Entity> void registerRenderer(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider);
}