package com.daqem.knot.test.registry;

import com.daqem.knot.Knot;
import com.daqem.knot.registry.KnotRegistry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.test.Test;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public interface TestMobEffects {
    KnotRegistry<MobEffect> MOB_EFFECTS = Knot.REGISTRAR.createRegistry(BuiltInRegistries.MOB_EFFECT, Test.MOD_ID);

    RegistryEntry<MobEffect> TEST_EFFECT = MOB_EFFECTS.register("test_effect",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x98D982) {}
    );

    static void register() {
        MOB_EFFECTS.register();
    }
}