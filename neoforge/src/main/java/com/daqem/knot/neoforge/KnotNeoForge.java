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
}
