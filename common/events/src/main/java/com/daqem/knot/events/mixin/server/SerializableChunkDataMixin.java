package com.daqem.knot.events.mixin.server;

import com.daqem.knot.events.server.ServerChunkEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SerializableChunkData.class)
public abstract class SerializableChunkDataMixin {

    @Inject(method = "read", at = @At("RETURN"))
    private void knot$onChunkLoad(ServerLevel serverLevel, PoiManager poiManager, RegionStorageInfo regionStorageInfo, ChunkPos chunkPos, CallbackInfoReturnable<ProtoChunk> cir, @Local ChunkAccess chunkAccess) {
        ServerChunkEvent.LOAD_DATA.invoker().onLoadData(chunkAccess, serverLevel, (SerializableChunkData) (Object) this);
    }
}