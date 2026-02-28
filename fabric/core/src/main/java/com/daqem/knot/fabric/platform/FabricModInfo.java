package com.daqem.knot.fabric.platform;

import com.daqem.knot.api.platform.ModInfo;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.nio.file.Path;
import java.util.List;

public class FabricModInfo implements ModInfo {

    private final ModContainer container;
    private final ModMetadata metadata;

    public FabricModInfo(ModContainer container) {
        this.metadata = container.getMetadata();
        this.container = container;
    }

    @Override
    public String getId() {
        return metadata.getId();
    }

    @Override
    public String getName() {
        return metadata.getName();
    }

    @Override
    public String getVersion() {
        return metadata.getVersion().getFriendlyString();
    }

    @Override
    public String getDescription() {
        return metadata.getDescription();
    }

    @Override
    public List<Path> getFilePaths() {
        return container.getRootPaths();
    }
}