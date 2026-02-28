package com.daqem.knot.fabric.registry.entity;

import com.daqem.knot.registry.entity.EntityAttributesRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

public class FabricEntityAttributesRegistry implements EntityAttributesRegistry {

    @Override
    public <T extends LivingEntity> void register(Supplier<? extends EntityType<T>> type, Supplier<AttributeSupplier.Builder> attributes) {
        FabricDefaultAttributeRegistry.register(type.get(), attributes.get());
    }
}