package com.daqem.frame.mixin;

import com.daqem.frame.event.FrameEntityEvent;
import com.daqem.frame.event.EventResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Animal.class)
public abstract class MixinAnimal {

    @Unique
    private Animal frame$getAnimal() {
        return (Animal) (Object) this;
    }

    @Inject(
            method = "spawnChildFromBreeding",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/AgeableMob;setBaby(Z)V",
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void onSpawnChildFromBreeding(ServerLevel serverLevel, Animal animal, CallbackInfo ci, AgeableMob ageableMob) {
        ServerPlayer serverPlayer = frame$getAnimal().getLoveCause();
        EventResult eventResult = FrameEntityEvent.BREED_ANIMAL.invoker().onBreedAnimal(serverLevel, serverPlayer, ageableMob);
        if (eventResult.cancelsEvent()) {
            ci.cancel();
        }
    }
}
