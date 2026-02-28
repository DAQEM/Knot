package com.daqem.knot.registry.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ScreenBinder<T extends AbstractContainerMenu, U extends Screen & MenuAccess<@NotNull T>> {

    U create(T menu, Inventory inventory, Component title);

}