package com.daqem.knot.test.registry;

import com.daqem.knot.Knot;
import com.daqem.knot.registry.KnotRegistry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.test.Test;
import com.daqem.knot.test.menu.BoxOfSecretsMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public interface TestMenus {
    KnotRegistry<MenuType<?>> MENUS = Test.API.register(BuiltInRegistries.MENU);

    RegistryEntry<MenuType<@NotNull BoxOfSecretsMenu>> BOX_OF_SECRETS = MENUS.register("box_of_secrets", () ->
            Knot.MENUS.createType(BoxOfSecretsMenu::new)
    );

    static void register() {
        MENUS.register();
    }
}