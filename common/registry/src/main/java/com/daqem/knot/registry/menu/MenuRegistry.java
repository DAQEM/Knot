package com.daqem.knot.registry.menu;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface MenuRegistry {

    /**
     * Creates and returns a new MenuType.
     */
    <T extends AbstractContainerMenu> MenuType<@NotNull T> create(MenuConstructor<T> constructor);

    /**
     * Opens a menu for a player with optional extra data (buffer).
     */
    void open(ServerPlayer player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> extraDataWriter);

    /**
     * Opens a menu for a player without extra data.
     */
    default void open(ServerPlayer player, MenuProvider provider) {
        open(player, provider, buf -> {});
    }
}