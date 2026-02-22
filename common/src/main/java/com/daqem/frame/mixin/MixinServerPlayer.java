package com.daqem.frame.mixin;

import com.daqem.frame.event.common.FrameTickEvent;
import com.daqem.frame.event.server.FrameServersideTickEvent;
import com.daqem.frame.world.entity.MovementType;
import com.daqem.frame.event.FrameItemEvent;
import com.daqem.frame.event.FrameMovementEvent;
import com.daqem.frame.event.FramePlayerEvent;
import com.daqem.frame.world.entity.player.FrameServerPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer extends Player implements FrameServerPlayer {

    @Unique
    private MovementType frame$previousMovementType = MovementType.IDLE;
    @Unique
    private double frame$totalWalkedCm = 0;
    @Unique
    private double frame$totalSprintedCm = 0;
    @Unique
    private double frame$totalSwamCm = 0;
    @Unique
    private double frame$totalCrouchedCm = 0;
    @Unique
    private double frame$totalElytraFlyCm = 0;
    @Unique
    private double frame$totalHorseRideCm = 0;

    public MixinServerPlayer(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Override
    public ServerPlayer frame$getServerPlayer() {
        return (ServerPlayer) (Object) this;
    }

    @Override
    public double frame$getTotalWalkedCm() {
        return this.frame$totalWalkedCm;
    }

    @Override
    public double frame$getTotalSprintedCm() {
        return this.frame$totalSprintedCm;
    }

    @Override
    public double frame$getTotalSwamCm() {
        return this.frame$totalSwamCm;
    }

    @Override
    public double frame$getTotalCrouchedCm() {
        return this.frame$totalCrouchedCm;
    }

    @Override
    public double frame$getTotalElytraFlyCm() {
        return this.frame$totalElytraFlyCm;
    }

    @Override
    public double frame$getTotalHorseRideCm() {
        return this.frame$totalHorseRideCm;
    }

    @Inject(method = "checkMovementStatistics", at = @At("HEAD"))
    public void onCheckMovementStatistics(double movedX, double movedY, double movedZ, CallbackInfo ci) {
        MovementType currentMovementType = MovementType.IDLE;
        int distanceInCm = 0;

        if (this.isPassenger() && this.getRootVehicle() instanceof AbstractHorse horse && horse.isSaddled()) {
            currentMovementType = MovementType.HORSE_RIDING;
            distanceInCm = Math.round((float) Math.sqrt(movedX * movedX + movedZ * movedZ) * 100.0F);
        } else if (this.isSwimming()) {
            currentMovementType = MovementType.SWIMMING;
            distanceInCm = Math.round((float) Math.sqrt(movedX * movedX + movedY * movedY + movedZ * movedZ) * 100.0F);
        } else if (this.onGround()) {
            distanceInCm = Math.round((float) Math.sqrt(movedX * movedX + movedZ * movedZ) * 100.0F);
            if (this.isSprinting()) {
                currentMovementType = MovementType.SPRINTING;
            } else if (this.isCrouching()) {
                currentMovementType = MovementType.CROUCHING;
            } else {
                currentMovementType = MovementType.WALKING;
            }
        } else if (this.isFallFlying()) {
            currentMovementType = MovementType.ELYTRA_FLYING;
            distanceInCm = Math.round((float) Math.sqrt(movedX * movedX + movedY * movedY + movedZ * movedZ) * 100.0F);
        }

        if (distanceInCm <= 0) {
            currentMovementType = MovementType.IDLE;
        }

        if (currentMovementType != this.frame$previousMovementType) {
            // Fire the STOP event for the old state
            frame$fireStopEvent(this.frame$previousMovementType, this.frame$getServerPlayer());
            // Fire the START event for the new state
            frame$fireStartEvent(currentMovementType, this.frame$getServerPlayer());
            // Update the state for the next tick
            this.frame$previousMovementType = currentMovementType;
        }

        if (distanceInCm > 0) {
            switch (currentMovementType) {
                case WALKING:
                    this.frame$totalWalkedCm += distanceInCm;
                    FrameMovementEvent.WALK.invoker().onWalk(this.frame$getServerPlayer(), this.frame$totalWalkedCm);
                    break;
                case SPRINTING:
                    this.frame$totalSprintedCm += distanceInCm;
                    FrameMovementEvent.SPRINT.invoker().onSprint(this.frame$getServerPlayer(), this.frame$totalSprintedCm);
                    break;
                case SWIMMING:
                    this.frame$totalSwamCm += distanceInCm;
                    FrameMovementEvent.SWIM.invoker().onSwim(this.frame$getServerPlayer(), this.frame$totalSwamCm);
                    break;
                case CROUCHING:
                    this.frame$totalCrouchedCm += distanceInCm;
                    FrameMovementEvent.CROUCH.invoker().onCrouch(this.frame$getServerPlayer(), this.frame$totalCrouchedCm);
                    break;
                case ELYTRA_FLYING:
                    this.frame$totalElytraFlyCm += distanceInCm;
                    FrameMovementEvent.ELYTRA_FLY.invoker().onElytraFly(this.frame$getServerPlayer(), this.frame$totalElytraFlyCm);
                    break;
                case HORSE_RIDING:
                    this.frame$totalHorseRideCm += distanceInCm;
                    FrameMovementEvent.HORSE_RIDE.invoker().onHorseRide(this.frame$getServerPlayer(), this.frame$totalHorseRideCm);
                    break;
            }
        }
    }

    @Unique
    private void frame$fireStartEvent(MovementType type, ServerPlayer player) {
        switch (type) {
            case WALKING:
                FrameMovementEvent.START_WALK.invoker().onStartWalk(player);
                break;
            case SPRINTING:
                FrameMovementEvent.START_SPRINT.invoker().onStartSprint(player);
                break;
            case SWIMMING:
                FrameMovementEvent.START_SWIM.invoker().onStartSwim(player);
                break;
            case CROUCHING:
                FrameMovementEvent.START_CROUCH.invoker().onStartCrouch(player);
                break;
            case ELYTRA_FLYING:
                FrameMovementEvent.START_ELYTRA_FLY.invoker().onStartElytraFly(player);
                break;
            case HORSE_RIDING:
                FrameMovementEvent.START_HORSE_RIDE.invoker().onStartHorseRide(player);
                break;
        }
    }

    @Unique
    private void frame$fireStopEvent(MovementType type, ServerPlayer player) {
        switch (type) {
            case WALKING:
                FrameMovementEvent.STOP_WALK.invoker().onStopWalk(player);
                break;
            case SPRINTING:
                FrameMovementEvent.STOP_SPRINT.invoker().onStopSprint(player);
                break;
            case SWIMMING:
                FrameMovementEvent.STOP_SWIM.invoker().onStopSwim(player);
                break;
            case CROUCHING:
                FrameMovementEvent.STOP_CROUCH.invoker().onStopCrouch(player);
                break;
            case ELYTRA_FLYING:
                FrameMovementEvent.STOP_ELYTRA_FLY.invoker().onStopElytraFly(player);
                break;
            case HORSE_RIDING:
                FrameMovementEvent.STOP_HORSE_RIDE.invoker().onStopHorseRide(player);
                break;
        }
    }

    @Inject(at = @At("TAIL"), method = "onEnchantmentPerformed(Lnet/minecraft/world/item/ItemStack;I)V")
    public void onEnchantmentPerformed(ItemStack itemStack, int level, CallbackInfo ci) {
        FramePlayerEvent.ENCHANT_ITEM.invoker().onEnchantItem((ServerPlayer) (Object) this, itemStack, level);
    }

    @Inject(at = @At("TAIL"), method = "restoreFrom(Lnet/minecraft/server/level/ServerPlayer;Z)V")
    public void restoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        if (oldPlayer instanceof FrameServerPlayer serverPlayer) {
            this.frame$totalWalkedCm = serverPlayer.frame$getTotalWalkedCm();
            this.frame$totalSprintedCm = serverPlayer.frame$getTotalSprintedCm();
            this.frame$totalSwamCm = serverPlayer.frame$getTotalSwamCm();
            this.frame$totalCrouchedCm = serverPlayer.frame$getTotalCrouchedCm();
            this.frame$totalElytraFlyCm = serverPlayer.frame$getTotalElytraFlyCm();
            this.frame$totalHorseRideCm = serverPlayer.frame$getTotalHorseRideCm();
        }
    }

    @Inject(method = "onItemPickup", at = @At("HEAD"))
    private void frame$onItemPickup(ItemEntity itemEntity, CallbackInfo ci) {
        FrameItemEvent.PICKUP_ITEM.invoker().onPickupItem(this, itemEntity);
    }
    
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void frame$preTick(CallbackInfo ci) {
        FrameServersideTickEvent.SERVER_PLAYER_PRE.invoker().tick(this.frame$getServerPlayer());
        FrameTickEvent.PLAYER_PRE.invoker().tick(this.frame$getServerPlayer());
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void frame$postTick(CallbackInfo ci) {
        FrameServersideTickEvent.SERVER_PLAYER_POST.invoker().tick(this.frame$getServerPlayer());
        FrameTickEvent.PLAYER_POST.invoker().tick(this.frame$getServerPlayer());
    }
}
