package com.daqem.knot.fabric.world.entity.player;

import com.daqem.knot.api.world.entity.player.FakePlayerService;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class FabricFakePlayerService implements FakePlayerService {

    @Override
    public ServerPlayer getFakePlayer(ServerLevel level, GameProfile profile) {
        return FakePlayer.get(level, profile);
    }
}