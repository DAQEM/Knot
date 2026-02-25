package com.daqem.knot.mixin;

import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FolderRepositorySource.class)
public abstract class MixinFolderRepositorySource {

    @Final
    @Shadow
    private PackType packType;

    @Unique
    public PackType knot$getPackType() {
        return this.packType;
    }
}