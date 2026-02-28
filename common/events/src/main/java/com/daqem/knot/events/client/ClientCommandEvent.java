package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import com.mojang.brigadier.CommandDispatcher;

public interface ClientCommandEvent {

    Event<Register> REGISTER = EventFactory.createLoop(Register.class);

    interface Register {
        void onRegister(CommandDispatcher<?> dispatcher);
    }
}