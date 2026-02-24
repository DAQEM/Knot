package com.daqem.knot.test.registry;

public interface TestRegistries {

    static void init() {
        TestBlocks.register();
        TestMobEffects.register();
        TestSoundEvents.register();
        TestItems.register();
        TestEntityTypes.register();
        TestCreativeTabs.register();
    }
}
