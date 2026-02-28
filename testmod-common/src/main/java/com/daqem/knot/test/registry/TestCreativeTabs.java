package com.daqem.knot.test.registry;

import com.daqem.knot.Knot;
import com.daqem.knot.registry.Registry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.registry.creativetab.VanillaTabs;
import com.daqem.knot.test.Test;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public interface TestCreativeTabs {

    Registry<CreativeModeTab> TABS = Knot.REGISTRAR.createRegistry(BuiltInRegistries.CREATIVE_MODE_TAB, "my_mod");

    RegistryEntry<CreativeModeTab> TEST_TAB = TABS.register("my_tab", () ->
            Knot.CREATIVE_TABS_REGISTRY.build(Test.API.translatable("tab.my_tab"), () -> new ItemStack(TestItems.TEST_FOOD.get()))
    );

    static void register() {
        TABS.register();

        Knot.CREATIVE_TABS_REGISTRY.modify(VanillaTabs.NATURAL_BLOCKS, populator -> {
            populator.add(TestItems.TEST_ITEM.get());
            populator.addAfter(Items.DIRT, TestItems.TEST_BLOCK_ITEM.get());
            populator.addBefore(Items.DIRT, TestItems.TEST_HORN.get());
        });
    }
}
