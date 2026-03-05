package com.daqem.knot.test.registry;

import com.daqem.knot.Knot;
import com.daqem.knot.registry.Registry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.test.Test;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public interface TestSoundEvents {

    Registry<SoundEvent> SOUNDS = Knot.REGISTRAR.createRegistry(BuiltInRegistries.SOUND_EVENT, Test.MOD_ID);

    RegistryEntry<SoundEvent> TEST_SOUND = SOUNDS.register("test_sound",
            key -> SoundEvent.createVariableRangeEvent(key.location())
    );

    static void register() {
        SOUNDS.register();
    }
}