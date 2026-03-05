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

    /**
     * Wraps the screen's key pressed logic.
     */
    @WrapOperation(method = "keyPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z"))
    private boolean knot$onScreenKeyPressed(Screen screen, int keyCode, int scanCode, int modifiers, Operation<Boolean> original) {
        if (ClientScreenInputEvent.KEY_PRESSED_PRE.invoker().onKeyPressed(minecraft, screen, keyCode, scanCode, modifiers).cancelsEvent()) return true;
        boolean result = original.call(screen, keyCode, scanCode, modifiers);
        ClientScreenInputEvent.KEY_PRESSED_POST.invoker().onKeyPressed(minecraft, screen, keyCode, scanCode, modifiers);
        return result;
    }

    /**
     * Wraps the screen's key released logic.
     */
    @WrapOperation(method = "keyPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyReleased(III)Z"))
    private boolean knot$onScreenKeyReleased(Screen screen, int keyCode, int scanCode, int modifiers, Operation<Boolean> original) {
        if (ClientScreenInputEvent.KEY_RELEASED_PRE.invoker().onKeyReleased(minecraft, screen, keyCode, scanCode, modifiers).cancelsEvent()) return true;
        boolean result = original.call(screen, keyCode, scanCode, modifiers);
        ClientScreenInputEvent.KEY_RELEASED_POST.invoker().onKeyReleased(minecraft, screen, keyCode, scanCode, modifiers);
        return result;
    }

    /**
     * Wraps the screen's char typed logic.
     */
    @WrapOperation(method = "charTyped", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;charTyped(CI)Z"))
    private boolean knot$onScreenCharTyped(Screen screen, char codepoint, int modifiers, Operation<Boolean> original) {
        // We cast codepoint to char to seamlessly support the KnotClientScreenInputEvent interface
        if (ClientScreenInputEvent.CHAR_TYPED_PRE.invoker().onCharTyped(minecraft, screen, codepoint, modifiers).cancelsEvent()) return true;
        boolean result = original.call(screen, codepoint, modifiers);
        ClientScreenInputEvent.CHAR_TYPED_POST.invoker().onCharTyped(minecraft, screen, codepoint, modifiers);
        return result;
    }
}