package com.daqem.knot.fabric.mixin;

import com.daqem.knot.fabric.mixin.MixinPackRepositoryAccessor;
import com.daqem.knot.pack.GlobalPackPaths;
import com.daqem.knot.pack.KnotGlobalPackRepository;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(WorldLoader.class)
public class MixinWorldLoader {

    @Inject(method = "load", at = @At("HEAD"))
    private static <D, R> void knot$addGlobalDataPacks(
            WorldLoader.InitConfig initConfig,
            WorldLoader.WorldDataSupplier<D> worldDataSupplier,
            WorldLoader.ResultFactory<D, R> resultFactory,
            Executor backgroundExecutor,
            Executor gameExecutor,
            CallbackInfoReturnable<CompletableFuture<R>> cir
    ) {
        var repo = initConfig.packConfig().packRepository();
        Set<RepositorySource> sources = ((MixinPackRepositoryAccessor) repo).knot$getSources();
        sources.add(new KnotGlobalPackRepository(
                GlobalPackPaths.DATA_PACKS,
                PackType.SERVER_DATA,
                GlobalPackPaths.KNOT_PACK_SOURCE
        ));
    }
}