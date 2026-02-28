package com.daqem.knot.neoforge.registry.client;

import com.daqem.knot.registry.client.RenderTypeRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class NeoForgeRenderTypeRegistry implements RenderTypeRegistry {

    @Override
    public void register(ChunkSectionLayer type, Block... blocks) {
        for (Block block : blocks) {
            ItemBlockRenderTypes.setRenderLayer(block, type);
        }
    }

    @Override
    public void register(ChunkSectionLayer type, Fluid... fluids) {
        for (Fluid fluid : fluids) {
            ItemBlockRenderTypes.setRenderLayer(fluid, type);
        }
    }
}