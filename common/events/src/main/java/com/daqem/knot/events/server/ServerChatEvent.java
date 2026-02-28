package com.daqem.knot.events.server;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import com.daqem.knot.events.EventResult;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.apache.commons.lang3.mutable.MutableObject;

/**
 * Events related to server-side chat processing and decoration.
 */
public interface ServerChatEvent {

    Event<Decorate> DECORATE = EventFactory.createLoop(Decorate.class);
    Event<Received> RECEIVED = EventFactory.createEventResult(Received.class);

    interface Decorate {
        /**
         * Fired when a player sends a message, allowing the server to format or modify it before it gets broadcasted.
         *
         * @param player  The player sending the chat message.
         * @param message A mutable wrapper containing the chat component.
         */
        void onDecorateChat(ServerPlayer player, MutableObject<Component> message);
    }

    interface Received {
        /**
         * Fired when the server receives a chat message from a player.
         * You can cancel this event to prevent the message from being broadcasted.
         *
         * @param player  The player sending the chat message.
         * @param message The chat component.
         * @return EventResult.INTERRUPT to cancel the message entirely, otherwise PASS.
         */
        EventResult onReceiveChat(ServerPlayer player, Component message);
    }
}