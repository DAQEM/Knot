package com.daqem.knot.neoforge.registry;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.Registrar;
import com.daqem.knot.registry.Registry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgeRegistrar implements Registrar {

    private static final List<NeoForgeRegistry<?>> REGISTRIES = new ArrayList<>();

    @Override
    public <T> Registry<T> createRegistry(net.minecraft.core.Registry<T> registry, String modId) {
        NeoForgeRegistry<T> neoRegistry = new NeoForgeRegistry<>(registry, modId);
        REGISTRIES.add(neoRegistry);
        return neoRegistry;
    }

    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        for (NeoForgeRegistry<?> registry : REGISTRIES) {
            if (event.getRegistryKey().equals(registry.getRegistryKey())) {
                registry.onRegisterEvent(event);
            }
        }
    }
}