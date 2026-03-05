package com.daqem.knot.fabric.registry.resource;

import com.daqem.knot.registry.resource.ReloadRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FabricReloadRegistry implements ReloadRegistry {

    @Override
    public void registerData(@NotNull ResourceLocation id, @NotNull PreparableReloadListener listener, @NotNull ResourceLocation... dependencies) {
        register(PackType.SERVER_DATA, id, listener, dependencies);
    }

    @Override
    public void registerAssets(@NotNull ResourceLocation id, @NotNull PreparableReloadListener listener, @NotNull ResourceLocation... dependencies) {
        register(PackType.CLIENT_RESOURCES, id, listener, dependencies);
    }

    public static void register(PackType type, @NotNull ResourceLocation id, @NotNull PreparableReloadListener listener, @NotNull ResourceLocation... dependencies) {
        ResourceManagerHelper.get(type).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return id;
            }

            @Override
            public @NotNull String getName() {
                return listener.getName();
            }

            @Override
            public Collection<ResourceLocation> getFabricDependencies() {
                return new ArrayList<>(List.of(dependencies));
            }

            @Override
            public @NotNull CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, Executor backgroundExecutor, Executor gameExecutor) {
                return listener.reload(barrier, manager, backgroundExecutor, gameExecutor);
            }
        });
    }
}