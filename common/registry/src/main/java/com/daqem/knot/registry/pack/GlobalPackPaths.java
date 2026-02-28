package com.daqem.knot.registry.pack;

import com.daqem.knot.api.Logger;
import com.daqem.knot.api.platform.Platform;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.PackSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.UnaryOperator;

public class GlobalPackPaths {

    // config -> knot -> global_packs -> [data/resources]
    private static final Path ROOT = Platform.INFO.getConfigFolder().resolve("knot").resolve("global_packs");
    public static final Path DATA_PACKS = ROOT.resolve("data");
    public static final Path RESOURCE_PACKS = ROOT.resolve("resources");

    // "true" in the second argument makes these packs "Required" in the UI (cannot be disabled)
    public static final PackSource KNOT_PACK_SOURCE = PackSource.create(
            decorate("Knot Global"),
            true
    );

    public static void init() {
        try {
            if (!Files.exists(DATA_PACKS)) Files.createDirectories(DATA_PACKS);
            if (!Files.exists(RESOURCE_PACKS)) Files.createDirectories(RESOURCE_PACKS);
        } catch (IOException e) {
            Logger.error("Failed to create global pack directories", e);
        }
    }

    private static UnaryOperator<Component> decorate(String name) {
        return (component) -> Component.translatable("pack.nameAndSource", component, Component.literal(name).withStyle(style -> style.withColor(0xFFD700)));
    }
}