package com.daqem.knot.fabric.registry.client;

import com.daqem.knot.registry.client.RenderTypeRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.impl.client.rendering.BlockRenderLayerMapImpl;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class FabricRenderTypeRegistry implements RenderTypeRegistry {
    @Override
    public void register(ChunkSectionLayer type, Block... blocks) {
        BlockRenderLayerMap.putBlocks(type, blocks);
    }

    @Override
    public void register(ChunkSectionLayer type, Fluid... fluids) {
        BlockRenderLayerMap.putFluids(type, fluids);
    }
}