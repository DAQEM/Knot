package com.daqem.knot.permissions;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;

public interface PermissionsService {
    boolean check(CommandSourceStack source, String permissionNode);
    boolean check(CommandSourceStack source, String permissionNode, PermissionLevel fallbackLevel);
    boolean check(ServerPlayer player, String permissionNode);
    boolean check(ServerPlayer player, String permissionNode, PermissionLevel fallbackLevel);
}