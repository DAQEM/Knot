package com.daqem.frame.test.event;

import com.daqem.frame.Frame;
import com.daqem.frame.event.common.FrameTickEvent;
import com.daqem.frame.test.network.ClientboundPayload;
import com.daqem.frame.test.network.ServerboundPayload;
import net.minecraft.server.level.ServerPlayer;

public class ServerTickEvent {

    public static void registerEvent() {
        FrameTickEvent.PLAYER_PRE.register(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                Frame.NETWORKING.sendToPlayer(serverPlayer, new ClientboundPayload("Hello from the server!"));
            } else {
                Frame.NETWORKING.sendToServer(new ServerboundPayload("Hello from the client!"));
            }
        });
    }
}
