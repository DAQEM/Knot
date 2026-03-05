package com.daqem.knot.neoforge.platform;

import com.daqem.knot.api.platform.KnotEnvironment;
import com.daqem.knot.api.platform.KnotPlatform;
import com.daqem.knot.api.platform.ModInfo;
import com.daqem.knot.api.platform.PlatformInfo;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class NeoForgePlatformInfo implements PlatformInfo {

    @Override
    public KnotPlatform getPlatform() {
        return KnotPlatform.NEOFORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public Optional<ModInfo> getMod(String modId) {
        return ModList.get().getMods().stream()
                .filter(info -> info.getModId().equals(modId))
                .findFirst()
                .map(NeoForgeModInfo::new);
    }

    @Override
    public Collection<ModInfo> getAllMods() {
        return ModList.get().getMods().stream()
                .map(NeoForgeModInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    public KnotEnvironment getEnvironment() {
        return FMLEnvironment.dist == Dist.CLIENT
                ? KnotEnvironment.PHYSICAL_CLIENT
                : KnotEnvironment.DEDICATED_SERVER;
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLEnvironment.production;
    }

    @Override
    public Path getConfigFolder() {
        return FMLPaths.CONFIGDIR.get().toAbsolutePath().normalize();
    }

    @Override
    public Path getGameFolder() {
        return FMLPaths.GAMEDIR.get().toAbsolutePath().normalize();
    }

    @Override
    public Path getModsFolder() {
        return FMLPaths.MODSDIR.get().toAbsolutePath().normalize();
    }
}