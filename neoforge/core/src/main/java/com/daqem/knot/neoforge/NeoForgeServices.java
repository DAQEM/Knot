package com.daqem.knot.neoforge;

import com.daqem.knot.Services;
import com.daqem.knot.api.world.entity.player.FakePlayerService;
import com.daqem.knot.api.world.item.ItemTransferService;
import com.daqem.knot.neoforge.networking.NeoForgeNetworkingService;
import com.daqem.knot.neoforge.permissions.NeoForgePermissionsService;
import com.daqem.knot.neoforge.registry.NeoForgeRegistryService;
import com.daqem.knot.neoforge.world.entity.player.NeoForgeFakePlayerService;
import com.daqem.knot.neoforge.world.item.NeoForgeItemTransferService;
import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.permissions.PermissionsService;
import com.daqem.knot.registry.RegistryService;

public class NeoForgeServices implements Services {

    public static final NeoForgeNetworkingService NETWORKING = new NeoForgeNetworkingService();
    public static final NeoForgeRegistryService REGISTRY = new NeoForgeRegistryService();
    public static final NeoForgePermissionsService PERMISSIONS = new NeoForgePermissionsService();
    public static final NeoForgeFakePlayerService FAKE_PLAYER = new NeoForgeFakePlayerService();
    public static final NeoForgeItemTransferService ITEM_TRANSFER = new NeoForgeItemTransferService();

    @Override
    public NetworkingService getNetworking() {
        return NETWORKING;
    }

    @Override
    public RegistryService getRegistry() {
        return REGISTRY;
    }

    @Override
    public PermissionsService getPermissions() {
        return PERMISSIONS;
    }

    @Override
    public FakePlayerService getFakePlayer() {
        return FAKE_PLAYER;
    }

    @Override
    public ItemTransferService getItemTransfer() {
        return ITEM_TRANSFER;
    }
}
