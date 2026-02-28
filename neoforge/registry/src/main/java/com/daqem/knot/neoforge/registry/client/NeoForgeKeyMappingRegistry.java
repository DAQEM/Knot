package com.daqem.knot.neoforge.registry.client;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.client.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeKeyMappingRegistry implements KeyMappingRegistry {

    private static final List<KeyMapping> MAPPINGS = new ArrayList<>();

    @Override
    public void register(KeyMapping mapping) {
        MAPPINGS.add(mapping);
    }

    @SubscribeEvent
    public static void onRegister(RegisterKeyMappingsEvent event) {
        MAPPINGS.forEach(event::register);
    }
}