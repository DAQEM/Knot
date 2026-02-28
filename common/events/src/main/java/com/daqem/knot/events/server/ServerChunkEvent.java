package com.daqem.knot.events.server;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import org.jetbrains.annotations.Nullable;

public interface ServerChunkEvent {

    Event<SaveData> SAVE_DATA = EventFactory.createLoop(SaveData.class);
    Event<LoadData> LOAD_DATA = EventFactory.createLoop(LoadData.class);

    interface SaveData {
        /**
         * Fired when a chunk's data is being written to disk.
         * You can append your own NBT data to the SerializableChunkData.
         */
        void onSaveData(ChunkAccess chunk, ServerLevel level, SerializableChunkData data);
    }

    interface LoadData {
        /**
         * Fired just before a chunk's data is fully read from disk.
         * You can read out your custom NBT data from here.
         */
        void onLoadData(ChunkAccess chunk, @Nullable ServerLevel level, SerializableChunkData data);
    }
}