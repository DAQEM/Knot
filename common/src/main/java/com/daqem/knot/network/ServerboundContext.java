package com.daqem.knot.network;

import net.minecraft.server.level.ServerPlayer;

public interface ServerboundContext {
    /**
     * @return The server-side player who sent the packet.
     */
    ServerPlayer player();
}