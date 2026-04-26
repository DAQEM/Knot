package com.daqem.knot.fabric.registry.fuel;

import com.daqem.knot.registry.fuel.FuelRegistry;
import net.minecraft.world.level.ItemLike;

public class FabricFuelRegistry implements FuelRegistry {

    @Override
    public void register(int burnTimeTicks, ItemLike... items) {
        for (ItemLike item : items) {
            net.fabricmc.fabric.api.registry.FuelRegistry.INSTANCE.add(item.asItem(), burnTimeTicks);
        }
    }
}