package com.daqem.knot.fabric.registry.client;

import com.daqem.knot.registry.client.BlockEntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class FabricBlockEntityRendererRegistry implements BlockEntityRendererRegistry {

    @Override
    public <T extends BlockEntity> void bind(Supplier<BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T> provider) {
        BlockEntityRenderers.register(type.get(), provider);
    }
}