package com.daqem.knot.mixin;

import com.daqem.knot.event.KnotChunkEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkMap.class)
public class MixinChunkMap {
    @Shadow @Final ServerLevel level;

    @Inject(method = "save", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ChunkMap;write(Lnet/minecraft/world/level/ChunkPos;Ljava/util/function/Supplier;)Ljava/util/concurrent/CompletableFuture;", ordinal = 0))
    private void knot$onChunkSave(ChunkAccess chunkAccess, CallbackInfoReturnable<Boolean> cir, @Local SerializableChunkData data) {
        KnotChunkEvent.SAVE_DATA.invoker().onSaveData(chunkAccess, this.level, data);
    }
}