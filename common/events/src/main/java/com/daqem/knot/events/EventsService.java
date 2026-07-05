package com.daqem.knot.events;

import com.daqem.knot.events.client.*;
import com.daqem.knot.events.server.ServerLevelLifecycleEvent;
import com.daqem.knot.events.common.TickEvent;
import com.daqem.knot.events.common.block.BlockEvent;
import com.daqem.knot.events.common.entity.EntityEvent;
import com.daqem.knot.events.common.entity.player.AdvancementEvent;
import com.daqem.knot.events.common.entity.player.MovementEvent;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import com.daqem.knot.events.common.item.ItemEvent;
import com.daqem.knot.events.common.loot.LootEvent;
import com.daqem.knot.events.server.*;

public interface EventsService {

    interface Block {
        Event<BlockEvent.BreakBlock> BREAK_BLOCK = BlockEvent.BREAK_BLOCK;
        Event<BlockEvent.PlaceBlock> PLACE_BLOCK = BlockEvent.PLACE_BLOCK;
        Event<BlockEvent.RightClickBlock> RIGHT_CLICK_BLOCK = BlockEvent.RIGHT_CLICK_BLOCK;
        Event<BlockEvent.PlantCrop> PLANT_CROP = BlockEvent.PLANT_CROP;
        Event<BlockEvent.HarvestCrop> HARVEST_CROP = BlockEvent.HARVEST_CROP;
        Event<BlockEvent.GetDestroySpeed> GET_DESTROY_SPEED = BlockEvent.GET_DESTROY_SPEED;
        Event<BlockEvent.TillSoil> TILL_SOIL = BlockEvent.TILL_SOIL;
        Event<BlockEvent.FarmlandTrample> FARMLAND_TRAMPLE = BlockEvent.FARMLAND_TRAMPLE;
        Event<BlockEvent.FallingLand> FALLING_LAND = BlockEvent.FALLING_LAND;
        Event<BlockEvent.LeftClickBlock> LEFT_CLICK_BLOCK = BlockEvent.LEFT_CLICK_BLOCK;
    }

    interface Player {
        Event<PlayerEvent.EntityHurtPlayer> ENTITY_HURT_PLAYER = PlayerEvent.ENTITY_HURT_PLAYER;
        Event<PlayerEvent.PlayerHurtPlayer> PLAYER_HURT_PLAYER = PlayerEvent.PLAYER_HURT_PLAYER;
        Event<PlayerEvent.ShootProjectile> SHOOT_PROJECTILE = PlayerEvent.SHOOT_PROJECTILE;
        Event<PlayerEvent.RodReelIn> ROD_REEL_IN = PlayerEvent.ROD_REEL_IN;
        Event<PlayerEvent.BrewPotion> BREW_POTION = PlayerEvent.BREW_POTION;
        Event<PlayerEvent.AddEffect> ADD_EFFECT = PlayerEvent.ADD_EFFECT;
        Event<PlayerEvent.EnchantItem> ENCHANT_ITEM = PlayerEvent.ENCHANT_ITEM;
        Event<PlayerEvent.FishUpItem> FISH_UP_ITEM = PlayerEvent.FISH_UP_ITEM;
        Event<PlayerEvent.GrindItem> GRIND_ITEM = PlayerEvent.GRIND_ITEM;
        Event<PlayerEvent.SmeltItem> SMELT_ITEM = PlayerEvent.SMELT_ITEM;
        Event<PlayerEvent.StripLog> STRIP_LOG = PlayerEvent.STRIP_LOG;
        Event<PlayerEvent.UseAnvil> USE_ANVIL = PlayerEvent.USE_ANVIL;
        Event<PlayerEvent.Drink> DRINK = PlayerEvent.DRINK;
        Event<PlayerEvent.Eat> EAT = PlayerEvent.EAT;
        Event<PlayerEvent.GetAttackSpeed> GET_ATTACK_SPEED = PlayerEvent.GET_ATTACK_SPEED;
        Event<PlayerEvent.Jump> JUMP = PlayerEvent.JUMP;
        Event<PlayerEvent.LandOnGround> LAND_ON_GROUND = PlayerEvent.LAND_ON_GROUND;
        Event<PlayerEvent.BlockWithShield> BLOCK_WITH_SHIELD = PlayerEvent.BLOCK_WITH_SHIELD;
        Event<PlayerEvent.ChangeDimension> CHANGE_DIMENSION = PlayerEvent.CHANGE_DIMENSION;
        Event<PlayerEvent.PlayerJoin> PLAYER_JOIN = PlayerEvent.PLAYER_JOIN;
        Event<PlayerEvent.PlayerQuit> PLAYER_QUIT = PlayerEvent.PLAYER_QUIT;
        Event<PlayerEvent.PlayerRespawn> PLAYER_RESPAWN = PlayerEvent.PLAYER_RESPAWN;
        Event<PlayerEvent.PlayerClone> PLAYER_CLONE = PlayerEvent.PLAYER_CLONE;
    }

