package com.daqem.knot.fabric.registry.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public class FabricParticleProviderRegistry implements com.daqem.knot.registry.client.ParticleProviderRegistry {

    @Override
    public <T extends ParticleOptions> void register(ParticleType<@NotNull T> type, ParticleProvider<@NotNull T> provider) {
        ParticleProviderRegistry.getInstance().register(type, provider);
    }
}