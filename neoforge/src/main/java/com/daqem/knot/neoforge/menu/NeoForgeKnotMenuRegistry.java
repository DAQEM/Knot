package com.daqem.knot.neoforge.menu;

import com.daqem.knot.menu.KnotMenuRegistry;
import com.daqem.knot.menu.MenuConstructor;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class NeoForgeKnotMenuRegistry implements KnotMenuRegistry {

    @Override
    public <T extends AbstractContainerMenu> MenuType<@NotNull T> createType(MenuConstructor<T> constructor) {
        return IMenuTypeExtension.create(constructor::create);
    }

    @Override
    public void open(ServerPlayer player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> extraDataWriter) {
        player.openMenu(provider, extraDataWriter);
    }
}