    interface Item {
        Event<ItemEvent.DropItem> DROP_ITEM = ItemEvent.DROP_ITEM;
        Event<ItemEvent.CraftItem> CRAFT_ITEM = ItemEvent.CRAFT_ITEM;
        Event<ItemEvent.HurtItem> HURT_ITEM = ItemEvent.HURT_ITEM;
        Event<ItemEvent.ThrowItem> THROW_ITEM = ItemEvent.THROW_ITEM;
        Event<ItemEvent.UseItem> USE_ITEM = ItemEvent.USE_ITEM;
        Event<ItemEvent.PickupItem> PICKUP_ITEM = ItemEvent.PICKUP_ITEM;
        Event<ItemEvent.FillBucket> FILL_BUCKET = ItemEvent.FILL_BUCKET;
        Event<ItemEvent.EmptyBucket> EMPTY_BUCKET = ItemEvent.EMPTY_BUCKET;
        Event<ItemEvent.ItemBreak> ITEM_BREAK = ItemEvent.ITEM_BREAK;
    }

    interface Entity {
        Event<EntityEvent.PlayerDeath> PLAYER_DEATH = EntityEvent.PLAYER_DEATH;
        Event<EntityEvent.PlayerKillEntity> PLAYER_KILL_ENTITY = EntityEvent.PLAYER_KILL_ENTITY;
        Event<EntityEvent.PlayerHurtEntity> PLAYER_HURT_ENTITY = EntityEvent.PLAYER_HURT_ENTITY;
        Event<EntityEvent.BreedAnimal> BREED_ANIMAL = EntityEvent.BREED_ANIMAL;
        Event<EntityEvent.TameAnimal> TAME_ANIMAL = EntityEvent.TAME_ANIMAL;
        Event<EntityEvent.InteractWithEntity> INTERACT_WITH_ENTITY = EntityEvent.INTERACT_WITH_ENTITY;
        Event<EntityEvent.TradeWithVillager> TRADE_WITH_VILLAGER = EntityEvent.TRADE_WITH_VILLAGER;
        Event<EntityEvent.EnterSection> ENTER_SECTION = EntityEvent.ENTER_SECTION;
        Event<EntityEvent.Add> ADD = EntityEvent.ADD;
    }

