package com.daqem.knot.test.registry;

import com.daqem.knot.registry.KnotRegistry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.registry.entity.KnotEntityAttributes;
import com.daqem.knot.test.Test;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.pig.Pig;
import org.jetbrains.annotations.NotNull;

public interface TestEntityTypes {

    KnotRegistry<EntityType<?>> ENTITY_TYPES = KnotRegistry.create(BuiltInRegistries.ENTITY_TYPE, Test.MOD_ID);

    RegistryEntry<EntityType<@NotNull Pig>> TEST_ENTITY = ENTITY_TYPES.register("test_entity",
            key -> EntityType.Builder.of(Pig::new, MobCategory.CREATURE)
                    .sized(0.9F, 0.9F)
                    .clientTrackingRange(10)
                    .build(key)
    );

    static void register() {
        ENTITY_TYPES.register();
        KnotEntityAttributes.register(TEST_ENTITY, Pig::createAttributes);
    }
}