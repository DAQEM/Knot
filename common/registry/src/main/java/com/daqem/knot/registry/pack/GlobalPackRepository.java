package com.daqem.knot.registry.pack;

import com.daqem.knot.api.Logger;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.FileUtil;
import net.minecraft.world.level.validation.DirectoryValidator;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

public class GlobalPackRepository extends FolderRepositorySource {

    private final Path folder;
    private final PackType packType;
    private final PackSource packSource;

    private static final PackSelectionConfig FORCED_CONFIG = new PackSelectionConfig(true, Pack.Position.TOP, true);

    public GlobalPackRepository(Path folder, PackType packType, PackSource packSource) {
        super(folder, packType, packSource, new DirectoryValidator(path -> true));
        this.folder = folder;
        this.packType = packType;
        this.packSource = packSource;
    }

    @Override
    public void loadPacks(@NotNull Consumer<Pack> consumer) {
        try {
            FileUtil.createDirectoriesSafe(this.folder);

            FolderRepositorySource.discoverPacks(this.folder, new DirectoryValidator(path -> true), (path, resourcesSupplier) -> {

                String name = path.getFileName().toString();
                PackLocationInfo info = new PackLocationInfo(
                        "knot/" + name,
                        Component.literal(name),
                        this.packSource,
                        Optional.empty()
                );

                Pack pack = Pack.readMetaAndCreate(
                        info,
                        resourcesSupplier,
                        this.packType,
                        FORCED_CONFIG
                );

                if (pack != null) {
                    consumer.accept(pack);
                }
            });
        } catch (IOException e) {
            Logger.error("Failed to load global packs from {}", this.folder, e);
        }
    }
}