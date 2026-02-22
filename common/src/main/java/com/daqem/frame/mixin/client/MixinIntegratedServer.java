package com.daqem.frame.mixin.client;

import com.daqem.frame.event.common.FrameTickEvent;
import com.mojang.datafixers.DataFixer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;

@Mixin(IntegratedServer.class)
public abstract class MixinIntegratedServer extends MinecraftServer {

    public MixinIntegratedServer(Thread serverThread, LevelStorageSource.LevelStorageAccess storageSource, PackRepository packRepository, WorldStem worldStem, Proxy proxy, DataFixer fixerUpper, Services services, LevelLoadListener levelLoadListener) {
        super(serverThread, storageSource, packRepository, worldStem, proxy, fixerUpper, services, levelLoadListener);
    }

    @Inject(
            method = "tickServer",
            at = @At("HEAD")
    )
    private void frame$preTick(CallbackInfo ci) {
        FrameTickEvent.SERVER_PRE.invoker().tick(this);
    }

    @Inject(
            method = "tickServer",
            at = @At("RETURN")
    )
    private void frame$postTick(CallbackInfo ci) {
        FrameTickEvent.SERVER_POST.invoker().tick(this);
    }
}
