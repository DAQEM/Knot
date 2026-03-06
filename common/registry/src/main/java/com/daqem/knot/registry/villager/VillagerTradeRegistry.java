package com.daqem.knot.registry.villager;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

public interface VillagerTradeRegistry {

    void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing... trades);

    void registerWanderingTrader(boolean rare, VillagerTrades.ItemListing... trades);

}