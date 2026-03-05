package com.daqem.knot.events.mixin.common;

import com.daqem.knot.events.common.TickEvent;
import com.daqem.knot.events.server.ServerTickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Inject(
            method = "tickServer",
            at = @At("HEAD")
    )
    private void knot$preTick(CallbackInfo ci) {
        if ((MinecraftServer) (Object) this instanceof DedicatedServer dedicatedServer) {
            ServerTickEvent.DEDICATED_SERVER_PRE.invoker().tick(dedicatedServer);
            TickEvent.SERVER_PRE.invoker().tick(dedicatedServer);
        }
    }

    @Inject(
            method = "tickServer",
            at = @At("RETURN")
    )
    private void knot$postTick(CallbackInfo ci) {
        if ((MinecraftServer) (Object) this instanceof DedicatedServer dedicatedServer) {
            ServerTickEvent.DEDICATED_SERVER_POST.invoker().tick(dedicatedServer);
            TickEvent.SERVER_POST.invoker().tick(dedicatedServer);
        }
    }
}
