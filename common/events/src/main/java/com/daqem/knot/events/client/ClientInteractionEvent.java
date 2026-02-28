package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

/**
 * Events related to specific interactions that primarily originate from the client.
 */
public interface ClientInteractionEvent {

    Event<LeftClickAir> LEFT_CLICK_AIR = EventFactory.createLoop(LeftClickAir.class);
    Event<RightClickAir> RIGHT_CLICK_AIR = EventFactory.createLoop(RightClickAir.class);

    interface LeftClickAir {
        void onLeftClickAir(Player player, InteractionHand hand);
    }

    interface RightClickAir {
        void onRightClickAir(Player player, InteractionHand hand);
    }
}