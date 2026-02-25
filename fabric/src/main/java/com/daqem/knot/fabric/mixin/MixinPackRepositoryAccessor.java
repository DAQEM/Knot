package com.daqem.knot.fabric.mixin;

import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(PackRepository.class)
public interface MixinPackRepositoryAccessor {

    @Accessor("sources")
    Set<RepositorySource> knot$getSources();
}