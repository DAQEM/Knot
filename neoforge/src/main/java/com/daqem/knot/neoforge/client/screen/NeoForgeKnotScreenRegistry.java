package com.daqem.knot.neoforge.client.screen;

import com.daqem.knot.KnotMod;
import com.daqem.knot.client.screen.KnotScreenRegistry;
import com.daqem.knot.client.screen.ScreenBinder;
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

@EventBusSubscriber(modid = KnotMod.MOD_ID, value = Dist.CLIENT)
public class NeoForgeKnotScreenRegistry implements KnotScreenRegistry {
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
    }
}