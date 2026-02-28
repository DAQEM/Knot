package com.daqem.knot.api.platform;

import java.nio.file.Path;
import java.util.List;

/**
 * A platform-agnostic representation of a loaded mod's metadata.
 */
public interface ModInfo {

    /**
     * @return The unique identifier of the mod (e.g., "knot").
     */
    String getId();

    /**
     * @return The display name of the mod (e.g., "Knot").
     */
    String getName();

    /**
     * @return The version string of the mod.
     */
    String getVersion();

    /**
     * @return The description of the mod.
     */
    String getDescription();

    /**
     * Gets a list of all root paths for this mod.
     * (Fabric mods can have multiple root paths if they use nested jars or specific source sets. NeoForge usually has one).
     * Useful for reading internal assets via NIO (e.g. Files.readAllBytes(path.resolve("data/..."))).
     *
     * @return A list of root paths belonging to the mod.
     */
    List<Path> getFilePaths();
}