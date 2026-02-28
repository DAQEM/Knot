package com.daqem.knot.registry.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

public interface EntityAttributesRegistry {

    /**
     * Registers default attributes for a custom LivingEntity.
     *
     * @param type       A supplier providing the EntityType.
     * @param attributes A supplier providing the AttributeSupplier.Builder.
     * @param <T>        The entity type.
     */
    <T extends LivingEntity> void register(Supplier<? extends EntityType<T>> type, Supplier<AttributeSupplier.Builder> attributes);
}