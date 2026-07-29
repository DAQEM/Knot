package com.daqem.knot.fabric.events.mixin;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.server.ServerCommandEvent;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public abstract class CommandsMixin {

    @Inject(method = "performCommand", at = @At("HEAD"), cancellable = true)
    private void knot$onPerformCommand(ParseResults<CommandSourceStack> command, String commandString, CallbackInfo ci) {
        MutableObject<ParseResults<CommandSourceStack>> resultsRef = new MutableObject<>(command);
        MutableObject<Throwable> exceptionRef = new MutableObject<>(null);

        EventResult result = ServerCommandEvent.PERFORM.invoker().onPerform(resultsRef, exceptionRef);

        if (result.cancelsEvent()) {
            ci.cancel();
        }
    }
}