package com.daqem.knot.fabric.registry.client;

import com.daqem.knot.registry.client.ScreenBinder;
import com.daqem.knot.registry.client.ScreenRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FabricScreenRegistry implements ScreenRegistry {

    @Override
    public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<@NotNull T>> void bind(
            Supplier<? extends MenuType<? extends @NotNull T>> menuType,
            ScreenBinder<T, U> screenBinder) {
        MenuScreens.register(menuType.get(), screenBinder::create);
    }
}