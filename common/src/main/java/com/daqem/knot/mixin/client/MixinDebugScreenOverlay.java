package com.daqem.knot.mixin.client;

import com.daqem.knot.event.client.KnotHudEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class MixinDebugScreenOverlay {

    @Inject(method = "renderLines", at = @At("HEAD"))
    private void knot$onRenderLines(GuiGraphics graphics, List<String> lines, boolean leftSide, CallbackInfo ci) {
        if (leftSide) {
            KnotHudEvent.DEBUG_TEXT_LEFT.invoker().onGatherDebugText(lines);
        } else {
            KnotHudEvent.DEBUG_TEXT_RIGHT.invoker().onGatherDebugText(lines);
        }
    }
}