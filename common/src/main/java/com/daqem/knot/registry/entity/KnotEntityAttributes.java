package com.daqem.knot.registry.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

/**
 * Service interface for registering entity attributes.
 * <p>
 * Accessed via {@link com.daqem.knot.Knot#ENTITY_ATTRIBUTES} or {@link com.daqem.knot.Knot#registerAttribute}.
 * </p>
 */
public interface KnotEntityAttributes {

    /**
     * Registers default attributes for a custom LivingEntity.
     *
     * @param type       A supplier providing the EntityType.
     * @param attributes A supplier providing the AttributeSupplier.Builder.
     * @param <T>        The entity type.
     */
    <T extends LivingEntity> void registerAttributes(Supplier<? extends EntityType<T>> type, Supplier<AttributeSupplier.Builder> attributes);
}