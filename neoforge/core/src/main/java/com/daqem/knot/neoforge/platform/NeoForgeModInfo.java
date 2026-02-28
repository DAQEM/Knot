package com.daqem.knot.neoforge.platform;

import com.daqem.knot.api.platform.ModInfo;
import net.neoforged.neoforgespi.language.IModInfo;

import java.nio.file.Path;
import java.util.List;

public class NeoForgeModInfo implements ModInfo {

    private final IModInfo info;

    public NeoForgeModInfo(IModInfo info) {
        this.info = info;
    }

    @Override
    public String getId() {
        return info.getModId();
    }

    @Override
    public String getName() {
        return info.getDisplayName();
    }

    @Override
    public String getVersion() {
        return info.getVersion().toString();
    }

    @Override
    public String getDescription() {
        return info.getDescription();
    }

    @Override
    public List<Path> getFilePaths() {
        return List.of(info.getOwningFile().getFile().getFilePath());
    }
}