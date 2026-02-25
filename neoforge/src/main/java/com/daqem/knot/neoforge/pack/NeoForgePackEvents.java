package com.daqem.knot.neoforge.pack;

import com.daqem.knot.KnotMod;
import com.daqem.knot.pack.GlobalPackPaths;
import com.daqem.knot.pack.KnotGlobalPackRepository;
import net.minecraft.server.packs.PackType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = KnotMod.MOD_ID)
public class NeoForgePackEvents {

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            event.addRepositorySource(new KnotGlobalPackRepository(
                    GlobalPackPaths.DATA_PACKS,
                    PackType.SERVER_DATA,
                    GlobalPackPaths.KNOT_PACK_SOURCE
            ));
        } else if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            event.addRepositorySource(new KnotGlobalPackRepository(
                    GlobalPackPaths.RESOURCE_PACKS,
                    PackType.CLIENT_RESOURCES,
                    GlobalPackPaths.KNOT_PACK_SOURCE
            ));
        }
    }
}