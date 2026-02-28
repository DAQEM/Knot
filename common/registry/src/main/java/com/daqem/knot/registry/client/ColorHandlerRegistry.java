package com.daqem.knot.registry.client;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;

public interface ColorHandlerRegistry {
    void registerBlockColors(BlockColor color, Block... blocks);
}