package com.daqem.knot.api.world.entity.player;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public interface FakePlayerService {

    /**
     * Gets or creates a fake player for the given level and profile.
     *
     * @param level   The server level.
     * @param profile The game profile for the fake player.
     * @return A ServerPlayer instance acting as a fake player.
     */
    ServerPlayer getFakePlayer(ServerLevel level, GameProfile profile);
}