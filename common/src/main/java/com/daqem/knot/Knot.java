package com.daqem.knot;

import com.daqem.knot.client.registry.KnotEntityRendererRegistry;
import com.daqem.knot.item.creativetab.KnotCreativeTabsProvider;
import com.daqem.knot.item.creativetab.TabPopulator;
import com.daqem.knot.network.Networking;
import com.daqem.knot.platform.PlatformInfo;
import com.daqem.knot.registry.KnotRegistrar;
import com.daqem.knot.registry.KnotRegistry;
import com.daqem.knot.registry.entity.KnotEntityAttributes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

    /**
     * The unified networking service for registering and sending packets.
     */
    public static final Networking NETWORKING = loadService(Networking.class);

    /**
     * The platform utility service for checking loaded mods, environments, and configuration paths.
     */
    public static final PlatformInfo PLATFORM = loadService(PlatformInfo.class);

    /**
     * The internal registrar service for creating unified registries.
     */
    public static final KnotRegistrar REGISTRAR = loadService(KnotRegistrar.class);

    /**
     * The internal service for registering entity attributes.
     */
    public static final KnotEntityAttributes ENTITY_ATTRIBUTES = loadService(KnotEntityAttributes.class);

    /**
     * The internal service for registering entity renderers.
     */
    public static final KnotEntityRendererRegistry ENTITY_RENDERER = loadService(KnotEntityRendererRegistry.class);

    /**
     * The internal service for creating and modifying creative tabs.
     */
    public static final KnotCreativeTabsProvider CREATIVE_TABS = loadService(KnotCreativeTabsProvider.class);

    private final String modId;

    /**
     * The raw SLF4J Logger instance for this mod.
     */
    public final Logger LOGGER;

    /**
     * Creates a new Knot API instance for your mod.
     *
     * @param modId Your mod's ID (e.g. "my_mod")
     */
    public Knot(@NotNull String modId) {
        this.modId = modId;
        this.LOGGER = LoggerFactory.getLogger(modId);
    }

    private static <T> T loadService(Class<T> clazz) {
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No " + clazz.getSimpleName() + " implementation found!"));
    }

    /**
     * @return The Mod ID associated with this Knot instance.
     */
    public String getModId() {
        return modId;
    }

    /**
     * Creates an Identifier (ResourceLocation) for this mod.
     * <br>
     * Usage: {@code Test.API.getId("example_item")} -> {@code "my_mod:example_item"}
     *
     * @param path The path of the identifier.
     * @return An Identifier with this mod's namespace.
     */
    public Identifier getId(String path) {
        return Identifier.fromNamespaceAndPath(this.modId, path);
    }

    // --- Registry Helpers ---

    /**
     * Creates a new {@link KnotRegistry} wrapper for the given Vanilla Registry.
     * <p>
     * This wrapper automatically uses this Knot instance's Mod ID for all registrations.
     * </p>
     *
     * @param registry The vanilla registry to wrap (e.g. {@code BuiltInRegistries.BLOCK}).
     * @param <T>      The type of registry entry.
     * @return A new KnotRegistry instance.
     */
    public <T> KnotRegistry<T> register(Registry<T> registry) {
        return REGISTRAR.createRegistry(registry, this.modId);
    }

    /**
     * Registers default attributes for a custom LivingEntity.
     * <p>
     * This handles the loader-specific event hooks for attribute creation.
     * </p>
     *
     * @param type       A supplier providing the EntityType (e.g., your RegistryEntry).
     * @param attributes A supplier providing the AttributeSupplier.Builder (e.g., Pig::createAttributes).
     * @param <T>        The entity type.
     */
    public <T extends LivingEntity> void registerAttribute(Supplier<? extends EntityType<T>> type, Supplier<AttributeSupplier.Builder> attributes) {
        ENTITY_ATTRIBUTES.registerAttributes(type, attributes);
    }

    /**
     * Registers an entity renderer for a custom EntityType.
     * <p>
     * <b>Note:</b> This must ONLY be called on the physical client side.
     * Wrap this call in {@code Knot.PLATFORM.executeOnPhysicalClient(...)}.
     * </p>
     *
     * @param type     The registered EntityType supplier.
     * @param provider The renderer provider (e.g., PigRenderer::new).
     * @param <T>      The entity type.
     */
    public <T extends Entity> void registerEntityRenderer(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider) {
        ENTITY_RENDERER.registerRenderer(type, provider);
    }

    // --- Creative Tab Helpers ---

    /**
     * Builds a standard Creative Mode Tab.
     * <p>
     * Use this directly inside a {@code KnotRegistry.register(...)} call.
     * </p>
     *
     * @param title The translation component for the tab title.
     * @param icon  A supplier returning the ItemStack to display as the icon.
     * @return The constructed CreativeModeTab.
     */
    public CreativeModeTab createTab(Component title, Supplier<ItemStack> icon) {
        return CREATIVE_TABS.buildTab(title, icon);
    }

    /**
     * Modifies an existing Creative Mode Tab.
     * <p>
     * Perfect for adding your mod's items into vanilla tabs (e.g., Ingredients, Combat).
     * </p>
     *
     * @param tabKey    The ResourceKey of the tab to modify (e.g., {@code VanillaTabs.INGREDIENTS}).
     * @param populator A consumer providing the TabPopulator interface to add items.
     */
    public void modifyTab(ResourceKey<CreativeModeTab> tabKey, Consumer<TabPopulator> populator) {
        CREATIVE_TABS.modifyTab(tabKey, populator);
    }

    // --- Translation Helpers ---

    /**
     * Creates a translatable component using this mod's ID as the namespace prefix.
     * <br>
     * Usage: {@code Test.API.translatable("gui.title")} -> {@code Component.translatable("my_mod.gui.title")}
     *
     * @param key The suffix of the translation key.
     * @return A mutable component.
     */
    public MutableComponent translatable(String key) {
        return Component.translatable(this.modId + "." + key);
    }

    /**
     * Creates a translatable component with arguments using this mod's ID as the namespace prefix.
     *
     * @param key  The suffix of the translation key.
     * @param args Arguments for formatting.
     * @return A mutable component.
     */
    public MutableComponent translatable(String key, Object... args) {
        return Component.translatable(this.modId + "." + key, args);
    }

    /**
     * Creates a literal text component.
     *
     * @param text The raw text.
     * @return A mutable component.
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