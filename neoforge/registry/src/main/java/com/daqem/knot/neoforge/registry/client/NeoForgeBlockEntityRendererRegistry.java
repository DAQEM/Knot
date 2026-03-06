package com.daqem.knot.neoforge.registry.client;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.client.BlockEntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgeBlockEntityRendererRegistry implements BlockEntityRendererRegistry {

    private static final Map<Supplier<? extends BlockEntityType<?>>, BlockEntityRendererProvider<?>> PENDING_BINDINGS = new ConcurrentHashMap<>();

    @Override
    public <T extends BlockEntity> void bind(Supplier<BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T> provider) {
        PENDING_BINDINGS.put(type, provider);
    }

    @SubscribeEvent
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        PENDING_BINDINGS.forEach((typeSupplier, provider) ->
                event.registerBlockEntityRenderer((BlockEntityType) typeSupplier.get(), (BlockEntityRendererProvider) provider));
        PENDING_BINDINGS.clear();
    }


}