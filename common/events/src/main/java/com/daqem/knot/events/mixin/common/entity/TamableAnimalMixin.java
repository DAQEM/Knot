package com.daqem.knot.events.mixin.common.entity;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.entity.EntityEvent;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TamableAnimal.class)
public abstract class TamableAnimalMixin {

    @Inject(method = "tame", at = @At("HEAD"), cancellable = true)
    private void knot$onTame(Player player, CallbackInfo ci) {
        EventResult result = EntityEvent.TAME_ANIMAL.invoker().onTameAnimal((TamableAnimal) (Object) this, player);
        if (result.cancelsEvent()) {
            ci.cancel();
        }
    }
}