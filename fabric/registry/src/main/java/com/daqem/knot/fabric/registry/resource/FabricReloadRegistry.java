package com.daqem.knot.fabric.registry.resource;

import com.daqem.knot.registry.resource.ReloadRegistry;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.impl.resource.ResourceLoaderImpl;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.NotNull;

public class FabricReloadRegistry implements ReloadRegistry {

    @Override
    public void registerData(@NotNull Identifier id, @NotNull PreparableReloadListener listener, @NotNull Identifier... dependencies) {
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(id, listener);
        for (Identifier dependency : dependencies) {
            ResourceLoader.get(PackType.SERVER_DATA).addListenerOrdering(dependency, id);
        }
    }

    @Override
    public void registerAssets(@NotNull Identifier id, @NotNull PreparableReloadListener listener, @NotNull Identifier... dependencies) {
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(id, listener);
        for (Identifier dependency : dependencies) {
            ResourceLoader.get(PackType.CLIENT_RESOURCES).addListenerOrdering(dependency, id);
        }
    }
}