package com.daqem.knot.fabric.registry.villager;

import com.daqem.knot.registry.villager.VillagerTradeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;

import java.util.Collections;

public class FabricVillagerTradeRegistry implements VillagerTradeRegistry {

    @Override
    public void registerVillagerTrade(ResourceKey<VillagerProfession> profession, int level, VillagerTrades.ItemListing... trades) {
        TradeOfferHelper.registerVillagerOffers(profession, level, allTrades -> Collections.addAll(allTrades, trades));
    }

    @Override
    public void registerWanderingTrader(boolean rare, VillagerTrades.ItemListing... trades) {
        TradeOfferHelper.registerWanderingTraderOffers(pool -> {
            if (rare) pool.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL, trades);
            else pool.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL, trades);
        });
    }
}