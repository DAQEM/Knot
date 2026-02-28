package com.daqem.knot.fabric.events.mixin;

import com.daqem.knot.events.common.entity.player.AdvancementEvent;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancements.class)
public abstract class PlayerAdvancementsMixin {

    @Shadow private ServerPlayer player;

    @Inject(
            method = "award",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void knot$onAwardAdvancement(AdvancementHolder advancement, String criterionKey, CallbackInfoReturnable<Boolean> cir) {
        AdvancementEvent.ADVANCEMENT.invoker().onAdvancement(this.player, advancement);
    }
}
