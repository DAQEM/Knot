package com.daqem.frame;

import com.daqem.frame.network.Networking;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

/**
 * The core API class for Frame.
 * <p>
 * To use this in your mod, create a static constant instance:
 * <pre>
 * public static final Frame API = new Frame("my_mod_id");
 * </pre>
 * Then you can access helpers via {@code MyMod.API.info("...")} or {@code MyMod.API.getId("...")}.
 * </p>
 */
public class Frame {

    /**
     * The unified networking service for registering and sending packets.
     * Implementations are automatically discovered by the ServiceLoader per-platform.
     */
    public static final Networking NETWORKING = ServiceLoader.load(Networking.class)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No Frame Networking implementation found!"));

    private final String modId;

    /**
     * The raw SLF4J Logger instance for this mod.
     */
    public final Logger LOGGER;

    /**
     * Creates a new Frame API instance for your mod.
     * @param modId Your mod's ID (e.g. "my_mod")
     */
    public Frame(@NotNull String modId) {
        this.modId = modId;
        this.LOGGER = LoggerFactory.getLogger(modId);
    }

    /**
     * @return The Mod ID associated with this Frame instance.
     */
    public String getModId() {
        return modId;
    }

    /**
     * Creates an Identifier (ResourceLocation) for this mod.
     * <br>
     * Usage: {@code Test.API.getId("example_item")} -> {@code "my_mod:example_item"}
     */
    public Identifier getId(String path) {
        return Identifier.fromNamespaceAndPath(this.modId, path);
    }

    /**
     * Creates a translatable component using this mod's ID.
     * <br>
     * Usage: {@code Test.API.translatable("gui.title")} -> {@code "my_mod.gui.title"}
     */
    public MutableComponent translatable(String key) {
        return Component.translatable(this.modId + "." + key);
    }

    /**
     * Creates a translatable component with arguments using this mod's ID.
     */
    public MutableComponent translatable(String key, Object... args) {
        return Component.translatable(this.modId + "." + key, args);
    }

    /**
     * Creates a literal component.
     */
    public MutableComponent literal(String text) {
        return Component.literal(text);
    }

    // --- Logging Helpers ---

    public void info(String message) {
        LOGGER.info(message);
    }

    public void info(String message, Object... params) {
        LOGGER.info(message, params);
    }

    public void warn(String message) {
        LOGGER.warn(message);
    }

    public void warn(String message, Object... params) {
        LOGGER.warn(message, params);
    }

    public void error(String message) {
        LOGGER.error(message);
    }

    public void error(String message, Object... params) {
        LOGGER.error(message, params);
    }

    public void error(String message, Throwable t) {
        LOGGER.error(message, t);
    }
}