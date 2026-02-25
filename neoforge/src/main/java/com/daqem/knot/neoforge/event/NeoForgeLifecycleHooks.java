package com.daqem.knot.neoforge.event;

import com.daqem.knot.KnotMod;
import com.daqem.knot.event.*;
import com.daqem.knot.event.common.KnotCommandEvent;
import com.daqem.knot.event.lifecycle.KnotLevelLifecycleEvent;
import com.daqem.knot.event.lifecycle.KnotServerLifecycleEvent;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.*;
import org.apache.commons.lang3.mutable.MutableObject;

import java.lang.reflect.Field;
import java.util.List;

@EventBusSubscriber(modid = KnotMod.MOD_ID)
public class NeoForgeLifecycleHooks {

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        KnotServerLifecycleEvent.BEFORE_START.invoker().onServerBeforeStart(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        KnotServerLifecycleEvent.STARTING.invoker().onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        KnotServerLifecycleEvent.STARTED.invoker().onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        KnotServerLifecycleEvent.STOPPING.invoker().onServerStopping(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        KnotServerLifecycleEvent.STOPPED.invoker().onServerStopped(event.getServer());
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            KnotLevelLifecycleEvent.SERVER_LEVEL_LOAD.invoker().onServerLevelLoad(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            KnotLevelLifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().onServerLevelUnload(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        MutableObject<Component> mutable = new MutableObject<>(event.getMessage());
        KnotChatEvent.DECORATE.invoker().onDecorateChat(event.getPlayer(), mutable);
        event.setMessage(mutable.get());

        EventResult result = KnotChatEvent.RECEIVED.invoker().onReceiveChat(event.getPlayer(), event.getMessage());
        if (result.cancelsEvent()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        KnotLootEvent.MODIFY_LOOT_TABLE.invoker().onModifyLootTable(
                ResourceKey.create(Registries.LOOT_TABLE, event.getName()),
                poolBuilder -> addPoolToTable(event.getTable(), poolBuilder.build()),
                true
        );
    }

    @SuppressWarnings("unchecked")
    private static void addPoolToTable(LootTable table, LootPool pool) {
        try {
            // Retrieve the pools list using reflection to mutate it directly
            for (Field field : LootTable.class.getDeclaredFields()) {
                if (field.getType() == List.class) {
                    field.setAccessible(true);
                    List<LootPool> pools = (List<LootPool>) field.get(table);
                    pools.add(pool);
                    return;
                }
            }
        } catch (Exception e) {
            KnotMod.API.error("Failed to add loot pool to table via reflection", e);
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        KnotCommandEvent.REGISTER.invoker().onRegister(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }

    @SubscribeEvent
    public static void onCommandPerform(CommandEvent event) {
        MutableObject<ParseResults<CommandSourceStack>> resultsRef = new MutableObject<>(event.getParseResults());
        MutableObject<Throwable> exceptionRef = new MutableObject<>(event.getException());

        if (KnotCommandEvent.PERFORM.invoker().onPerform(resultsRef, exceptionRef).cancelsEvent()) {
            event.setCanceled(true);
        }

        event.setParseResults(resultsRef.get());
        event.setException(exceptionRef.get());
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (KnotBlockEvent.LEFT_CLICK_BLOCK.invoker().onLeftClickBlock(event.getEntity(), event.getHand(), event.getPos(), event.getFace()).cancelsEvent()) {
            event.setCanceled(true);
        }
    }
}