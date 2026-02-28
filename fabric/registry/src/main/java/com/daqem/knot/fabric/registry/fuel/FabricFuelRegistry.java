package com.daqem.knot.fabric.registry.fuel;

import com.daqem.knot.registry.fuel.FuelRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.world.level.ItemLike;

public class FabricFuelRegistry implements FuelRegistry {

    @Override
    public void register(int burnTimeTicks, ItemLike... items) {
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            for (ItemLike item : items) {
                builder.add(item, burnTimeTicks);
            }
        });
    }
}