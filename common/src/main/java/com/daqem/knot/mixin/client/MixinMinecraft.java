package com.daqem.knot.mixin.client;

import com.daqem.knot.event.EventResult;
import com.daqem.knot.event.client.KnotClientsideTickEvent;
import com.daqem.knot.event.client.KnotScreenEvent;
import com.daqem.knot.event.lifecycle.KnotClientLifecycleEvent;
import com.daqem.knot.event.lifecycle.KnotLevelLifecycleEvent;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.WindowEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft extends ReentrantBlockableEventLoop<@NotNull Runnable> implements WindowEventHandler {

    @Shadow
    @Nullable
    public ClientLevel level;

    @Shadow @Nullable public Screen screen;
    @Unique
    private boolean knot$cancelScreenSwap = false;

    public MixinMinecraft(String string) {
        super(string);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void knot$preTick(CallbackInfo ci) {
        KnotClientsideTickEvent.CLIENT_PRE.invoker().tick((Minecraft) (Object) this);
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void knot$postTick(CallbackInfo ci) {
        KnotClientsideTickEvent.CLIENT_POST.invoker().tick((Minecraft) (Object) this);
    }

    @Inject(
            method = "setLevel",
            at = @At("HEAD")
    )
    private void knot$onSetLevel(CallbackInfo ci) {
        if (this.level != null) {
            KnotLevelLifecycleEvent.CLIENT_LEVEL_UNLOAD.invoker().onClientLevelUnload(this.level);
        }
    }

    @Inject(
            method = "disconnect(Lnet/minecraft/client/gui/screens/Screen;ZZ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;onDisconnected()V",
                    shift = At.Shift.AFTER
            )
    )
    private void knot$onDisconnect(CallbackInfo ci) {
        if (this.level != null) {
            KnotLevelLifecycleEvent.CLIENT_LEVEL_UNLOAD.invoker().onClientLevelUnload(this.level);
        }
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameThread:Ljava/lang/Thread;", shift = At.Shift.AFTER, ordinal = 0, opcode = Opcodes.PUTFIELD), method = "run")
    private void knot$onClientStarted(CallbackInfo ci) {
        // This triggers when the client initialization is finished and the loop begins.
        KnotClientLifecycleEvent.STARTED.invoker().onClientStarted((Minecraft) (Object) this);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;)V", shift = At.Shift.AFTER), method = "destroy")
    private void knot$onClientStopping(CallbackInfo ci) {
        // This triggers as soon as the shutdown sequence is initiated.
        KnotClientLifecycleEvent.STOPPING.invoker().onClientStopping((Minecraft) (Object) this);
    }

    @WrapOperation(
            method = "setScreen",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", opcode = Opcodes.PUTFIELD)
    )
    private void knot$wrapScreenChange(Minecraft instance, Screen newScreen, Operation<Void> original) {
        MutableObject<Screen> screenWrapper = new MutableObject<>(newScreen);

        EventResult result = KnotScreenEvent.BEFORE_OPEN.invoker().onBeforeOpen(this.screen, screenWrapper);

        // If canceled, we simply don't call original.call(), so the field is never updated.
        if (result.cancelsEvent()) {
            return;
        }

        // Proceed with the wrapped (potentially replaced) screen
        original.call(instance, screenWrapper.get());
    }

    @Inject(
            method = "setScreen",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", shift = At.Shift.BEFORE),
            cancellable = true
    )
    private void knot$cancelSetScreen(Screen screen, CallbackInfo ci) {
        if (this.knot$cancelScreenSwap) {
            this.knot$cancelScreenSwap = false;
            ci.cancel();
        }
    }
}
