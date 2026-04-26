package com.daqem.knot.events.mixin.server;

import com.daqem.knot.events.server.ServerExplosionEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {

    @Inject(
            method = "explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;ZLnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/level/Explosion;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Explosion;explode()V"
            ),
            cancellable = true
    )
    public void knot$explode(Entity source, DamageSource damageSource, ExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius, boolean fire, Level.ExplosionInteraction explosionInteraction, boolean spawnParticles, ParticleOptions smallExplosionParticles, ParticleOptions largeExplosionParticles, Holder<SoundEvent> explosionSound, CallbackInfoReturnable<Explosion> cir, @Local(name = "explosion") Explosion explosion) {
        if ((Level) (Object) this instanceof ServerLevel) {
            if (ServerExplosionEvent.PRE.invoker().onPreExplosion((ServerLevel) (Object) this, explosion).cancelsEvent()) {
                cir.cancel();
            }
        }
    }
}
