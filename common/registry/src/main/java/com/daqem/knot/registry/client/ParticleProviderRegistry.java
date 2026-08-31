package com.daqem.knot.registry.client;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public interface ParticleProviderRegistry {

    <T extends ParticleOptions> void register(ParticleType<@NotNull T> type, ParticleProvider<@NotNull T> provider);

    <T extends ParticleOptions> void register(ParticleType<@NotNull T> type, SpriteParticleProvider<@NotNull T> provider);

    @FunctionalInterface
    interface SpriteParticleProvider<T extends ParticleOptions> {
        ParticleProvider<T> create(SpriteSet spriteSet);
    }
}