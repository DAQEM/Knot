package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.client.player.LocalPlayer;

public interface ClientPlayerEvent {
    Event<Join> JOIN = EventFactory.createLoop(Join.class);
    Event<Quit> QUIT = EventFactory.createLoop(Quit.class);
    Event<Respawn> RESPAWN = EventFactory.createLoop(Respawn.class);

    interface Join {
        void onJoin(LocalPlayer player);
    }

    interface Quit {
        void onQuit(LocalPlayer player);
    }

    interface Respawn {
        void onRespawn(LocalPlayer player);
    }
}