package com.daqem.knot.network;

import net.minecraft.world.entity.player.Player;

public interface ClientboundContext {
    /**
     * @return The local client player.
     */
    Player player();
}