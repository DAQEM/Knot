package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.client.ClientScreenInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContainerEventHandler.class)
public interface ContainerEventHandlerMixin {

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void knot$mouseClickedPre(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            if (ClientScreenInputEvent.MOUSE_CLICKED_PRE.invoker().onMouseClicked(minecraft, screen, mouseX, mouseY, button).cancelsEvent()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void knot$mouseClickedPost(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientScreenInputEvent.MOUSE_CLICKED_POST.invoker().onMouseClicked(minecraft, screen, mouseX, mouseY, button);
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"), cancellable = true)
    private void knot$mouseReleasedPre(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            if (ClientScreenInputEvent.MOUSE_RELEASED_PRE.invoker().onMouseReleased(minecraft, screen, mouseX, mouseY, button).cancelsEvent()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "mouseReleased", at = @At("RETURN"))
    private void knot$mouseReleasedPost(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientScreenInputEvent.MOUSE_RELEASED_POST.invoker().onMouseReleased(minecraft, screen, mouseX, mouseY, button);
        }
    }

    @Inject(method = "mouseDragged", at = @At("HEAD"), cancellable = true)
    private void knot$mouseDraggedPre(double mouseX, double mouseY, int button, double dragX, double dragY, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            if (ClientScreenInputEvent.MOUSE_DRAGGED_PRE.invoker().onMouseDragged(minecraft, screen, mouseX, mouseY, button, dragX, dragY).cancelsEvent()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "mouseDragged", at = @At("RETURN"))
    private void knot$mouseDraggedPost(double mouseX, double mouseY, int button, double dragX, double dragY, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientScreenInputEvent.MOUSE_DRAGGED_POST.invoker().onMouseDragged(minecraft, screen, mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void knot$keyPressedPre(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            if (ClientScreenInputEvent.KEY_PRESSED_PRE.invoker().onKeyPressed(minecraft, screen, keyCode, scanCode, modifiers).cancelsEvent()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "keyPressed", at = @At("RETURN"))
    private void knot$keyPressedPost(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientScreenInputEvent.KEY_PRESSED_POST.invoker().onKeyPressed(minecraft, screen, keyCode, scanCode, modifiers);
        }
    }

    @Inject(method = "keyReleased", at = @At("HEAD"), cancellable = true)
    private void knot$keyReleasedPre(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            if (ClientScreenInputEvent.KEY_RELEASED_PRE.invoker().onKeyReleased(minecraft, screen, keyCode, scanCode, modifiers).cancelsEvent()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "keyReleased", at = @At("RETURN"))
    private void knot$keyReleasedPost(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientScreenInputEvent.KEY_RELEASED_POST.invoker().onKeyReleased(minecraft, screen, keyCode, scanCode, modifiers);
        }
    }

    @Inject(method = "charTyped", at = @At("HEAD"), cancellable = true)
    private void knot$charTypedPre(char codePoint, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            if (ClientScreenInputEvent.CHAR_TYPED_PRE.invoker().onCharTyped(minecraft, screen, codePoint, modifiers).cancelsEvent()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "charTyped", at = @At("RETURN"))
    private void knot$charTypedPost(char codePoint, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen screen) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientScreenInputEvent.CHAR_TYPED_POST.invoker().onCharTyped(minecraft, screen, codePoint, modifiers);
        }
    }
}
