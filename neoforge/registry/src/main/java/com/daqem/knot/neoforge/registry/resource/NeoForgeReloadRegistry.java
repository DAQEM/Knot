package com.daqem.knot.neoforge.registry.resource;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.resource.ReloadRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeReloadRegistry implements ReloadRegistry {

    private static final Map<ResourceLocation, ReloadListener> DATA_LISTENERS = new HashMap<>();
    private static final Map<ResourceLocation, ReloadListener> ASSET_LISTENERS = new HashMap<>();

    @Override
    public void registerData(@NotNull ResourceLocation id, @NotNull PreparableReloadListener listener, @NotNull ResourceLocation... dependencies) {
        DATA_LISTENERS.put(id, new ReloadListener(listener, dependencies));
    }

    @Override
    public void registerAssets(@NotNull ResourceLocation id, @NotNull PreparableReloadListener listener, @NotNull ResourceLocation... dependencies) {
        ASSET_LISTENERS.put(id, new ReloadListener(listener, dependencies));
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddServerReloadListenersEvent event) {
        for (Map.Entry<ResourceLocation, ReloadListener> entry : DATA_LISTENERS.entrySet()) {
            ResourceLocation id = entry.getKey();
            ReloadListener reloadListener = entry.getValue();
            event.addListener(id, reloadListener.listener());
            for (ResourceLocation dependency : reloadListener.dependencies()) {
                event.addDependency(dependency, id);
            }
        }
        DATA_LISTENERS.clear();
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onRegisterClientReloadListeners(AddClientReloadListenersEvent event) {
            for (Map.Entry<ResourceLocation, ReloadListener> entry : ASSET_LISTENERS.entrySet()) {
                ResourceLocation id = entry.getKey();
                ReloadListener reloadListener = entry.getValue();
                event.addListener(id, reloadListener.listener());
                for (ResourceLocation dependency : reloadListener.dependencies()) {
                    event.addDependency(dependency, id);
                }
            }
            ASSET_LISTENERS.clear();
        }
    }

    private record ReloadListener(PreparableReloadListener listener, ResourceLocation[] dependencies) {
    }
}