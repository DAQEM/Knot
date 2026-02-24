package com.daqem.knot.registry.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface KnotEntityAttributes {

    KnotEntityAttributes PROVIDER = ServiceLoader.load(KnotEntityAttributes.class)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No KnotEntityAttributes implementation found on the classpath!"));

    /**
     * Registers default attributes for a custom LivingEntity.
     *
     * @param type       A supplier providing the EntityType (e.g., your RegistryEntry).
     * @param attributes A supplier providing the AttributeSupplier.Builder (e.g., Pig::createAttributes).
     */
    static <T extends LivingEntity> void register(Supplier<? extends EntityType<T>> type, Supplier<AttributeSupplier.Builder> attributes) {
        PROVIDER.registerAttributes(type, attributes);
    }

    <T extends LivingEntity> void registerAttributes(Supplier<? extends EntityType<T>> type, Supplier<AttributeSupplier.Builder> attributes);
}