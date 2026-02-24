package com.daqem.knot.test.registry;

import com.daqem.knot.registry.KnotRegistry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.test.Test;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TestItems {
    KnotRegistry<Item> ITEMS = KnotRegistry.create(BuiltInRegistries.ITEM, Test.MOD_ID);

    RegistryEntry<Item> TEST_ITEM = ITEMS.register("test_item",
            key -> new Item(new Item.Properties().setId(key).knot$tab(TestCreativeTabs.TEST_TAB.getKey()))
    );

    RegistryEntry<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block",
            key -> new BlockItem(TestBlocks.TEST_BLOCK.get(), new Item.Properties().setId(key))
    );

    // A food item that gives your custom effect when eaten
    RegistryEntry<Item> TEST_FOOD = ITEMS.register("test_food", key ->
            new Item(new Item.Properties()
                    .setId(key)
                    .food(
                            new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).build(),
                            Consumables.defaultFood()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            List.of(new MobEffectInstance(
                                                    // 1.21.2+ requires Holders for effects
                                                    BuiltInRegistries.MOB_EFFECT.wrapAsHolder(TestMobEffects.TEST_EFFECT.get()),
                                                    200, // Duration in ticks (10 seconds)
                                                    0    // Amplifier (Level 1)
                                            ))
                                    )).build()
                    )
            )
    );

    // A custom item that plays your sound when right-clicked
    RegistryEntry<Item> TEST_HORN = ITEMS.register("test_horn", key ->
            new Item(new Item.Properties().setId(key)) {
                @Override
                public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
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
                    return InteractionResult.SUCCESS;
                }
            }
    );

    static void register() {
        ITEMS.register();
    }
}