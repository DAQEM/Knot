package com.daqem.knot.neoforge.permissions;

import com.daqem.knot.permissions.PermissionsService;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NeoForgePermissionsService implements PermissionsService {

    private static final Map<String, PermissionNode<Boolean>> NODES = new ConcurrentHashMap<>();

    private PermissionNode<Boolean> getNode(String permissionNode) {
        return NODES.computeIfAbsent(permissionNode, id -> {
            int dotIndex = id.indexOf('.');
            String namespace = dotIndex > 0 ? id.substring(0, dotIndex) : "knot";
            String path = dotIndex > 0 ? id.substring(dotIndex + 1) : id;

            return new PermissionNode<>(
                    namespace,
                    path,
                    PermissionTypes.BOOLEAN,
                    (p, uuid, context) -> false
            );
        });
    }

    @Override
    public boolean check(CommandSourceStack source, String permissionNode) {
        return check(source, permissionNode, PermissionLevel.GAMEMASTERS);
    }

    @Override
    public boolean check(CommandSourceStack source, String permissionNode, PermissionLevel fallbackLevel) {
        if (source.getEntity() instanceof ServerPlayer player) {
            return check(player, permissionNode, fallbackLevel);
        }
        return source.permissions().hasPermission(new Permission.HasCommandLevel(fallbackLevel));
    }

    @Override
    public boolean check(ServerPlayer player, String permissionNode) {
        return check(player, permissionNode, PermissionLevel.GAMEMASTERS);
    }

    @Override
    public boolean check(ServerPlayer player, String permissionNode, PermissionLevel fallbackLevel) {
        try {
            return PermissionAPI.getPermission(player, getNode(permissionNode));
        } catch (Exception e) {
            return player.permissions().hasPermission(new Permission.HasCommandLevel(fallbackLevel));
        }
    }
}