    interface Client {
        Event<ClientChatEvent.Send> CHAT_SEND = ClientChatEvent.SEND;
        Event<ClientChatEvent.Receive> CHAT_RECEIVE = ClientChatEvent.RECEIVE;
        Event<ClientChatEvent.SystemMessage> CHAT_SYSTEM_MESSAGE = ClientChatEvent.SYSTEM_MESSAGE;
        Event<ClientHudEvent.Render> HUD_RENDER = ClientHudEvent.RENDER;
        Event<ClientHudEvent.DebugText> HUD_DEBUG_TEXT_LEFT = ClientHudEvent.DEBUG_TEXT_LEFT;
        Event<ClientHudEvent.DebugText> HUD_DEBUG_TEXT_RIGHT = ClientHudEvent.DEBUG_TEXT_RIGHT;
        Event<ClientInteractionEvent.LeftClickAir> INTERACTION_LEFT_CLICK_AIR = ClientInteractionEvent.LEFT_CLICK_AIR;
        Event<ClientInteractionEvent.RightClickAir> INTERACTION_RIGHT_CLICK_AIR = ClientInteractionEvent.RIGHT_CLICK_AIR;
        Event<ClientLifecycleEvent.ClientStarted> LIFECYCLE_STARTED = ClientLifecycleEvent.STARTED;
        Event<ClientLifecycleEvent.ClientStopping> LIFECYCLE_STOPPING = ClientLifecycleEvent.STOPPING;
        Event<ClientRawInputEvent.KeyPressed> RAW_INPUT_KEY_PRESSED = ClientRawInputEvent.KEY_PRESSED;
        Event<ClientRawInputEvent.MouseClicked> RAW_INPUT_MOUSE_CLICKED_PRE = ClientRawInputEvent.MOUSE_CLICKED_PRE;
        Event<ClientRawInputEvent.MouseClickedPost> RAW_INPUT_MOUSE_CLICKED_POST = ClientRawInputEvent.MOUSE_CLICKED_POST;
        Event<ClientRawInputEvent.MouseScrolled> RAW_INPUT_MOUSE_SCROLLED = ClientRawInputEvent.MOUSE_SCROLLED;
        Event<ClientRecipeEvent.Update> RECIPE_UPDATE = ClientRecipeEvent.UPDATE;
        Event<ClientScreenEvent.BeforeOpen> SCREEN_BEFORE_OPEN = ClientScreenEvent.BEFORE_OPEN;
        Event<ClientScreenEvent.BeforeInit> SCREEN_BEFORE_INIT = ClientScreenEvent.BEFORE_INIT;
        Event<ClientScreenEvent.AfterInit> SCREEN_AFTER_INIT = ClientScreenEvent.AFTER_INIT;
        Event<ClientScreenEvent.BeforeRender> SCREEN_BEFORE_RENDER = ClientScreenEvent.BEFORE_RENDER;
        Event<ClientScreenEvent.AfterRender> SCREEN_AFTER_RENDER = ClientScreenEvent.AFTER_RENDER;
        Event<ClientScreenEvent.RenderContainerForeground> SCREEN_RENDER_CONTAINER_FOREGROUND = ClientScreenEvent.RENDER_CONTAINER_FOREGROUND;
        Event<ClientScreenInputEvent.MouseScrolled> SCREEN_INPUT_MOUSE_SCROLLED_PRE = ClientScreenInputEvent.MOUSE_SCROLLED_PRE;
        Event<ClientScreenInputEvent.MouseScrolledPost> SCREEN_INPUT_MOUSE_SCROLLED_POST = ClientScreenInputEvent.MOUSE_SCROLLED_POST;
        Event<ClientScreenInputEvent.MouseClicked> SCREEN_INPUT_MOUSE_CLICKED_PRE = ClientScreenInputEvent.MOUSE_CLICKED_PRE;
        Event<ClientScreenInputEvent.MouseClickedPost> SCREEN_INPUT_MOUSE_CLICKED_POST = ClientScreenInputEvent.MOUSE_CLICKED_POST;
        Event<ClientScreenInputEvent.MouseReleased> SCREEN_INPUT_MOUSE_RELEASED_PRE = ClientScreenInputEvent.MOUSE_RELEASED_PRE;
        Event<ClientScreenInputEvent.MouseReleasedPost> SCREEN_INPUT_MOUSE_RELEASED_POST = ClientScreenInputEvent.MOUSE_RELEASED_POST;
        Event<ClientScreenInputEvent.MouseDragged> SCREEN_INPUT_MOUSE_DRAGGED_PRE = ClientScreenInputEvent.MOUSE_DRAGGED_PRE;
        Event<ClientScreenInputEvent.MouseDraggedPost> SCREEN_INPUT_MOUSE_DRAGGED_POST = ClientScreenInputEvent.MOUSE_DRAGGED_POST;
        Event<ClientScreenInputEvent.KeyPressed> SCREEN_INPUT_KEY_PRESSED_PRE = ClientScreenInputEvent.KEY_PRESSED_PRE;
        Event<ClientScreenInputEvent.KeyPressedPost> SCREEN_INPUT_KEY_PRESSED_POST = ClientScreenInputEvent.KEY_PRESSED_POST;
        Event<ClientScreenInputEvent.KeyReleased> SCREEN_INPUT_KEY_RELEASED_PRE = ClientScreenInputEvent.KEY_RELEASED_PRE;
        Event<ClientScreenInputEvent.KeyReleasedPost> SCREEN_INPUT_KEY_RELEASED_POST = ClientScreenInputEvent.KEY_RELEASED_POST;
        Event<ClientScreenInputEvent.CharTyped> SCREEN_INPUT_CHAR_TYPED_PRE = ClientScreenInputEvent.CHAR_TYPED_PRE;
        Event<ClientScreenInputEvent.CharTypedPost> SCREEN_INPUT_CHAR_TYPED_POST = ClientScreenInputEvent.CHAR_TYPED_POST;
        Event<ClientTooltipEvent.GatherComponents> TOOLTIP_GATHER_COMPONENTS = ClientTooltipEvent.GATHER_COMPONENTS;
        Event<ClientTooltipEvent.BeforeRender> TOOLTIP_BEFORE_RENDER = ClientTooltipEvent.BEFORE_RENDER;
        Event<ClientTooltipEvent.AdjustPosition> TOOLTIP_ADJUST_POSITION = ClientTooltipEvent.ADJUST_POSITION;
        Event<ClientPlayerEvent.Join> PLAYER_JOIN = ClientPlayerEvent.JOIN;
        Event<ClientPlayerEvent.Quit> PLAYER_QUIT = ClientPlayerEvent.QUIT;
        Event<ClientPlayerEvent.Respawn> PLAYER_RESPAWN = ClientPlayerEvent.RESPAWN;
        Event<ClientCommandEvent.Register> COMMAND_REGISTER = ClientCommandEvent.REGISTER;

