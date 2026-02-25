package com.daqem.knot.mixin;

import com.daqem.knot.event.common.KnotTickEvent;
import com.daqem.knot.event.server.KnotServersideTickEvent;
import com.daqem.knot.world.entity.MovementType;
import com.daqem.knot.event.KnotItemEvent;
import com.daqem.knot.event.KnotMovementEvent;
import com.daqem.knot.event.KnotPlayerEvent;
import com.daqem.knot.world.entity.player.KnotServerPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerLevel;
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
public abstract class MixinServerPlayer extends Player implements KnotServerPlayer {

    @Unique
    private MovementType knot$previousMovementType = MovementType.IDLE;
    @Unique
    private double knot$totalWalkedCm = 0;
    @Unique
    private double knot$totalSprintedCm = 0;
    @Unique
    private double knot$totalSwamCm = 0;
    @Unique
    private double knot$totalCrouchedCm = 0;
    @Unique
    private double knot$totalElytraFlyCm = 0;
    @Unique
    private double knot$totalHorseRideCm = 0;

    public MixinServerPlayer(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Override
    public ServerPlayer knot$getServerPlayer() {
        return (ServerPlayer) (Object) this;
    }

    @Override
    public double knot$getTotalWalkedCm() {
        return this.knot$totalWalkedCm;
    }

    @Override
    public double knot$getTotalSprintedCm() {
        return this.knot$totalSprintedCm;
    }

    @Override
    public double knot$getTotalSwamCm() {
        return this.knot$totalSwamCm;
    }

    @Override
    public double knot$getTotalCrouchedCm() {
        return this.knot$totalCrouchedCm;
    }

    @Override
    public double knot$getTotalElytraFlyCm() {
        return this.knot$totalElytraFlyCm;
    }

    @Override
    public double knot$getTotalHorseRideCm() {
        return this.knot$totalHorseRideCm;
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

        if (currentMovementType != this.knot$previousMovementType) {
            // Fire the STOP event for the old state
            knot$fireStopEvent(this.knot$previousMovementType, this.knot$getServerPlayer());
            // Fire the START event for the new state
            knot$fireStartEvent(currentMovementType, this.knot$getServerPlayer());
            // Update the state for the next tick
            this.knot$previousMovementType = currentMovementType;
        }

        if (distanceInCm > 0) {
            switch (currentMovementType) {
                case WALKING:
                    this.knot$totalWalkedCm += distanceInCm;
                    KnotMovementEvent.WALK.invoker().onWalk(this.knot$getServerPlayer(), this.knot$totalWalkedCm);
                    break;
                case SPRINTING:
                    this.knot$totalSprintedCm += distanceInCm;
                    KnotMovementEvent.SPRINT.invoker().onSprint(this.knot$getServerPlayer(), this.knot$totalSprintedCm);
                    break;
                case SWIMMING:
                    this.knot$totalSwamCm += distanceInCm;
                    KnotMovementEvent.SWIM.invoker().onSwim(this.knot$getServerPlayer(), this.knot$totalSwamCm);
                    break;
                case CROUCHING:
                    this.knot$totalCrouchedCm += distanceInCm;
                    KnotMovementEvent.CROUCH.invoker().onCrouch(this.knot$getServerPlayer(), this.knot$totalCrouchedCm);
                    break;
                case ELYTRA_FLYING:
                    this.knot$totalElytraFlyCm += distanceInCm;
                    KnotMovementEvent.ELYTRA_FLY.invoker().onElytraFly(this.knot$getServerPlayer(), this.knot$totalElytraFlyCm);
                    break;
                case HORSE_RIDING:
                    this.knot$totalHorseRideCm += distanceInCm;
                    KnotMovementEvent.HORSE_RIDE.invoker().onHorseRide(this.knot$getServerPlayer(), this.knot$totalHorseRideCm);
                    break;
            }
        }
    }

    @Unique
    private void knot$fireStartEvent(MovementType type, ServerPlayer player) {
        switch (type) {
            case WALKING:
                KnotMovementEvent.START_WALK.invoker().onStartWalk(player);
                break;
            case SPRINTING:
                KnotMovementEvent.START_SPRINT.invoker().onStartSprint(player);
                break;
            case SWIMMING:
                KnotMovementEvent.START_SWIM.invoker().onStartSwim(player);
                break;
            case CROUCHING:
                KnotMovementEvent.START_CROUCH.invoker().onStartCrouch(player);
                break;
            case ELYTRA_FLYING:
                KnotMovementEvent.START_ELYTRA_FLY.invoker().onStartElytraFly(player);
                break;
            case HORSE_RIDING:
                KnotMovementEvent.START_HORSE_RIDE.invoker().onStartHorseRide(player);
                break;
        }
    }

    @Unique
    private void knot$fireStopEvent(MovementType type, ServerPlayer player) {
        switch (type) {
            case WALKING:
                KnotMovementEvent.STOP_WALK.invoker().onStopWalk(player);
                break;
            case SPRINTING:
                KnotMovementEvent.STOP_SPRINT.invoker().onStopSprint(player);
                break;
            case SWIMMING:
                KnotMovementEvent.STOP_SWIM.invoker().onStopSwim(player);
                break;
            case CROUCHING:
                KnotMovementEvent.STOP_CROUCH.invoker().onStopCrouch(player);
                break;
            case ELYTRA_FLYING:
                KnotMovementEvent.STOP_ELYTRA_FLY.invoker().onStopElytraFly(player);
                break;
            case HORSE_RIDING:
                KnotMovementEvent.STOP_HORSE_RIDE.invoker().onStopHorseRide(player);
                break;
        }
    }

    @Inject(at = @At("TAIL"), method = "onEnchantmentPerformed(Lnet/minecraft/world/item/ItemStack;I)V")
    public void onEnchantmentPerformed(ItemStack itemStack, int level, CallbackInfo ci) {
        KnotPlayerEvent.ENCHANT_ITEM.invoker().onEnchantItem((ServerPlayer) (Object) this, itemStack, level);
    }

    @Inject(at = @At("TAIL"), method = "restoreFrom(Lnet/minecraft/server/level/ServerPlayer;Z)V")
    public void restoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        if (oldPlayer instanceof KnotServerPlayer serverPlayer) {
            this.knot$totalWalkedCm = serverPlayer.knot$getTotalWalkedCm();
            this.knot$totalSprintedCm = serverPlayer.knot$getTotalSprintedCm();
            this.knot$totalSwamCm = serverPlayer.knot$getTotalSwamCm();
            this.knot$totalCrouchedCm = serverPlayer.knot$getTotalCrouchedCm();
            this.knot$totalElytraFlyCm = serverPlayer.knot$getTotalElytraFlyCm();
            this.knot$totalHorseRideCm = serverPlayer.knot$getTotalHorseRideCm();
        }
    }

    @Inject(method = "onItemPickup", at = @At("HEAD"))
    private void knot$onItemPickup(ItemEntity itemEntity, CallbackInfo ci) {
        KnotItemEvent.PICKUP_ITEM.invoker().onPickupItem(this, itemEntity);
    }
    
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void knot$preTick(CallbackInfo ci) {
        KnotServersideTickEvent.SERVER_PLAYER_PRE.invoker().tick(this.knot$getServerPlayer());
        KnotTickEvent.PLAYER_PRE.invoker().tick(this.knot$getServerPlayer());
    }

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void knot$postTick(CallbackInfo ci) {
        KnotServersideTickEvent.SERVER_PLAYER_POST.invoker().tick(this.knot$getServerPlayer());
        KnotTickEvent.PLAYER_POST.invoker().tick(this.knot$getServerPlayer());
    }

    @Inject(method = "triggerDimensionChangeTriggers", at = @At("HEAD"))
    private void knot$onChangeDimension(ServerLevel origin, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        KnotPlayerEvent.CHANGE_DIMENSION.invoker().onChangeDimension(player, origin.dimension(), player.level().dimension());
    }
}
