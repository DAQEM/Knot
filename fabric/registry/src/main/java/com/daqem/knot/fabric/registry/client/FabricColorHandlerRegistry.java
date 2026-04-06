package com.daqem.knot.fabric.registry.client;

import com.daqem.knot.registry.client.ColorHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class FabricColorHandlerRegistry implements ColorHandlerRegistry {

    @Override
    public void registerBlockColors(List<BlockTintSource> layers, Block... blocks) {
        BlockColorRegistry.register(layers,blocks);
    }
}