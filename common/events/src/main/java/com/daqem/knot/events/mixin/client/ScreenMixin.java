package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.client.ClientScreenEvent;
import com.daqem.knot.events.client.ClientScreenInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Inject(method = "init(Lnet/minecraft/client/Minecraft;II)V", at = @At("HEAD"), cancellable = true)
    private void knot$onBeforeInit(Minecraft minecraft, int width, int height, CallbackInfo ci) {
        if (ClientScreenEvent.BEFORE_INIT.invoker().onBeforeInit((Screen) (Object) this).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(method = "init(Lnet/minecraft/client/Minecraft;II)V", at = @At("RETURN"))
    private void knot$onAfterInit(Minecraft minecraft, int width, int height, CallbackInfo ci) {
        ClientScreenEvent.AFTER_INIT.invoker().onAfterInit((Screen) (Object) this);
    }

    @Inject(method = "renderWithTooltip", at = @At("HEAD"), cancellable = true)
    private void knot$onBeforeRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (ClientScreenEvent.BEFORE_RENDER.invoker().onBeforeRender((Screen) (Object) this, graphics, mouseX, mouseY, partialTicks).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderWithTooltip", at = @At("RETURN"))
    private void knot$onAfterRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        ClientScreenEvent.AFTER_RENDER.invoker().onAfterRender((Screen) (Object) this, graphics, mouseX, mouseY, partialTicks);
    }
}