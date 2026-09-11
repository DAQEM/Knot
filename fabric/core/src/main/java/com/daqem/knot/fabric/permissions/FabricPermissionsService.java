package com.daqem.knot.fabric.permissions;

import com.daqem.knot.permissions.PermissionsService;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;

public class FabricPermissionsService implements PermissionsService {
    private static final boolean PERMISSIONS_API_LOADED = FabricLoader.getInstance().isModLoaded("fabric-permissions-api-v0");

    @Override
    public boolean check(CommandSourceStack source, String permissionNode) {
        return check(source, permissionNode, PermissionLevel.GAMEMASTERS);
    }

    @Override
    public boolean check(CommandSourceStack source, String permissionNode, PermissionLevel fallbackLevel) {
        if (PERMISSIONS_API_LOADED) {
            try {
                return me.lucko.fabric.api.permissions.v0.Permissions.check(source, permissionNode, fallbackLevel);
            } catch (Exception t) {
                return source.permissions().hasPermission(new Permission.HasCommandLevel(fallbackLevel));
            }
        }
        return source.permissions().hasPermission(new Permission.HasCommandLevel(fallbackLevel));
    }

    @Override
    public boolean check(ServerPlayer player, String permissionNode) {
        return check(player, permissionNode, PermissionLevel.GAMEMASTERS);
    }

    @Override
    public boolean check(ServerPlayer player, String permissionNode, PermissionLevel fallbackLevel) {
        if (PERMISSIONS_API_LOADED) {
            try {
                return me.lucko.fabric.api.permissions.v0.Permissions.check(player, permissionNode, fallbackLevel);
            } catch (Exception t) {
                return player.permissions().hasPermission(new Permission.HasCommandLevel(fallbackLevel));
            }
        }
        return player.permissions().hasPermission(new Permission.HasCommandLevel(fallbackLevel));
    }
}