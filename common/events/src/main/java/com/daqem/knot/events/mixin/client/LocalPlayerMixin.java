package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.client.ClientTickEvent;
import com.daqem.knot.events.common.TickEvent;
import com.daqem.knot.api.world.entity.player.KnotClientPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer implements KnotClientPlayer {

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Override
    public LocalPlayer knot$getLocalPlayer() {
        return (LocalPlayer) (Object) this;
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void knot$onTickPre(CallbackInfo ci) {
        ClientTickEvent.CLIENT_PLAYER_PRE.invoker().tick(this.knot$getLocalPlayer());
        TickEvent.PLAYER_PRE.invoker().tick(this.knot$getLocalPlayer());
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void knot$onTickPost(CallbackInfo ci) {
        ClientTickEvent.CLIENT_PLAYER_POST.invoker().tick(this.knot$getLocalPlayer());
        TickEvent.PLAYER_POST.invoker().tick(this.knot$getLocalPlayer());
    }
}
