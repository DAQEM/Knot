package com.daqem.knot.api.world.entity.player;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public interface FakePlayerService {
    ServerPlayer getFakePlayer(ServerLevel level, GameProfile profile);
}