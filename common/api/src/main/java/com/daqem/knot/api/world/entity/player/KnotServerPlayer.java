package com.daqem.knot.api.world.entity.player;

import net.minecraft.server.level.ServerPlayer;

public interface KnotServerPlayer extends KnotPlayer {

    ServerPlayer knot$getServerPlayer();

    double knot$getTotalWalkedCm();
    double knot$getTotalSprintedCm();
    double knot$getTotalSwamCm();
    double knot$getTotalCrouchedCm();
    double knot$getTotalElytraFlyCm();
    double knot$getTotalHorseRideCm();
}
