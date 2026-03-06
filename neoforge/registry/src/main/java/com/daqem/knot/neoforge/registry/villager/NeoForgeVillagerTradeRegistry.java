package com.daqem.knot.neoforge.registry.villager;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.villager.VillagerTradeRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeVillagerTradeRegistry implements VillagerTradeRegistry {

    private static final Map<VillagerProfession, Map<Integer, List<VillagerTrades.ItemListing>>> VILLAGER_TRADES = new HashMap<>();
    private static final List<VillagerTrades.ItemListing> WANDERER_GENERIC_TRADES = new ArrayList<>();
    private static final List<VillagerTrades.ItemListing> WANDERER_RARE_TRADES = new ArrayList<>();

    @Override
    public void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        VILLAGER_TRADES.computeIfAbsent(profession, p -> new HashMap<>())
                .computeIfAbsent(level, l -> new ArrayList<>())
                .addAll(List.of(trades));
    }

    @Override
    public void registerWanderingTrader(boolean rare, VillagerTrades.ItemListing... trades) {
        if (rare) {
            WANDERER_RARE_TRADES.addAll(List.of(trades));
        } else {
            WANDERER_GENERIC_TRADES.addAll(List.of(trades));
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        Map<Integer, List<VillagerTrades.ItemListing>> map = VILLAGER_TRADES.get(event.getType());
        if (map != null) {
            map.forEach((level, trades) -> event.getTrades().get(level).addAll(trades));
        }
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(WANDERER_GENERIC_TRADES);
        event.getRareTrades().addAll(WANDERER_RARE_TRADES);
    }
}