package com.daqem.knot.mixin.client;

import com.daqem.knot.event.EventResult;
import com.daqem.knot.event.client.KnotClientChatEvent;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener extends ClientCommonPacketListenerImpl {

    protected MixinClientPacketListener(net.minecraft.client.Minecraft minecraft, net.minecraft.network.Connection connection, net.minecraft.client.multiplayer.CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }

    @Unique
    private boolean knot$cancelNextChat = false;

    @ModifyVariable(method = "sendChat(Ljava/lang/String;)V", at = @At("HEAD"), argsOnly = true)
    private String knot$modifySendChat(String message) {
        MutableObject<String> mutable = new MutableObject<>(message);
        EventResult result = KnotClientChatEvent.SEND.invoker().onSendChat(mutable);
        if (result.cancelsEvent()) {
            this.knot$cancelNextChat = true;
        }
        return mutable.get();
    }

    @Inject(method = "sendChat(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void knot$cancelSendChat(String message, CallbackInfo ci) {
        if (this.knot$cancelNextChat) {
            this.knot$cancelNextChat = false;
            ci.cancel();
        }
    }
}