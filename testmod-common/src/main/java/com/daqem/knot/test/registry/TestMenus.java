package com.daqem.knot.test.registry;

import com.daqem.knot.Knot;
import com.daqem.knot.registry.Registry;
import com.daqem.knot.registry.RegistryEntry;
import com.daqem.knot.test.Test;
import com.daqem.knot.test.menu.BoxOfSecretsMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public interface TestMenus {

    Registry<MenuType<?>> MENUS = Knot.REGISTRAR.createRegistry(BuiltInRegistries.MENU, Test.MOD_ID);

    RegistryEntry<MenuType<@NotNull BoxOfSecretsMenu>> BOX_OF_SECRETS = MENUS.register("box_of_secrets", () ->
            Knot.MENU_REGISTRY.create(BoxOfSecretsMenu::new)
    );

    static void register() {
        MENUS.register();
    }
}