package com.daqem.knot.event.client;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

/**
 * Events related to specific interactions that primarily originate from the client.
 */
public interface KnotClientInteractionEvent {

    Event<LeftClickAir> LEFT_CLICK_AIR = EventFactory.createLoop(LeftClickAir.class);
    Event<RightClickAir> RIGHT_CLICK_AIR = EventFactory.createLoop(RightClickAir.class);

    interface LeftClickAir {
        void onLeftClickAir(Player player, InteractionHand hand);
    }

    interface RightClickAir {
        void onRightClickAir(Player player, InteractionHand hand);
    }
}