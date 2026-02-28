package com.daqem.knot;

import com.daqem.knot.events.EventsService;
import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.registry.Registrar;
import com.daqem.knot.registry.client.EntityRendererRegistry;
import com.daqem.knot.registry.client.ScreenRegistry;
import com.daqem.knot.registry.creativetab.CreativeTabsRegistry;
import com.daqem.knot.registry.entity.EntityAttributesRegistry;
import com.daqem.knot.registry.menu.MenuRegistry;
import com.daqem.knot.registry.resource.ReloadRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

/**
 * The core API class for Knot.
 * <p>
 * This class serves as the central entry point for all Knot services.
 * To use this in your mod, create a static constant instance:
 * <pre>
 * public static final Knot API = new Knot("my_mod_id");
 * </pre>
 * Then you can access helpers via instance methods like {@code MyMod.API.register(Registries.ITEM)}.
 * </p>
 */
public class Knot {

    private static final Services SERVICES = ServiceLoader.load(Services.class).findFirst().orElseThrow();

    public interface Events extends EventsService {}
    public static final NetworkingService NETWORKING = SERVICES.getNetworking();
    public static final Registrar REGISTRAR = SERVICES.getRegistry().getRegistrar();
    public static final MenuRegistry MENU_REGISTRY = SERVICES.getRegistry().getMenuRegistry();
    public static final CreativeTabsRegistry CREATIVE_TABS_REGISTRY = SERVICES.getRegistry().getCreativeTabsRegistry();
    public static final EntityAttributesRegistry ENTITY_ATTRIBUTES_REGISTRY = SERVICES.getRegistry().getEntityAttributesRegistry();
    public static final EntityRendererRegistry ENTITY_RENDERER_REGISTRY = SERVICES.getRegistry().getEntityRendererRegistry();
    public static final ScreenRegistry SCREEN_REGISTRY = SERVICES.getRegistry().getScreenRegistry();
    public static final ReloadRegistry RELOAD_REGISTRY = SERVICES.getRegistry().getReloadRegistry();

    private final String modId;
    public final Logger LOGGER;

    public Knot(@NotNull String modId) {
        this.modId = modId;
        this.LOGGER = LoggerFactory.getLogger(modId);
    }

    public String getModId() {
        return modId;
    }

    public Identifier getId(String path) {
        return Identifier.fromNamespaceAndPath(this.modId, path);
    }

    public MutableComponent translatable(String key) {
        return Component.translatable(this.modId + "." + key);
    }

    public MutableComponent translatable(String key, Object... args) {
        return Component.translatable(this.modId + "." + key, args);
    }

    public MutableComponent literal(String text) {
        return Component.literal(text);
    }

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