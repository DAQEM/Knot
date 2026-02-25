package com.daqem.knot.event;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

public interface KnotLootEvent {

    Event<ModifyLootTable> MODIFY_LOOT_TABLE = EventFactory.createLoop(ModifyLootTable.class);

    interface ModifyLootTable {
        /**
         * Fired when a Loot Table is loaded into the game.
         * Allows appending custom pools (items) to existing Vanilla tables.
         *
         * @param key      The ResourceKey of the LootTable being loaded.
         * @param context  The wrapper to modify the table safely.
         * @param builtin  True if this table is a native built-in table (Vanilla/Mod), false if it's from a user datapack.
         */
        void onModifyLootTable(ResourceKey<LootTable> key, LootTableModificationContext context, boolean builtin);
    }

    interface LootTableModificationContext {
        void addPool(LootPool.Builder pool);
    }
}