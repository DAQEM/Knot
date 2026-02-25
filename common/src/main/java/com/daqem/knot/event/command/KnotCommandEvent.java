package com.daqem.knot.event.common;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import com.daqem.knot.event.EventResult;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.apache.commons.lang3.mutable.MutableObject;

public interface KnotCommandEvent {

    Event<Register> REGISTER = EventFactory.createLoop(Register.class);
    Event<Perform> PERFORM = EventFactory.createEventResult(Perform.class);

    interface Register {
        /**
         * Fired when the server registers its commands.
         */
        void onRegister(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment);
    }

    interface Perform {
        /**
         * Fired before a command is executed.
         * You can modify the ParseResults or exception, or cancel the event entirely to suppress execution.
         *
         * @param results A mutable reference to the parsed command results.
         * @param exception A mutable reference to an exception, if one occurred during parsing.
         * @return EventResult.INTERRUPT to cancel command execution.
         */
        EventResult onPerform(MutableObject<ParseResults<CommandSourceStack>> results, MutableObject<Throwable> exception);
    }
}