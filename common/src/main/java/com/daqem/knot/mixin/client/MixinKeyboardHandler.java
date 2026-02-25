package com.daqem.knot.mixin.client;

import com.daqem.knot.event.client.KnotClientRawInputEvent;
import com.daqem.knot.event.client.KnotClientScreenInputEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class MixinKeyboardHandler {

    @Shadow @Final private Minecraft minecraft;

    /**
     * Intercepts the raw key press right at the beginning of the handler.
     */
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void knot$onRawKeyPress(long windowPointer, int action, KeyEvent event, CallbackInfo ci) {
        if (windowPointer == this.minecraft.getWindow().handle()) {
            if (KnotClientRawInputEvent.KEY_PRESSED.invoker().onKeyPressed(minecraft, event.key(), event.scancode(), action, event.modifiers()).cancelsEvent()) {
                ci.cancel();
            }
        }
    }

    /**
     * Wraps the screen's key pressed logic.
     */
    @WrapOperation(method = "keyPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(Lnet/minecraft/client/input/KeyEvent;)Z"))
    private boolean knot$onScreenKeyPressed(Screen screen, KeyEvent event, Operation<Boolean> original) {
        if (KnotClientScreenInputEvent.KEY_PRESSED_PRE.invoker().onKeyPressed(minecraft, screen, event.key(), event.scancode(), event.modifiers()).cancelsEvent()) return true;
        boolean result = original.call(screen, event);
        KnotClientScreenInputEvent.KEY_PRESSED_POST.invoker().onKeyPressed(minecraft, screen, event.key(), event.scancode(), event.modifiers());
        return result;
    }

    /**
     * Wraps the screen's key released logic.
     */
    @WrapOperation(method = "keyPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyReleased(Lnet/minecraft/client/input/KeyEvent;)Z"))
    private boolean knot$onScreenKeyReleased(Screen screen, KeyEvent event, Operation<Boolean> original) {
        if (KnotClientScreenInputEvent.KEY_RELEASED_PRE.invoker().onKeyReleased(minecraft, screen, event.key(), event.scancode(), event.modifiers()).cancelsEvent()) return true;
        boolean result = original.call(screen, event);
        KnotClientScreenInputEvent.KEY_RELEASED_POST.invoker().onKeyReleased(minecraft, screen, event.key(), event.scancode(), event.modifiers());
        return result;
    }

    /**
     * Wraps the screen's char typed logic.
     */
    @WrapOperation(method = "charTyped", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;charTyped(Lnet/minecraft/client/input/CharacterEvent;)Z"))
    private boolean knot$onScreenCharTyped(Screen screen, CharacterEvent event, Operation<Boolean> original) {
        // We cast codepoint to char to seamlessly support the KnotClientScreenInputEvent interface
        if (KnotClientScreenInputEvent.CHAR_TYPED_PRE.invoker().onCharTyped(minecraft, screen, (char) event.codepoint(), event.modifiers()).cancelsEvent()) return true;
        boolean result = original.call(screen, event);
        KnotClientScreenInputEvent.CHAR_TYPED_POST.invoker().onCharTyped(minecraft, screen, (char) event.codepoint(), event.modifiers());
        return result;
    }
}