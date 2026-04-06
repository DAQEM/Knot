package com.daqem.knot.registry.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface ColorHandlerRegistry {
    void registerBlockColors(List<BlockTintSource> layers, Block... blocks);
}