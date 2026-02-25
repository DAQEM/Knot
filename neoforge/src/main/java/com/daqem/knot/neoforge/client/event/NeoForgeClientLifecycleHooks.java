package com.daqem.knot.neoforge.client.event;

import com.daqem.knot.KnotMod;
import com.daqem.knot.event.lifecycle.KnotLevelLifecycleEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber(modid = KnotMod.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientLifecycleHooks {

    @SubscribeEvent
    public static void onClientLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ClientLevel clientLevel) {
            KnotLevelLifecycleEvent.CLIENT_LEVEL_LOAD.invoker().onClientLevelLoad(clientLevel);
        }
    }

    @SubscribeEvent
    public static void onClientLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ClientLevel clientLevel) {
            KnotLevelLifecycleEvent.CLIENT_LEVEL_UNLOAD.invoker().onClientLevelUnload(clientLevel);
        }
    }
}