package com.daqem.knot.event.client;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public interface KnotClientsideTickEvent {

    Event<Client> CLIENT_PRE = EventFactory.createLoop(Client.class);
    Event<Client> CLIENT_POST = EventFactory.createLoop(Client.class);

    Event<ClientLevel> CLIENT_LEVEL_PRE = EventFactory.createLoop(ClientLevel.class);
    Event<ClientLevel> CLIENT_LEVEL_POST = EventFactory.createLoop(ClientLevel.class);

    Event<ClientPlayer> CLIENT_PLAYER_PRE = EventFactory.createLoop(ClientPlayer.class);
    Event<ClientPlayer> CLIENT_PLAYER_POST = EventFactory.createLoop(ClientPlayer.class);

    interface Client {
        void tick(Minecraft server);
    }

    interface ClientLevel {
        void tick(net.minecraft.client.multiplayer.ClientLevel level);
    }

    interface ClientPlayer {
        void tick(LocalPlayer player);
    }
}
