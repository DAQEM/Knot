package com.daqem.knot.fabric.platform;

import com.daqem.knot.platform.KnotEnvironment;
import com.daqem.knot.platform.KnotPlatform;
import com.daqem.knot.platform.ModInfo;
import com.daqem.knot.platform.PlatformInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class FabricPlatformInfo implements PlatformInfo {

    @Override
    public KnotPlatform getPlatform() {
        return KnotPlatform.FABRIC;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public Optional<ModInfo> getMod(String modId) {
        return FabricLoader.getInstance().getModContainer(modId).map(FabricModInfo::new);
    }

    @Override
    public Collection<ModInfo> getAllMods() {
        return FabricLoader.getInstance().getAllMods().stream()
                .map(FabricModInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    public KnotEnvironment getEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT
                ? KnotEnvironment.PHYSICAL_CLIENT
                : KnotEnvironment.DEDICATED_SERVER;
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Path getConfigFolder() {
        return FabricLoader.getInstance().getConfigDir().toAbsolutePath().normalize();
    }

    @Override
    public Path getGameFolder() {
        return FabricLoader.getInstance().getGameDir().toAbsolutePath().normalize();
    }

    @Override
    public Path getModsFolder() {
        return FabricLoader.getInstance().getGameDir().resolve("mods").toAbsolutePath().normalize();
    }
}