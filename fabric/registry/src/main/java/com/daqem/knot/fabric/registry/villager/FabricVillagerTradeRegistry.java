package com.daqem.knot.fabric.registry.villager;

import com.daqem.knot.registry.villager.VillagerTradeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.Collections;

public class FabricVillagerTradeRegistry implements VillagerTradeRegistry {

    @Override
    public void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        TradeOfferHelper.registerVillagerOffers(profession, level, allTrades -> Collections.addAll(allTrades, trades));
    }

    @Override
    public void registerWanderingTrader(boolean rare, VillagerTrades.ItemListing... trades) {
        int level = rare ? 5 : 1;
        TradeOfferHelper.registerWanderingTraderOffers(level, allTrades -> Collections.addAll(allTrades, trades));
    }
}