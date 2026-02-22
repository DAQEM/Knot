package com.daqem.frame.mixin.client;

import com.daqem.frame.event.client.FrameClientsideTickEvent;
import com.daqem.frame.event.common.FrameTickEvent;
import com.daqem.frame.world.entity.player.FrameClientPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends AbstractClientPlayer implements FrameClientPlayer {

    public MixinLocalPlayer(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Override
    public LocalPlayer frame$getLocalPlayer() {
        return (LocalPlayer) (Object) this;
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void frame$onTickPre(CallbackInfo ci) {
        FrameClientsideTickEvent.CLIENT_PLAYER_PRE.invoker().tick(this.frame$getLocalPlayer());
        FrameTickEvent.PLAYER_PRE.invoker().tick(this.frame$getLocalPlayer());
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void frame$onTickPost(CallbackInfo ci) {
        FrameClientsideTickEvent.CLIENT_PLAYER_POST.invoker().tick(this.frame$getLocalPlayer());
        FrameTickEvent.PLAYER_POST.invoker().tick(this.frame$getLocalPlayer());
    }
}