        interface Tick {
            Event<ClientTickEvent.Client> CLIENT_PRE = ClientTickEvent.CLIENT_PRE;
            Event<ClientTickEvent.Client> CLIENT_POST = ClientTickEvent.CLIENT_POST;
            Event<ClientTickEvent.ClientLevel> CLIENT_LEVEL_PRE = ClientTickEvent.CLIENT_LEVEL_PRE;
            Event<ClientTickEvent.ClientLevel> CLIENT_LEVEL_POST = ClientTickEvent.CLIENT_LEVEL_POST;
            Event<ClientTickEvent.ClientPlayer> CLIENT_PLAYER_PRE = ClientTickEvent.CLIENT_PLAYER_PRE;
            Event<ClientTickEvent.ClientPlayer> CLIENT_PLAYER_POST = ClientTickEvent.CLIENT_PLAYER_POST;
        }

        interface LevelLifecycle {
            Event<ClientLevelLifecycleEvent.ClientLevelLoad> CLIENT_LEVEL_LOAD = ClientLevelLifecycleEvent.CLIENT_LEVEL_LOAD;
            Event<ClientLevelLifecycleEvent.ClientLevelUnload> CLIENT_LEVEL_UNLOAD = ClientLevelLifecycleEvent.CLIENT_LEVEL_UNLOAD;
        }
    }

    interface Server {
        Event<ServerChatEvent.Decorate> CHAT_DECORATE = ServerChatEvent.DECORATE;
        Event<ServerChatEvent.Received> CHAT_RECEIVED = ServerChatEvent.RECEIVED;
        Event<ServerChunkEvent.SaveData> CHUNK_SAVE_DATA = ServerChunkEvent.SAVE_DATA;
        Event<ServerChunkEvent.LoadData> CHUNK_LOAD_DATA = ServerChunkEvent.LOAD_DATA;
        Event<ServerChunkEvent.Load> CHUNK_LOAD = ServerChunkEvent.LOAD;
        Event<ServerChunkEvent.Unload> CHUNK_UNLOAD = ServerChunkEvent.UNLOAD;
        Event<ServerCommandEvent.Register> COMMAND_REGISTER = ServerCommandEvent.REGISTER;
        Event<ServerCommandEvent.Perform> COMMAND_PERFORM = ServerCommandEvent.PERFORM;
        Event<ServerExplosionEvent.Pre> EXPLOSION_PRE = ServerExplosionEvent.PRE;
        Event<ServerExplosionEvent.Detonate> EXPLOSION_DETONATE = ServerExplosionEvent.DETONATE;
        Event<ServerLifecycleEvent.ServerBeforeStart> LIFECYCLE_BEFORE_START = ServerLifecycleEvent.BEFORE_START;
        Event<ServerLifecycleEvent.ServerStarting> LIFECYCLE_STARTING = ServerLifecycleEvent.STARTING;
        Event<ServerLifecycleEvent.ServerStarted> LIFECYCLE_STARTED = ServerLifecycleEvent.STARTED;
        Event<ServerLifecycleEvent.ServerStopping> LIFECYCLE_STOPPING = ServerLifecycleEvent.STOPPING;
        Event<ServerLifecycleEvent.ServerStopped> LIFECYCLE_STOPPED = ServerLifecycleEvent.STOPPED;
        Event<ServerLightningEvent.Strike> LIGHTNING_STRIKE = ServerLightningEvent.STRIKE;

