package com.daqem.knot.neoforge.registry.client;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.client.ColorHandlerRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeColorHandlerRegistry implements ColorHandlerRegistry {

    private static final List<BlockColorRegistration> BLOCKS = new ArrayList<>();

    @Override
    public void registerBlockColors(List<BlockTintSource> layers, Block... blocks) {
        BLOCKS.add(new BlockColorRegistration(layers, blocks));
    }

    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
        for (BlockColorRegistration reg : BLOCKS) {
            event.register(reg.layers, reg.blocks);
        }
    }

    private record BlockColorRegistration(List<BlockTintSource> layers, Block[] blocks) {
    }
}