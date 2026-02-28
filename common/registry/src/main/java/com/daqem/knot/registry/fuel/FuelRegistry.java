package com.daqem.knot.registry.fuel;

import net.minecraft.world.level.ItemLike;

public interface FuelRegistry {
    void register(int burnTimeTicks, ItemLike... items);
}