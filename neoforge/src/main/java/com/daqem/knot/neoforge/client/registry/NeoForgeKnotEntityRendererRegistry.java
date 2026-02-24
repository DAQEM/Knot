package com.daqem.knot.neoforge.client.registry;

import com.daqem.knot.KnotMod;
import com.daqem.knot.client.registry.KnotEntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@EventBusSubscriber(modid = KnotMod.MOD_ID, value = Dist.CLIENT)
public class NeoForgeKnotEntityRendererRegistry implements KnotEntityRendererRegistry {

    private static final Map<Supplier<? extends EntityType<?>>, EntityRendererProvider<?>> RENDERERS = new ConcurrentHashMap<>();

    @Override
    public <T extends Entity> void registerRenderer(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider) {
        RENDERERS.put(type, provider);
    }

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        RENDERERS.forEach((typeSupplier, provider) -> {
            event.registerEntityRenderer(typeSupplier.get(), (EntityRendererProvider<@NotNull Entity>) provider);
        });
    }
}