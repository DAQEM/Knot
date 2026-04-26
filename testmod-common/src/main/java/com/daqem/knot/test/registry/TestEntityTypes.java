package com.daqem.knot.test.registry;

import com.daqem.knot.Knot;
import com.daqem.knot.registry.Registry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.test.Test;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Pig;
import org.jetbrains.annotations.NotNull;

public interface TestEntityTypes {

    Registry<EntityType<?>> ENTITY_TYPES = Knot.REGISTRAR.createRegistry(BuiltInRegistries.ENTITY_TYPE, Test.MOD_ID);

    RegistryEntry<EntityType<@NotNull Pig>> TEST_ENTITY = ENTITY_TYPES.register("test_entity",
            key -> EntityType.Builder.of(Pig::new, MobCategory.CREATURE)
                    .sized(0.9F, 0.9F)
                    .clientTrackingRange(10)
                    .build(key.location().toString())
    );

    static void register() {
        ENTITY_TYPES.register();
        Knot.ENTITY_ATTRIBUTES_REGISTRY.register(TEST_ENTITY, Pig::createAttributes);
    }
}