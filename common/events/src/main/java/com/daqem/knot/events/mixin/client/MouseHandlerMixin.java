package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.client.ClientRawInputEvent;
import com.daqem.knot.events.client.ClientScreenInputEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    private void knot$onRawMouseClicked(long window, int button, int action, int modifiers, CallbackInfo ci) {
        if (window == this.minecraft.getWindow().getWindow()) {
            if (ClientRawInputEvent.MOUSE_CLICKED_PRE.invoker().onMouseClicked(minecraft, button, action, modifiers).cancelsEvent()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "onPress", at = @At("RETURN"))
    private void knot$onRawMouseClickedPost(long window, int button, int action, int modifiers, CallbackInfo ci) {
        if (window == this.minecraft.getWindow().getWindow()) {
            ClientRawInputEvent.MOUSE_CLICKED_POST.invoker().onMouseClicked(minecraft, button, action, modifiers);
        }
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void knot$onRawMouseScroll(long windowPointer, double xOffset, double yOffset, CallbackInfo ci) {
        if (windowPointer == this.minecraft.getWindow().getWindow()) {
            if (ClientRawInputEvent.MOUSE_SCROLLED.invoker().onMouseScrolled(minecraft, xOffset, yOffset).cancelsEvent()) {
                ci.cancel();
            }
        }
    }

    @WrapOperation(method = "onPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseClicked(DDI)Z"))
    private boolean knot$onScreenMouseClicked(Screen screen, double mouseX, double mouseY, int button, Operation<Boolean> original) {
        if (ClientScreenInputEvent.MOUSE_CLICKED_PRE.invoker().onMouseClicked(minecraft, screen, mouseX, mouseY, button).cancelsEvent()) return true;
        boolean result = original.call(screen, mouseX, mouseY, button);
        ClientScreenInputEvent.MOUSE_CLICKED_POST.invoker().onMouseClicked(minecraft, screen, mouseX, mouseY, button);
        return result;
    }

    @WrapOperation(method = "onPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseReleased(DDI)Z"))
    private boolean knot$onScreenMouseReleased(Screen screen, double mouseX, double mouseY, int button, Operation<Boolean> original) {
        if (ClientScreenInputEvent.MOUSE_RELEASED_PRE.invoker().onMouseReleased(minecraft, screen, mouseX, mouseY, button).cancelsEvent()) return true;
        boolean result = original.call(screen, mouseX, mouseY, button);
        ClientScreenInputEvent.MOUSE_RELEASED_POST.invoker().onMouseReleased(minecraft, screen, mouseX, mouseY, button);
        return result;
    }

    @WrapOperation(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseScrolled(DDDD)Z"))
    private boolean knot$onScreenMouseScrolled(Screen screen, double mouseX, double mouseY, double scrollX, double scrollY, Operation<Boolean> original) {
        if (ClientScreenInputEvent.MOUSE_SCROLLED_PRE.invoker().onMouseScrolled(minecraft, screen, mouseX, mouseY, scrollX, scrollY).cancelsEvent()) return true;
        boolean result = original.call(screen, mouseX, mouseY, scrollX, scrollY);
        ClientScreenInputEvent.MOUSE_SCROLLED_POST.invoker().onMouseScrolled(minecraft, screen, mouseX, mouseY, scrollX, scrollY);
        return result;
    }

    @WrapOperation(method = "handleAccumulatedMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseDragged(DDIDD)Z"))
    private boolean knot$onScreenMouseDragged(Screen screen, double mouseX, double mouseY, int button, double dragX, double dragY, Operation<Boolean> original) {
        if (ClientScreenInputEvent.MOUSE_DRAGGED_PRE.invoker().onMouseDragged(minecraft, screen, mouseX, mouseY, button, dragX, dragY).cancelsEvent()) return true;
        boolean result = original.call(screen, mouseX, mouseY, button, dragX, dragY);
        ClientScreenInputEvent.MOUSE_DRAGGED_POST.invoker().onMouseDragged(minecraft, screen, mouseX, mouseY, button, dragX, dragY);
        return result;
    }
}