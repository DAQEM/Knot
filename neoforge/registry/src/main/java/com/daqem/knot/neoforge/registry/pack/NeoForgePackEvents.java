package com.daqem.knot.neoforge.registry.pack;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.pack.GlobalPackPaths;
import com.daqem.knot.registry.pack.GlobalPackRepository;
import net.minecraft.server.packs.PackType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgePackEvents {

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            event.addRepositorySource(new GlobalPackRepository(
                    GlobalPackPaths.DATA_PACKS,
                    PackType.SERVER_DATA,
                    GlobalPackPaths.KNOT_PACK_SOURCE
            ));
        } else if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            event.addRepositorySource(new GlobalPackRepository(
                    GlobalPackPaths.RESOURCE_PACKS,
                    PackType.CLIENT_RESOURCES,
                    GlobalPackPaths.KNOT_PACK_SOURCE
            ));
        }
    }
}