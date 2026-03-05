package com.daqem.knot.fabric.registry.resource;

import com.daqem.knot.registry.resource.ReloadRegistry;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.NotNull;

public class FabricReloadRegistry implements ReloadRegistry {

    @Override
    public void registerData(@NotNull ResourceLocation id, @NotNull PreparableReloadListener listener, @NotNull ResourceLocation... dependencies) {
        ResourceLoader.get(PackType.SERVER_DATA).registerReloader(id, listener);
        for (ResourceLocation dependency : dependencies) {
            ResourceLoader.get(PackType.SERVER_DATA).addReloaderOrdering(dependency, id);
        }
    }

    @Override
    public void registerAssets(@NotNull ResourceLocation id, @NotNull PreparableReloadListener listener, @NotNull ResourceLocation... dependencies) {
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(id, listener);
        for (ResourceLocation dependency : dependencies) {
            ResourceLoader.get(PackType.CLIENT_RESOURCES).addReloaderOrdering(dependency, id);
        }
    }
}