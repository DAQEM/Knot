package com.daqem.knot.registry.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public interface RenderTypeRegistry {
    void register(RenderType type, Block... blocks);
    void register(RenderType type, Fluid... fluids);
}