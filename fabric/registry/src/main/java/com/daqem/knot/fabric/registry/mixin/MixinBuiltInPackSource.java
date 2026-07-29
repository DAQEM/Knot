package com.daqem.knot.fabric.registry.mixin;

import com.daqem.knot.registry.pack.GlobalPackPaths;
import com.daqem.knot.registry.pack.GlobalPackRepository;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(BuiltInPackSource.class)
public abstract class MixinBuiltInPackSource {

    @Shadow @Final private PackType packType;

    @Inject(method = "loadPacks", at = @At("RETURN"))
    private void knot$loadGlobalResourcePacks(Consumer<Pack> result, CallbackInfo ci) {
        if (this.packType == PackType.CLIENT_RESOURCES) {
            new GlobalPackRepository(
                    GlobalPackPaths.RESOURCE_PACKS,
                    PackType.CLIENT_RESOURCES,
                    GlobalPackPaths.KNOT_PACK_SOURCE
            ).loadPacks(result);
        }
    }
}