package com.daqem.knot.api.platform;

import net.minecraft.SharedConstants;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * The core platform utility service for Knot.
 * Loaded internally via ServiceLoader.
 */
public interface PlatformInfo {

    /**
     * @return The current platform (e.g., FABRIC or NEOFORGE).
     */
    KnotPlatform getPlatform();

    /**
     * Checks if a mod with the given mod ID is currently loaded.
     *
     * @param modId The ID of the mod to check (e.g., "jei", "rei")
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Gets a loaded mod by its ID.
     *
     * @param modId The ID of the mod.
     * @return An Optional containing the mod's information, or empty if not found.
     */
    Optional<ModInfo> getMod(String modId);

    /**
     * Gets a collection of all currently loaded mods.
     *
     * @return A collection of all mods.
     */
    Collection<ModInfo> getAllMods();

    /**
     * @return The current physical environment (CLIENT or SERVER).
     */
    KnotEnvironment getEnvironment();

    /**
     * @return True if the game is currently running in a development environment (e.g. from an IDE).
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the path to the current instance's "config" folder.
     * Use this to safely load and save your mod configurations.
     *
     * @return The absolute and normalized path to the config directory.
     */
    Path getConfigFolder();

    /**
     * Gets the path to the current instance's root game folder.
     *
     * @return The absolute and normalized path to the game directory.
     */
    Path getGameFolder();

    /**
     * Gets the path to the current instance's "mods" folder.
     */
    Path getModsFolder();

    /**
     * @return The current Minecraft version string (e.g. "1.21.11").
     */
    default String getMinecraftVersion() {
        return SharedConstants.getCurrentVersion().getId();
    }

    /**
     * Safely executes code only if the game is running on the Physical Client.
     * Use this during mod initialization to register rendering, screens, and keybinds
     * without causing a ClassNotFoundException on the Dedicated Server.
     * <br>
     * Example: {@code Knot.PLATFORM.executeOnPhysicalClient(() -> MyClientCode::init)}
     *
     * @param runnableSupplier A supplier providing the runnable to execute.
     */
    default void executeOnPhysicalClient(Supplier<Runnable> runnableSupplier) {
        if (getEnvironment() == KnotEnvironment.PHYSICAL_CLIENT) {
            runnableSupplier.get().run();
        }
    }

    /**
     * Safely executes code only if the game is running on the Dedicated Server.
     *
     * @param runnableSupplier A supplier providing the runnable to execute.
     */
    default void executeOnDedicatedServer(Supplier<Runnable> runnableSupplier) {
        if (getEnvironment() == KnotEnvironment.DEDICATED_SERVER) {
            runnableSupplier.get().run();
        }
    }
}