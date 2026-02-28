package com.daqem.knot.registry.client;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public interface ParticleProviderRegistry {

    <T extends ParticleOptions> void register(ParticleType<@NotNull T> type, ParticleProvider<@NotNull T> provider);

}