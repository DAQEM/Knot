package com.daqem.knot.neoforge.registry;

import com.daqem.knot.KnotMod;
import com.daqem.knot.registry.KnotRegistrar;
import com.daqem.knot.registry.KnotRegistry;
import net.minecraft.core.Registry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = KnotMod.MOD_ID)
public class NeoForgeKnotRegistrar implements KnotRegistrar {

    private static final List<NeoForgeKnotRegistry<?>> REGISTRIES = new ArrayList<>();

    @Override
    public <T> KnotRegistry<T> createRegistry(Registry<T> registry, String modId) {
        NeoForgeKnotRegistry<T> neoRegistry = new NeoForgeKnotRegistry<>(registry, modId);
        REGISTRIES.add(neoRegistry);
        return neoRegistry;
    }

    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        for (NeoForgeKnotRegistry<?> registry : REGISTRIES) {
            if (event.getRegistryKey().equals(registry.getRegistryKey())) {
                registry.onRegisterEvent(event);
            }
        }
    }
}