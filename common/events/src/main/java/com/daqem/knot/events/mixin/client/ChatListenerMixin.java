package com.daqem.knot.events.mixin.client;

import com.daqem.knot.events.EventResult;
import org.apache.commons.lang3.mutable.MutableObject;
import com.daqem.knot.events.client.ClientChatEvent;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(ChatListener.class)
public abstract class ChatListenerMixin {
    @Unique
    private ChatType.Bound knot$boundChatType;
    @Unique
    private final ThreadLocal<Component> knot$cancelNextChat = new ThreadLocal<>();
    @Unique
    private final ThreadLocal<Component> knot$cancelNextSystem = new ThreadLocal<>();

    @Inject(method = "handlePlayerChatMessage", at = @At(value = "INVOKE", target = "Ljava/time/Instant;now()Ljava/time/Instant;"))
    private void knot$captureChatType(PlayerChatMessage playerChatMessage, GameProfile gameProfile, ChatType.Bound bound, CallbackInfo ci) {
        this.knot$boundChatType = bound;
    }

    @ModifyVariable(method = "handlePlayerChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/PlayerChatMessage;signature()Lnet/minecraft/network/chat/MessageSignature;"))
    private Component knot$modifyMessage(Component value) {
        this.knot$cancelNextChat.remove();
        MutableObject<Component> mutable = new MutableObject<>(value);
        EventResult result = ClientChatEvent.RECEIVE.invoker().onReceiveChat(this.knot$boundChatType, mutable);
        this.knot$boundChatType = null;

        if (result.cancelsEvent()) {
            this.knot$cancelNextChat.set(value);
        }
        return mutable.getValue();
    }

    @Inject(
            method = "handlePlayerChatMessage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/chat/ChatListener;handleMessage(Lnet/minecraft/network/chat/MessageSignature;Ljava/util/function/BooleanSupplier;)V"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void knot$handleChatPre(PlayerChatMessage playerChatMessage, GameProfile gameProfile, ChatType.Bound bound, CallbackInfo ci, boolean onlyShowSecureChat, PlayerChatMessage filtered, Component component) {
        if (Objects.equals(this.knot$cancelNextChat.get(), component)) {
            ci.cancel();
        }
        this.knot$cancelNextChat.remove();
    }

    @ModifyVariable(method = "handleSystemMessage", at = @At("HEAD"), argsOnly = true)
    private Component knot$modifySystemMessage(Component message) {
        this.knot$cancelNextSystem.remove();
        MutableObject<Component> mutable = new MutableObject<>(message);
        EventResult result = ClientChatEvent.SYSTEM_MESSAGE.invoker().onSystemMessage(mutable);

        if (result.cancelsEvent()) {
            this.knot$cancelNextSystem.set(message);
        }
        return mutable.getValue();
    }

    @Inject(
            method = "handleSystemMessage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;hideMatchedNames()Lnet/minecraft/client/OptionInstance;"),
            cancellable = true
    )
    private void knot$cancelSystemMessage(Component component, boolean bl, CallbackInfo ci) {
        if (Objects.equals(this.knot$cancelNextSystem.get(), component)) {
            ci.cancel();
        }
        this.knot$cancelNextSystem.remove();
    }
}