package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.client.*;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.WindowEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin extends ReentrantBlockableEventLoop<@NotNull Runnable> implements WindowEventHandler {

    @Shadow
    @Nullable
    public ClientLevel level;

    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Unique
    private boolean knot$cancelScreenSwap = false;

    public MinecraftMixin(String name, boolean propagatesCrashes) {
        super(name, propagatesCrashes);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void knot$preTick(CallbackInfo ci) {
        ClientTickEvent.CLIENT_PRE.invoker().tick((Minecraft) (Object) this);
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void knot$postTick(CallbackInfo ci) {
        ClientTickEvent.CLIENT_POST.invoker().tick((Minecraft) (Object) this);
    }

    @Inject(
            method = "setLevel",
            at = @At("HEAD")
    )
    private void knot$onSetLevel(CallbackInfo ci) {
        if (this.level != null) {
            ClientLevelLifecycleEvent.CLIENT_LEVEL_UNLOAD.invoker().onClientLevelUnload(this.level);
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
            ClientLevelLifecycleEvent.CLIENT_LEVEL_UNLOAD.invoker().onClientLevelUnload(this.level);
        }
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameThread:Ljava/lang/Thread;", shift = At.Shift.AFTER, ordinal = 0, opcode = 181), method = "run")
    private void knot$onClientStarted(CallbackInfo ci) {
        // This triggers when the client initialization is finished and the loop begins.
        ClientLifecycleEvent.STARTED.invoker().onClientStarted((Minecraft) (Object) this);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;)V", shift = At.Shift.AFTER), method = "destroy")
    private void knot$onClientStopping(CallbackInfo ci) {
        // This triggers as soon as the shutdown sequence is initiated.
        ClientLifecycleEvent.STOPPING.invoker().onClientStopping((Minecraft) (Object) this);
    }

    @WrapOperation(
            method = "setScreen",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", opcode = 181)
    )
    private void knot$wrapScreenChange(Minecraft instance, Screen newScreen, Operation<Void> original) {
        MutableObject<Screen> screenWrapper = new MutableObject<>(newScreen);

        EventResult result = ClientScreenEvent.BEFORE_OPEN.invoker().onBeforeOpen(this.screen, screenWrapper);

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

    @Inject(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 1))
    private void knot$onRightClickAir(CallbackInfo ci, @Local(ordinal = 0) InteractionHand hand, @Local(ordinal = 0) ItemStack itemStack) {
        if (itemStack.isEmpty() && (this.hitResult == null || this.hitResult.getType() == HitResult.Type.MISS)) {
            ClientInteractionEvent.RIGHT_CLICK_AIR.invoker().onRightClickAir(this.player, hand);
        }
    }

    @Inject(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;resetAttackStrengthTicker()V"))
    private void knot$onLeftClickAir(CallbackInfoReturnable<Boolean> cir) {
        if (this.hitResult == null || this.hitResult.getType() == HitResult.Type.MISS) {
            ClientInteractionEvent.LEFT_CLICK_AIR.invoker().onLeftClickAir(this.player, InteractionHand.MAIN_HAND);
        }
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screens/Screen;ZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/GameNarrator;clear()V"))
    private void knot$onClientQuit(Screen screen, boolean bl, boolean bl2, CallbackInfo ci) {
        if (this.player != null) {
            ClientPlayerEvent.QUIT.invoker().onQuit(this.player);
        }
    }
}
