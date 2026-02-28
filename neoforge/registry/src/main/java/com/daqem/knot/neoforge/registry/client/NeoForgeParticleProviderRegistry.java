package com.daqem.knot.neoforge.registry.client;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.client.ParticleProviderRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeParticleProviderRegistry implements ParticleProviderRegistry {

    private static final List<Registration<?>> REGISTRATIONS = new ArrayList<>();

    @Override
    public <T extends ParticleOptions> void register(ParticleType<@NotNull T> type, ParticleProvider<@NotNull T> provider) {
        REGISTRATIONS.add(new Registration<>(type, provider));
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        for (Registration<?> reg : REGISTRATIONS) {
            registerParticle(event, reg);
        }
    }

    private static <T extends ParticleOptions> void registerParticle(RegisterParticleProvidersEvent event, Registration<T> reg) {
        event.registerSpecial(reg.type(), reg.provider());
    }

    private record Registration<T extends ParticleOptions>(
            ParticleType<@NotNull T> type,
            ParticleProvider<@NotNull T> provider
    ) {
    }
}