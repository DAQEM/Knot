package com.daqem.knot.neoforge.registry.menu;

import com.daqem.knot.registry.menu.MenuConstructor;
import com.daqem.knot.registry.menu.MenuRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class NeoForgeMenuRegistry implements MenuRegistry {

    @Override
    public <T extends AbstractContainerMenu> MenuType<@NotNull T> create(MenuConstructor<T> constructor) {
        return IMenuTypeExtension.create(constructor::create);
    }

    @Override
    public void open(ServerPlayer player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> extraDataWriter) {
        player.openMenu(provider, extraDataWriter);
    }
}