package com.daqem.knot.fabric.mixin;

import com.daqem.knot.event.common.KnotCommandEvent;
import com.daqem.knot.event.EventResult;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public class MixinCommands {

    @Inject(method = "performCommand", at = @At("HEAD"), cancellable = true)
    private void knot$onPerformCommand(ParseResults<CommandSourceStack> parseResults, String command, CallbackInfo ci) {
        MutableObject<ParseResults<CommandSourceStack>> resultsRef = new MutableObject<>(parseResults);
        MutableObject<Throwable> exceptionRef = new MutableObject<>(null);

        EventResult result = KnotCommandEvent.PERFORM.invoker().onPerform(resultsRef, exceptionRef);

        if (result.cancelsEvent()) {
            ci.cancel();
        }
    }
}