package com.daqem.knot.neoforge.registry.client;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.client.ColorHandlerRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgeColorHandlerRegistry implements ColorHandlerRegistry {

    private static final List<BlockColorRegistration> BLOCKS = new ArrayList<>();

    @Override
    public void registerBlockColors(BlockColor color, Block... blocks) {
        BLOCKS.add(new BlockColorRegistration(color, blocks));
    }

    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        for (BlockColorRegistration reg : BLOCKS) {
            event.register(reg.color, reg.blocks);
        }
    }

    private record BlockColorRegistration(BlockColor color, Block[] blocks) {
    }
}