package com.daqem.knot.registry.client;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public interface RenderTypeRegistry {
    void register(ChunkSectionLayer type, Block... blocks);
    void register(ChunkSectionLayer type, Fluid... fluids);
}