package com.daqem.knot.neoforge;

import com.daqem.knot.KnotMod;
import com.daqem.knot.event.KnotEntityEvent;
import com.daqem.knot.event.KnotItemEvent;
import com.daqem.knot.event.KnotPlayerEvent;
import com.daqem.knot.event.EventResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerDestroyItemEvent;
import org.apache.commons.lang3.mutable.MutableFloat;

@Mod(KnotMod.MOD_ID)
@EventBusSubscriber(modid = KnotMod.MOD_ID)
public class KnotNeoForge {

    public KnotNeoForge() {
        KnotMod.init();
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        MutableFloat damage = new MutableFloat(event.getContainer().getNewDamage());
        DamageSource damageSource = event.getSource();
        LivingEntity defender = event.getEntity();
        if (defender instanceof ServerPlayer serverPlayer) {
            EventResult eventResult = KnotPlayerEvent.ENTITY_HURT_PLAYER.invoker().onEntityHurtPlayer(serverPlayer, damageSource, damage);
            if (eventResult.cancelsEvent()) {
                event.setCanceled(true);
                return;
            }
        }
        if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
            EventResult eventResult = KnotEntityEvent.PLAYER_HURT_ENTITY.invoker().onPlayerHurtEntity(serverPlayer, defender, damageSource, damage);
            if (eventResult.cancelsEvent()) {
                event.setCanceled(true);
                return;
            }
        }
        if (defender instanceof ServerPlayer serverPlayer && damageSource.getEntity() instanceof ServerPlayer attacker) {
            EventResult eventResult = KnotPlayerEvent.PLAYER_HURT_PLAYER.invoker().onPlayerHurtPlayer(attacker, serverPlayer, damageSource, damage);
            if (eventResult.cancelsEvent()) {
                event.setCanceled(true);
            }
        }
        event.getContainer().setNewDamage(damage.floatValue());
    }

    @SubscribeEvent
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        if (event.getEntity() instanceof Player player && event.getBlockedDamage() > 0.0F) {
            KnotPlayerEvent.BLOCK_WITH_SHIELD.invoker().onBlockWithShield(player, event.getDamageSource(), event.getBlockedDamage());
        }
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        EventResult eventResult = KnotItemEvent.DROP_ITEM.invoker().onDropItem(event.getPlayer(), event.getEntity());
        if (eventResult.cancelsEvent()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onDestroyItem(PlayerDestroyItemEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            KnotItemEvent.ITEM_BREAK.invoker().onItemBreak(serverPlayer, event.getOriginal());
        }
    }
}
