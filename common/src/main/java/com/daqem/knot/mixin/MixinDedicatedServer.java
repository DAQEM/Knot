package com.daqem.knot.mixin;

import com.daqem.knot.event.common.KnotTickEvent;
import com.daqem.knot.event.server.KnotServersideTickEvent;
import com.mojang.datafixers.DataFixer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;

@Mixin(DedicatedServer.class)
public abstract class MixinDedicatedServer extends MinecraftServer implements ServerInterface {

    public MixinDedicatedServer(Thread serverThread, LevelStorageSource.LevelStorageAccess storageSource, PackRepository packRepository, WorldStem worldStem, Proxy proxy, DataFixer fixerUpper, Services services, LevelLoadListener levelLoadListener) {
        super(serverThread, storageSource, packRepository, worldStem, proxy, fixerUpper, services, levelLoadListener);
    }

    @Inject(
            method = "tickServer",
            at = @At("HEAD")
    )
    private void knot$preTick(CallbackInfo ci) {
        KnotServersideTickEvent.DEDICATED_SERVER_PRE.invoker().tick((DedicatedServer) (Object) this);
        KnotTickEvent.SERVER_PRE.invoker().tick(this);
    }

    @Inject(
            method = "tickServer",
            at = @At("RETURN")
    )
    private void knot$postTick(CallbackInfo ci) {
        KnotServersideTickEvent.DEDICATED_SERVER_POST.invoker().tick((DedicatedServer) (Object) this);
        KnotTickEvent.SERVER_POST.invoker().tick(this);
    }
}
