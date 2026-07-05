package com.daqem.knot.permissions;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;

public interface PermissionsService {

    /**
     * Checks if the given CommandSourceStack has the specified permission. Defaults to level 2.
     *
     * @param source         The command source stack.
     * @param permissionNode The permission node (e.g., "knot.command.fly").
     * @return True if the source has permission, false otherwise.
     */
    boolean check(CommandSourceStack source, String permissionNode);

    /**
     * Checks if the given CommandSourceStack has the specified permission.
     *
     * @param source         The command source stack.
     * @param permissionNode The permission node (e.g., "knot.command.fly").
     * @param fallbackLevel  The OP level to fall back to if no permission manager is found (0-4).
     * @return True if the source has permission, false otherwise.
     */
    boolean check(CommandSourceStack source, String permissionNode, PermissionLevel fallbackLevel);

    /**
     * Checks if the given ServerPlayer has the specified permission. Defaults to level 2.
     *
     * @param player         The server player.
     * @param permissionNode The permission node (e.g., "knot.command.fly").
     * @return True if the player has permission, false otherwise.
     */
    boolean check(ServerPlayer player, String permissionNode);

    /**
     * Checks if the given ServerPlayer has the specified permission.
     *
     * @param player         The server player.
     * @param permissionNode The permission node (e.g., "knot.command.fly").
     * @param fallbackLevel  The OP level to fall back to if no permission manager is found (0-4).
     * @return True if the player has permission, false otherwise.
     */
    boolean check(ServerPlayer player, String permissionNode, PermissionLevel fallbackLevel);
}