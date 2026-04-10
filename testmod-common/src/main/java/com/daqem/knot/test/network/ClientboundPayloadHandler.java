package com.daqem.knot.test.network;

import com.daqem.knot.networking.ClientboundContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import org.jetbrains.annotations.NotNull;

public class ClientboundPayloadHandler {

    public static void handle(@NotNull ClientboundPayload clientboundPayload, ClientboundContext clientboundContext) {
        Minecraft.getInstance().setScreen(new PauseScreen(true));
    }
}
