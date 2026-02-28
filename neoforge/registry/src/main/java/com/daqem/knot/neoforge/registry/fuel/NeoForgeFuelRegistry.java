package com.daqem.knot.neoforge.registry.fuel;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.fuel.FuelRegistry;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

import java.util.IdentityHashMap;
import java.util.Map;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeFuelRegistry implements FuelRegistry {

    private static final Map<ItemLike, Integer> FUELS = new IdentityHashMap<>();

    @Override
    public void register(int burnTimeTicks, ItemLike... items) {
        for (ItemLike item : items) {
            FUELS.put(item, burnTimeTicks);
        }
    }

    @SubscribeEvent
    public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        Integer time = FUELS.get(event.getItemStack().getItem());
        if (time != null) {
            event.setBurnTime(time);
        }
    }
}