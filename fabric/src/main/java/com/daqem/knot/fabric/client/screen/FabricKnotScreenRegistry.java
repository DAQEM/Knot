package com.daqem.knot.fabric.client.screen;

import com.daqem.knot.client.screen.KnotScreenRegistry;
import com.daqem.knot.client.screen.ScreenBinder;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FabricKnotScreenRegistry implements KnotScreenRegistry {

    @Override
    public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<@NotNull T>> void bind(
            Supplier<? extends MenuType<? extends @NotNull T>> menuType,
            ScreenBinder<T, U> screenBinder) {
        MenuScreens.register(menuType.get(), screenBinder::create);
    }
}