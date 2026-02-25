package com.daqem.knot.mixin.client;

import com.daqem.knot.event.client.KnotScreenEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class MixinScreen {

    @Inject(method = "init(II)V", at = @At("HEAD"), cancellable = true)
    private void knot$onBeforeInit(int width, int height, CallbackInfo ci) {
        if (KnotScreenEvent.BEFORE_INIT.invoker().onBeforeInit((Screen) (Object) this).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(method = "init(II)V", at = @At("RETURN"))
    private void knot$onAfterInit(int width, int height, CallbackInfo ci) {
        KnotScreenEvent.AFTER_INIT.invoker().onAfterInit((Screen) (Object) this);
    }

    @Inject(method = "renderWithTooltipAndSubtitles", at = @At("HEAD"), cancellable = true)
    private void knot$onBeforeRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (KnotScreenEvent.BEFORE_RENDER.invoker().onBeforeRender((Screen) (Object) this, graphics, mouseX, mouseY, partialTicks).cancelsEvent()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderWithTooltipAndSubtitles", at = @At("RETURN"))
    private void knot$onAfterRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        KnotScreenEvent.AFTER_RENDER.invoker().onAfterRender((Screen) (Object) this, graphics, mouseX, mouseY, partialTicks);
    }
}