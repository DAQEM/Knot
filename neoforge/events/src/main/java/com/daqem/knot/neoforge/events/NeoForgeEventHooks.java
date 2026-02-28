package com.daqem.knot.neoforge.events;

import com.daqem.knot.api.Constants;
import com.daqem.knot.api.Logger;
import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.LevelLifecycleEvent;
import com.daqem.knot.events.common.block.BlockEvent;
import com.daqem.knot.events.common.entity.EntityEvent;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import com.daqem.knot.events.common.item.ItemEvent;
import com.daqem.knot.events.common.loot.LootEvent;
import com.daqem.knot.events.server.ServerChatEvent;
import com.daqem.knot.events.server.ServerCommandEvent;
import com.daqem.knot.events.server.ServerLifecycleEvent;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerDestroyItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.*;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableObject;

import java.lang.reflect.Field;
import java.util.List;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeEventHooks {

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        ServerLifecycleEvent.BEFORE_START.invoker().onServerBeforeStart(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        ServerLifecycleEvent.STARTING.invoker().onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ServerLifecycleEvent.STARTED.invoker().onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        ServerLifecycleEvent.STOPPING.invoker().onServerStopping(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ServerLifecycleEvent.STOPPED.invoker().onServerStopped(event.getServer());
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            LevelLifecycleEvent.SERVER_LEVEL_LOAD.invoker().onServerLevelLoad(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            LevelLifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().onServerLevelUnload(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onServerChat(net.neoforged.neoforge.event.ServerChatEvent event) {
        MutableObject<Component> mutable = new MutableObject<>(event.getMessage());
        ServerChatEvent.DECORATE.invoker().onDecorateChat(event.getPlayer(), mutable);
        event.setMessage(mutable.get());

        EventResult result = ServerChatEvent.RECEIVED.invoker().onReceiveChat(event.getPlayer(), event.getMessage());
        if (result.cancelsEvent()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        LootEvent.MODIFY_LOOT_TABLE.invoker().onModifyLootTable(
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
            Logger.error("Failed to add loot pool to table via reflection", e);
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ServerCommandEvent.REGISTER.invoker().onRegister(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }

    @SubscribeEvent
    public static void onCommandPerform(CommandEvent event) {
        MutableObject<ParseResults<CommandSourceStack>> resultsRef = new MutableObject<>(event.getParseResults());
        MutableObject<Throwable> exceptionRef = new MutableObject<>(event.getException());

        if (ServerCommandEvent.PERFORM.invoker().onPerform(resultsRef, exceptionRef).cancelsEvent()) {
            event.setCanceled(true);
        }

        event.setParseResults(resultsRef.get());
        event.setException(exceptionRef.get());
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (BlockEvent.LEFT_CLICK_BLOCK.invoker().onLeftClickBlock(event.getEntity(), event.getHand(), event.getPos(), event.getFace()).cancelsEvent()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        MutableFloat damage = new MutableFloat(event.getContainer().getNewDamage());
        DamageSource damageSource = event.getSource();
        LivingEntity defender = event.getEntity();
        if (defender instanceof ServerPlayer serverPlayer) {
            EventResult eventResult = PlayerEvent.ENTITY_HURT_PLAYER.invoker().onEntityHurtPlayer(serverPlayer, damageSource, damage);
            if (eventResult.cancelsEvent()) {
                event.setCanceled(true);
                return;
            }
        }
        if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
            EventResult eventResult = EntityEvent.PLAYER_HURT_ENTITY.invoker().onPlayerHurtEntity(serverPlayer, defender, damageSource, damage);
            if (eventResult.cancelsEvent()) {
                event.setCanceled(true);
                return;
            }
        }
        if (defender instanceof ServerPlayer serverPlayer && damageSource.getEntity() instanceof ServerPlayer attacker) {
            EventResult eventResult = PlayerEvent.PLAYER_HURT_PLAYER.invoker().onPlayerHurtPlayer(attacker, serverPlayer, damageSource, damage);
            if (eventResult.cancelsEvent()) {
                event.setCanceled(true);
            }
        }
        event.getContainer().setNewDamage(damage.floatValue());
    }

    @SubscribeEvent
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        if (event.getEntity() instanceof Player player && event.getBlockedDamage() > 0.0F) {
            PlayerEvent.BLOCK_WITH_SHIELD.invoker().onBlockWithShield(player, event.getDamageSource(), event.getBlockedDamage());
        }
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        EventResult eventResult = ItemEvent.DROP_ITEM.invoker().onDropItem(event.getPlayer(), event.getEntity());
        if (eventResult.cancelsEvent()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onDestroyItem(PlayerDestroyItemEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            ItemEvent.ITEM_BREAK.invoker().onItemBreak(serverPlayer, event.getOriginal());
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(net.neoforged.neoforge.event.level.BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && event.getLevel() instanceof ServerLevel serverLevel) {
            EventResult result = BlockEvent.BREAK_BLOCK.invoker().onBreakBlock(
                    serverLevel,
                    event.getPos(),
                    event.getState(),
                    serverPlayer
            );
            if (result.cancelsEvent()) {
                event.setCanceled(true);
            } else {
                if (event.getState().getBlock() instanceof CropBlock) {
                    EventResult result1 = BlockEvent.HARVEST_CROP.invoker().onHarvestCrop(
                            serverLevel,
                            event.getPos(),
                            event.getState(),
                            serverPlayer
                    );
                    if (result1.cancelsEvent()) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent event) {
        EventResult result = BlockEvent.PLACE_BLOCK.invoker().onPlaceBlock(
                (Level) event.getLevel(),
                event.getPos(),
                event.getState(),
                event.getEntity()
        );
        if (result.cancelsEvent()) {
            event.setCanceled(true);
        } else {
            if (event.getState().getBlock() instanceof CropBlock) {
                EventResult result1 = BlockEvent.PLANT_CROP.invoker().onPlantCrop(
                        (Level) event.getLevel(),
                        event.getPos(),
                        event.getState(),
                        event.getEntity() instanceof Player player ? player : null
                );
                if (result1.cancelsEvent()) {
                    event.setCanceled(true);
                }
            }
        }
    }
}