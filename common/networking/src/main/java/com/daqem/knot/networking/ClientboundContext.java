package com.daqem.knot.networking;

import net.minecraft.client.player.LocalPlayer;

public interface ClientboundContext {
    /**
     * @return The local client player.
     */
    LocalPlayer player();
}