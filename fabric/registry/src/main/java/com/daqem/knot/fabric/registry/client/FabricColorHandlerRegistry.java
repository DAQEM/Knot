package com.daqem.knot.fabric.registry.client;

import com.daqem.knot.registry.client.ColorHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;

public class FabricColorHandlerRegistry implements ColorHandlerRegistry {

    @Override
    public void registerBlockColors(BlockColor color, Block... blocks) {
        ColorProviderRegistry.BLOCK.register(color, blocks);
    }
}