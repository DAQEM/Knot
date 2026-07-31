package com.daqem.knot.test;

import com.daqem.knot.Knot;
import com.daqem.knot.events.EventsService;
import com.daqem.knot.test.event.ServerTickEvent;
import com.daqem.knot.test.network.TestNetworking;
import com.daqem.knot.test.registry.*;
import net.minecraft.core.registries.BuiltInRegistries;

public class Test {

    public static final String MOD_ID = "knot_test";

    public static final Knot API = new Knot(MOD_ID);

    public static void init() {
        TestNetworking.init();
        ServerTickEvent.registerEvent();
        TestRegistries.init();
        EventsService.Player.ENCHANT_ITEM.register((serverPlayer, stack, enchantmentCost) ->
                API.LOGGER.info(
                        "Player {} enchanted item {} with cost {}",
                        serverPlayer.getName().getString(),
                        BuiltInRegistries.ITEM.getKey(stack.getItem()),
                        enchantmentCost
                )
        );
    }
}
