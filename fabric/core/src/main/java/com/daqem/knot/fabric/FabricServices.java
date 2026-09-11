package com.daqem.knot.fabric;

import com.daqem.knot.Services;
import com.daqem.knot.api.world.entity.player.FakePlayerService;
import com.daqem.knot.api.world.item.ItemTransferService;
import com.daqem.knot.fabric.networking.FabricNetworkingService;
import com.daqem.knot.fabric.permissions.FabricPermissionsService;
import com.daqem.knot.fabric.registry.FabricRegistryService;
import com.daqem.knot.fabric.world.entity.player.FabricFakePlayerService;
import com.daqem.knot.fabric.world.item.FabricItemTransferService;
import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.permissions.PermissionsService;
import com.daqem.knot.registry.RegistryService;

public class FabricServices implements Services {

    public static final FabricNetworkingService NETWORKING = new FabricNetworkingService();
    public static final FabricRegistryService REGISTRY = new FabricRegistryService();
    public static final FabricPermissionsService PERMISSIONS = new FabricPermissionsService();
    public static final FabricFakePlayerService FAKE_PLAYER = new FabricFakePlayerService();
    public static final FabricItemTransferService ITEM_TRANSFER = new FabricItemTransferService();

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
