package com.daqem.knot.mixin.client;

import com.daqem.knot.event.client.KnotScreenEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreen {

    @Inject(method = "renderLabels", at = @At("RETURN"))
    private void knot$onRenderForeground(GuiGraphics graphics, int mouseX, int mouseY, CallbackInfo ci) {
        KnotScreenEvent.RENDER_CONTAINER_FOREGROUND.invoker().onRenderForeground(
                (AbstractContainerScreen<?>) (Object) this,
                graphics,
                mouseX,
                mouseY
        );
    }
}