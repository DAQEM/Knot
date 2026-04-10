package com.daqem.knot.neoforge.events.client;

import com.daqem.knot.api.Constants;
import com.daqem.knot.events.client.ClientCommandEvent;
import com.daqem.knot.events.client.ClientLevelLifecycleEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientEventHooks {

    @SubscribeEvent
    public static void onClientLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ClientLevel clientLevel) {
            ClientLevelLifecycleEvent.CLIENT_LEVEL_LOAD.invoker().onClientLevelLoad(clientLevel);
        }
    }

    @SubscribeEvent
    public static void onClientLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ClientLevel clientLevel) {
            ClientLevelLifecycleEvent.CLIENT_LEVEL_UNLOAD.invoker().onClientLevelUnload(clientLevel);
        }
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        ClientCommandEvent.REGISTER.invoker().onRegister(event.getDispatcher());
    }
}