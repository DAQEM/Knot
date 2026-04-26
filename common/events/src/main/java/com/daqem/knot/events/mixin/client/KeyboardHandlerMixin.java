package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.client.ClientRawInputEvent;
import com.daqem.knot.events.client.ClientScreenInputEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

    @Shadow @Final private Minecraft minecraft;

    /**
     * Intercepts the raw key press right at the beginning of the handler.
     */
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void knot$onRawKeyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (windowPointer == this.minecraft.getWindow().getWindow()) {
            if (ClientRawInputEvent.KEY_PRESSED.invoker().onKeyPressed(minecraft, key, scanCode, action, modifiers).cancelsEvent()) {
                ci.cancel();
            }
        }
    }
}