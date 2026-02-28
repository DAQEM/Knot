package com.daqem.knot.events.common.entity;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import com.daqem.knot.events.EventResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface EntityEvent {

    Event<PlayerDeath> PLAYER_DEATH = EventFactory.createEventResult(PlayerDeath.class);
    Event<PlayerKillEntity> PLAYER_KILL_ENTITY = EventFactory.createEventResult(PlayerKillEntity.class);
    Event<PlayerHurtEntity> PLAYER_HURT_ENTITY = EventFactory.createEventResult(PlayerHurtEntity.class);

    Event<BreedAnimal> BREED_ANIMAL = EventFactory.createEventResult(BreedAnimal.class);
    Event<TameAnimal> TAME_ANIMAL = EventFactory.createEventResult(TameAnimal.class);

    Event<InteractWithEntity> INTERACT_WITH_ENTITY = EventFactory.createEventResult(InteractWithEntity.class);

    Event<TradeWithVillager> TRADE_WITH_VILLAGER = EventFactory.createLoop(TradeWithVillager.class);

    Event<EnterSection> ENTER_SECTION = EventFactory.createLoop(EnterSection.class);
    Event<Add> ADD = EventFactory.createEventResult(Add.class);

    interface PlayerDeath {
        EventResult onPlayerDeath(ServerPlayer serverPlayer, DamageSource damageSource);
    }

    interface PlayerKillEntity {
        EventResult onPlayerKillEntity(ServerPlayer serverPlayer, LivingEntity entity, DamageSource damageSource);
    }

    interface PlayerHurtEntity {
        EventResult onPlayerHurtEntity(ServerPlayer serverPlayer, LivingEntity entity, DamageSource damageSource, MutableFloat damage);
    }

    interface BreedAnimal {
        EventResult onBreedAnimal(ServerLevel serverLevel, ServerPlayer serverPlayer, AgeableMob baby);
    }

    interface TameAnimal {
        EventResult onTameAnimal(Animal animal, Player player);
    }

    interface InteractWithEntity {
        EventResult onInteractWithEntity(Player player, Entity entity, InteractionHand hand);
    }

    interface TradeWithVillager {
        void onTradeWithVillager(Player player, Merchant merchant, MerchantOffer offer, ItemStack boughtStack);
    }

    interface EnterSection {
        /**
         * Fired whenever an entity crosses a chunk/section boundary.
         */
        void onEnterSection(Entity entity, long previousSectionPos, long newSectionPos);
    }

    interface Add {
        /**
         * Fired when an entity is added to a level (both client and server).
         */
        EventResult onAddEntity(Entity entity, Level level);
    }
}