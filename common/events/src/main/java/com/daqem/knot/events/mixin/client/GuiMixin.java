package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.client.ClientHudEvent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Inject(method = "render", at = @At("RETURN"))
    private void knot$onRenderHud(GuiGraphics graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        ClientHudEvent.RENDER.invoker().onRenderHud(graphics, deltaTracker);
    }
}