        interface Tick {
            Event<ServerTickEvent.Server> DEDICATED_SERVER_PRE = ServerTickEvent.DEDICATED_SERVER_PRE;
            Event<ServerTickEvent.Server> DEDICATED_SERVER_POST = ServerTickEvent.DEDICATED_SERVER_POST;
            Event<ServerTickEvent.ServerLevel> SERVER_LEVEL_PRE = ServerTickEvent.SERVER_LEVEL_PRE;
            Event<ServerTickEvent.ServerLevel> SERVER_LEVEL_POST = ServerTickEvent.SERVER_LEVEL_POST;
            Event<ServerTickEvent.ServerPlayer> SERVER_PLAYER_PRE = ServerTickEvent.SERVER_PLAYER_PRE;
            Event<ServerTickEvent.ServerPlayer> SERVER_PLAYER_POST = ServerTickEvent.SERVER_PLAYER_POST;
        }

        interface LevelLifecycle {
            Event<ServerLevelLifecycleEvent.ServerLevelLoad> SERVER_LEVEL_LOAD = ServerLevelLifecycleEvent.SERVER_LEVEL_LOAD;
            Event<ServerLevelLifecycleEvent.ServerLevelUnload> SERVER_LEVEL_UNLOAD = ServerLevelLifecycleEvent.SERVER_LEVEL_UNLOAD;
            Event<ServerLevelLifecycleEvent.ServerLevelSave> SERVER_LEVEL_SAVE = ServerLevelLifecycleEvent.SERVER_LEVEL_SAVE;
        }
    }

    interface Loot {
        Event<LootEvent.ModifyLootTable> MODIFY_LOOT_TABLE = LootEvent.MODIFY_LOOT_TABLE;
    }

    interface Tick {
        Event<TickEvent.Server> SERVER_PRE = TickEvent.SERVER_PRE;
        Event<TickEvent.Server> SERVER_POST = TickEvent.SERVER_POST;
        Event<TickEvent.Level> LEVEL_PRE = TickEvent.LEVEL_PRE;
        Event<TickEvent.Level> LEVEL_POST = TickEvent.LEVEL_POST;
        Event<TickEvent.Player> PLAYER_PRE = TickEvent.PLAYER_PRE;
        Event<TickEvent.Player> PLAYER_POST = TickEvent.PLAYER_POST;
    }

    interface Movement {
        Event<MovementEvent.Walk> WALK = MovementEvent.WALK;
        Event<MovementEvent.StartWalk> START_WALK = MovementEvent.START_WALK;
        Event<MovementEvent.StopWalk> STOP_WALK = MovementEvent.STOP_WALK;
        Event<MovementEvent.Sprint> SPRINT = MovementEvent.SPRINT;
        Event<MovementEvent.StartSprint> START_SPRINT = MovementEvent.START_SPRINT;
        Event<MovementEvent.StopSprint> STOP_SPRINT = MovementEvent.STOP_SPRINT;
        Event<MovementEvent.Swim> SWIM = MovementEvent.SWIM;
        Event<MovementEvent.StartSwim> START_SWIM = MovementEvent.START_SWIM;
        Event<MovementEvent.StopSwim> STOP_SWIM = MovementEvent.STOP_SWIM;
        Event<MovementEvent.Crouch> CROUCH = MovementEvent.CROUCH;
        Event<MovementEvent.StartCrouch> START_CROUCH = MovementEvent.START_CROUCH;
        Event<MovementEvent.StopCrouch> STOP_CROUCH = MovementEvent.STOP_CROUCH;
        Event<MovementEvent.ElytraFly> ELYTRA_FLY = MovementEvent.ELYTRA_FLY;
        Event<MovementEvent.StartElytraFly> START_ELYTRA_FLY = MovementEvent.START_ELYTRA_FLY;
        Event<MovementEvent.StopElytraFly> STOP_ELYTRA_FLY = MovementEvent.STOP_ELYTRA_FLY;
        Event<MovementEvent.HorseRide> HORSE_RIDE = MovementEvent.HORSE_RIDE;
        Event<MovementEvent.StartHorseRide> START_HORSE_RIDE = MovementEvent.START_HORSE_RIDE;
        Event<MovementEvent.StopHorseRide> STOP_HORSE_RIDE = MovementEvent.STOP_HORSE_RIDE;
    }

    interface Advancement {
        Event<AdvancementEvent.Advancement> ADVANCEMENT = AdvancementEvent.ADVANCEMENT;
    }
}