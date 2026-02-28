package com.daqem.knot.fabric.registry.resource;

import com.daqem.knot.registry.resource.ReloadRegistry;
import net.fabricmc.fabric.impl.resource.ResourceLoaderImpl;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.NotNull;

public class FabricReloadRegistry implements ReloadRegistry {

    @Override
    public void registerData(@NotNull Identifier id, @NotNull PreparableReloadListener listener, @NotNull Identifier... dependencies) {
        ResourceLoaderImpl.get(PackType.SERVER_DATA).registerReloader(id, listener);
        for (Identifier dependency : dependencies) {
            ResourceLoaderImpl.get(PackType.SERVER_DATA).addReloaderOrdering(dependency, id);
        }
    }

    @Override
    public void registerAssets(@NotNull Identifier id, @NotNull PreparableReloadListener listener, @NotNull Identifier... dependencies) {
        ResourceLoaderImpl.get(PackType.CLIENT_RESOURCES).registerReloader(id, listener);
        for (Identifier dependency : dependencies) {
            ResourceLoaderImpl.get(PackType.CLIENT_RESOURCES).addReloaderOrdering(dependency, id);
        }
    }
}