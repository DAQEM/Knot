package com.daqem.knot.neoforge.registry.client;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.client.ScreenBinder;
import com.daqem.knot.registry.client.ScreenRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgeScreenRegistry implements ScreenRegistry {
    private static final Map<Supplier<? extends MenuType<?>>, ScreenBinder<?, ?>> BINDINGS = new HashMap<>();

    @Override
    public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<@NotNull T>> void bind(
            Supplier<? extends MenuType<? extends @NotNull T>> menuType,
            ScreenBinder<T, U> screenBinder) {
        BINDINGS.put(menuType, screenBinder);
    }

    @SubscribeEvent
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {
        BINDINGS.forEach((type, binder) ->
                event.register((MenuType) type.get(), binder::create));
        BINDINGS.clear();
    }
}