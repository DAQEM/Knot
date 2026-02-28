package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import com.daqem.knot.events.EventResult;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.mutable.MutableObject;

/**
 * Events related to client-side chat reception, rendering, and sending.
 */
public interface ClientChatEvent {

    Event<Send> SEND = EventFactory.createEventResult(Send.class);
    Event<Receive> RECEIVE = EventFactory.createEventResult(Receive.class);
    Event<SystemMessage> SYSTEM_MESSAGE = EventFactory.createEventResult(SystemMessage.class);

    interface Send {
        /**
         * Fired before the client attempts to send a chat message to the server.
         *
         * @param message A mutable wrapper containing the raw string being sent.
         * @return EventResult.INTERRUPT to cancel the message from sending, otherwise PASS.
         */
        EventResult onSendChat(MutableObject<String> message);
    }

    interface Receive {
        /**
         * Fired when the client receives a chat message from another player.
         *
         * @param type    The bound chat type detailing where the message came from.
         * @param message A mutable wrapper containing the component.
         * @return EventResult.INTERRUPT to hide the message entirely, otherwise PASS.
         */
        EventResult onReceiveChat(ChatType.Bound type, MutableObject<Component> message);
    }

    interface SystemMessage {
        /**
         * Fired when the client receives a system message (e.g. Command feedback, Server announcements).
         *
         * @param message A mutable wrapper containing the component.
         * @return EventResult.INTERRUPT to hide the message entirely, otherwise PASS.
         */
        EventResult onSystemMessage(MutableObject<Component> message);
    }
}