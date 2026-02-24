package com.daqem.knot.neoforge.registry.entity;

import com.daqem.knot.KnotMod;
import com.daqem.knot.registry.entity.KnotEntityAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@EventBusSubscriber(modid = KnotMod.MOD_ID)
public class NeoForgeKnotEntityAttributes implements KnotEntityAttributes {

    private static final Map<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> ATTRIBUTES = new ConcurrentHashMap<>();

    @Override
    public <T extends LivingEntity> void registerAttributes(Supplier<? extends EntityType<T>> type, Supplier<AttributeSupplier.Builder> attributes) {
        ATTRIBUTES.put(type, attributes);
    }

    @SubscribeEvent
    public static void onAttributeCreation(EntityAttributeCreationEvent event) {
        ATTRIBUTES.forEach((typeSupplier, attributeSupplier) -> {
            event.put(typeSupplier.get(), attributeSupplier.get().build());
        });
    }
}