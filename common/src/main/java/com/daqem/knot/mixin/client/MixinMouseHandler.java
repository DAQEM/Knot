package com.daqem.knot.mixin.client;

import com.daqem.knot.event.client.KnotClientRawInputEvent;
import com.daqem.knot.event.client.KnotClientScreenInputEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MixinMouseHandler {

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "onButton", at = @At("HEAD"), cancellable = true)
    private void knot$onRawMouseClicked(long window, MouseButtonInfo buttonInfo, int action, CallbackInfo ci) {
        if (window == this.minecraft.getWindow().handle()) {
            if (KnotClientRawInputEvent.MOUSE_CLICKED_PRE.invoker().onMouseClicked(minecraft, buttonInfo.button(), action, buttonInfo.modifiers()).cancelsEvent()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "onButton", at = @At("RETURN"))
    private void knot$onRawMouseClickedPost(long window, MouseButtonInfo buttonInfo, int action, CallbackInfo ci) {
        if (window == this.minecraft.getWindow().handle()) {
            KnotClientRawInputEvent.MOUSE_CLICKED_POST.invoker().onMouseClicked(minecraft, buttonInfo.button(), action, buttonInfo.modifiers());
        }
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void knot$onRawMouseScroll(long windowPointer, double xOffset, double yOffset, CallbackInfo ci) {
        if (windowPointer == this.minecraft.getWindow().handle()) {
            if (KnotClientRawInputEvent.MOUSE_SCROLLED.invoker().onMouseScrolled(minecraft, xOffset, yOffset).cancelsEvent()) {
                ci.cancel();
            }
        }
    }

    @WrapOperation(method = "onButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseClicked(Lnet/minecraft/client/input/MouseButtonEvent;Z)Z"))
    private boolean knot$onScreenMouseClicked(Screen screen, MouseButtonEvent event, boolean doubleClick, Operation<Boolean> original) {
        if (KnotClientScreenInputEvent.MOUSE_CLICKED_PRE.invoker().onMouseClicked(minecraft, screen, event.x(), event.y(), event.button()).cancelsEvent()) return true;
        boolean result = original.call(screen, event, doubleClick);
        KnotClientScreenInputEvent.MOUSE_CLICKED_POST.invoker().onMouseClicked(minecraft, screen, event.x(), event.y(), event.button());
        return result;
    }

    @WrapOperation(method = "onButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseReleased(Lnet/minecraft/client/input/MouseButtonEvent;)Z"))
    private boolean knot$onScreenMouseReleased(Screen screen, MouseButtonEvent event, Operation<Boolean> original) {
        if (KnotClientScreenInputEvent.MOUSE_RELEASED_PRE.invoker().onMouseReleased(minecraft, screen, event.x(), event.y(), event.button()).cancelsEvent()) return true;
        boolean result = original.call(screen, event);
        KnotClientScreenInputEvent.MOUSE_RELEASED_POST.invoker().onMouseReleased(minecraft, screen, event.x(), event.y(), event.button());
        return result;
    }

    @WrapOperation(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseScrolled(DDDD)Z"))
    private boolean knot$onScreenMouseScrolled(Screen screen, double mouseX, double mouseY, double scrollX, double scrollY, Operation<Boolean> original) {
        if (KnotClientScreenInputEvent.MOUSE_SCROLLED_PRE.invoker().onMouseScrolled(minecraft, screen, mouseX, mouseY, scrollX, scrollY).cancelsEvent()) return true;
        boolean result = original.call(screen, mouseX, mouseY, scrollX, scrollY);
        KnotClientScreenInputEvent.MOUSE_SCROLLED_POST.invoker().onMouseScrolled(minecraft, screen, mouseX, mouseY, scrollX, scrollY);
        return result;
    }

    @WrapOperation(method = "handleAccumulatedMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseDragged(Lnet/minecraft/client/input/MouseButtonEvent;DD)Z"))
    private boolean knot$onScreenMouseDragged(Screen screen, MouseButtonEvent event, double dx, double dy, Operation<Boolean> original) {
        if (KnotClientScreenInputEvent.MOUSE_DRAGGED_PRE.invoker().onMouseDragged(minecraft, screen, event.x(), event.y(), event.button(), dx, dy).cancelsEvent()) return true;
        boolean result = original.call(screen, event, dx, dy);
        KnotClientScreenInputEvent.MOUSE_DRAGGED_POST.invoker().onMouseDragged(minecraft, screen, event.x(), event.y(), event.button(), dx, dy);
        return result;
    }
}