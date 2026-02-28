package com.daqem.knot.registry.creativetab;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

/**
 * A utility class providing standard ResourceKeys for all Vanilla Creative Mode Tabs.
 * Since the vanilla keys in {@link net.minecraft.world.item.CreativeModeTabs} are private,
 * these safely recreated keys serve as a perfect, mapping-independent alternative.
 */
public final class VanillaTabs {

    public static final ResourceKey<CreativeModeTab> BUILDING_BLOCKS = create("building_blocks");
    public static final ResourceKey<CreativeModeTab> COLORED_BLOCKS = create("colored_blocks");
    public static final ResourceKey<CreativeModeTab> NATURAL_BLOCKS = create("natural_blocks");
    public static final ResourceKey<CreativeModeTab> FUNCTIONAL_BLOCKS = create("functional_blocks");
    public static final ResourceKey<CreativeModeTab> REDSTONE_BLOCKS = create("redstone_blocks");
    public static final ResourceKey<CreativeModeTab> HOTBAR = create("hotbar");
    public static final ResourceKey<CreativeModeTab> SEARCH = create("search");
    public static final ResourceKey<CreativeModeTab> TOOLS_AND_UTILITIES = create("tools_and_utilities");
    public static final ResourceKey<CreativeModeTab> COMBAT = create("combat");
    public static final ResourceKey<CreativeModeTab> FOOD_AND_DRINKS = create("food_and_drinks");
    public static final ResourceKey<CreativeModeTab> INGREDIENTS = create("ingredients");
    public static final ResourceKey<CreativeModeTab> SPAWN_EGGS = create("spawn_eggs");
    public static final ResourceKey<CreativeModeTab> OP_BLOCKS = create("op_blocks");
    public static final ResourceKey<CreativeModeTab> INVENTORY = create("inventory");

    private VanillaTabs() {}

    private static ResourceKey<CreativeModeTab> create(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace(name));
    }
}