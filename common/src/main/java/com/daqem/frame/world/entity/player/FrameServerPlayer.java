package com.daqem.frame.world.entity.player;

import net.minecraft.server.level.ServerPlayer;

public interface FrameServerPlayer extends FramePlayer {

    ServerPlayer frame$getServerPlayer();

    double frame$getTotalWalkedCm();
    double frame$getTotalSprintedCm();
    double frame$getTotalSwamCm();
    double frame$getTotalCrouchedCm();
    double frame$getTotalElytraFlyCm();
    double frame$getTotalHorseRideCm();
}
