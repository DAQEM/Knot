package com.daqem.knot.client.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface KnotScreenRegistry {

    /**
     * Binds a MenuType to a Screen. Must be called on the client.
     */
    <T extends AbstractContainerMenu, U extends Screen & MenuAccess<@NotNull T>> void bind(
            Supplier<? extends MenuType<? extends @NotNull T>> menuType,
            ScreenBinder<T, U> screenBinder
    );
}