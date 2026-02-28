package com.daqem.knot.test.event;

import com.daqem.knot.Knot;
import com.daqem.knot.test.network.ClientboundPayload;
import com.daqem.knot.test.network.ServerboundPayload;
import net.minecraft.server.level.ServerPlayer;

public class ServerTickEvent {

    public static void registerEvent() {
        Knot.Events.Tick.PLAYER_PRE.register(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                Knot.NETWORKING.sendToPlayer(serverPlayer, new ClientboundPayload("Hello from the server!"));
            } else {
                Knot.NETWORKING.sendToServer(new ServerboundPayload("Hello from the client!"));
            }
        });
    }
}
