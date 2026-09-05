package com.daqem.knot.test.neoforge.datagen;

import com.daqem.knot.test.Test;
import net.minecraft.core.RegistrySetBuilder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.LoggerFactory;

@EventBusSubscriber(modid = Test.MOD_ID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Server event) {
        LoggerFactory.getLogger(DataGenerators.class).info("Generating data for TestModNeoForge");
        event.createDatapackRegistryObjects(new RegistrySetBuilder());
        event.createProvider(TestRecipeProvider.Runner::new);
    }
}