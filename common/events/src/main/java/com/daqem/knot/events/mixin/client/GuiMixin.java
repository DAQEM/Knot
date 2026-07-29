package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.client.ClientHudEvent;
import com.daqem.knot.events.client.ClientScreenEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    private @Nullable Screen screen;

    @WrapOperation(method = "setScreen", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;screen:Lnet/minecraft/client/gui/screens/Screen;", opcode = 181))
    private void knot$wrapScreenChange(Gui instance, Screen newScreen, Operation<Void> original) {
        MutableObject<Screen> screenWrapper = new MutableObject<>(newScreen);

        EventResult result = ClientScreenEvent.BEFORE_OPEN.invoker().onBeforeOpen(this.screen, screenWrapper);

        // If canceled, we simply don't call original.call(), so the field is never updated.
        if (result.cancelsEvent()) {
            return;
        }

        // Proceed with the wrapped (potentially replaced) screen
        original.call(instance, screenWrapper.get());
    }
}