package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.client.ClientHudEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin {

    @Inject(method = "renderLines", at = @At("HEAD"))
    private void knot$onRenderLines(GuiGraphics graphics, List<String> lines, boolean leftSide, CallbackInfo ci) {
        if (leftSide) {
            ClientHudEvent.DEBUG_TEXT_LEFT.invoker().onGatherDebugText(lines);
        } else {
            ClientHudEvent.DEBUG_TEXT_RIGHT.invoker().onGatherDebugText(lines);
        }
    }
}