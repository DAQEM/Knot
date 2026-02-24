package com.daqem.knot.mixin.client;

import com.daqem.knot.event.client.KnotClientsideTickEvent;
import com.mojang.blaze3d.platform.WindowEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft extends ReentrantBlockableEventLoop<@NotNull Runnable> implements WindowEventHandler {

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
}
