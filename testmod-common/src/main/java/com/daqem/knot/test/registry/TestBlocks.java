package com.daqem.knot.test.registry;

import com.daqem.knot.Knot;
import com.daqem.knot.registry.KnotRegistry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.test.Test;
import com.daqem.knot.test.block.BoxOfSecretsBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface TestBlocks {
    KnotRegistry<Block> BLOCKS = Knot.REGISTRAR.createRegistry(BuiltInRegistries.BLOCK, Test.MOD_ID);

    RegistryEntry<Block> TEST_BLOCK = BLOCKS.register("test_block",
            key -> new Block(BlockBehaviour.Properties.of().setId(key).strength(2.0f))
    );
    RegistryEntry<BoxOfSecretsBlock> BOX_OF_SECRETS = BLOCKS.register("box_of_secrets",
            key -> new BoxOfSecretsBlock(BlockBehaviour.Properties.of().setId(key).strength(3.0f))
    );

    static void register() {
        BLOCKS.register();
    }
}