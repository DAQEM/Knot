package com.daqem.knot.neoforge.world.entity.player;

import com.daqem.knot.api.world.entity.player.FakePlayerService;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.util.FakePlayer;

public class NeoForgeFakePlayerService implements FakePlayerService {
    @Override
    public ServerPlayer getFakePlayer(ServerLevel level, GameProfile profile) {
        return new FakePlayer(level, profile);
    }
}