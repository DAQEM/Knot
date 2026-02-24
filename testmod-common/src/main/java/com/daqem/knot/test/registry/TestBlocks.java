package com.daqem.knot.test.registry;

import com.daqem.knot.registry.KnotRegistry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.test.Test;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface TestBlocks {
    KnotRegistry<Block> BLOCKS = KnotRegistry.create(BuiltInRegistries.BLOCK, Test.MOD_ID);

    RegistryEntry<Block> TEST_BLOCK = BLOCKS.register("test_block",
            key -> new Block(BlockBehaviour.Properties.of().setId(key).strength(2.0f))
    );

    static void register() {
        BLOCKS.register();
    }
}