package com.daqem.knot.test.registry;

import com.daqem.knot.Knot;
import com.daqem.knot.registry.Registry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.test.Test;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public interface TestItems {
    Registry<Item> ITEMS = Knot.REGISTRAR.createRegistry(BuiltInRegistries.ITEM, Test.MOD_ID);

    RegistryEntry<Item> TEST_ITEM = ITEMS.register("test_item",
            key -> new Item(new Item.Properties().knot$tab(TestCreativeTabs.TEST_TAB.getKey()))
    );

    RegistryEntry<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block",
            key -> new BlockItem(TestBlocks.TEST_BLOCK.get(), new Item.Properties())
    );

    // A food item that gives your custom effect when eaten
    RegistryEntry<Item> TEST_FOOD = ITEMS.register("test_food", key ->
            new Item(new Item.Properties()
                    .food(
                            new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).build()
                    )
            )
    );

    // A custom item that plays your sound when right-clicked
    RegistryEntry<Item> TEST_HORN = ITEMS.register("test_horn", key ->
            new Item(new Item.Properties()) {
                @Override
                public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
                    if (!level.isClientSide()) {
                        // Play the custom sound
                        level.playSound(
                                null,
                                player.blockPosition(),
                                TestSoundEvents.TEST_SOUND.get(),
                                SoundSource.PLAYERS,
                                1.0f, // Volume
                                1.0f  // Pitch
                        );
                    }
                    return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(usedHand));
                }
            }
    );

    static void register() {
        ITEMS.register();
    }
}