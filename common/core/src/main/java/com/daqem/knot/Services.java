package com.daqem.knot;

import com.daqem.knot.api.world.entity.player.FakePlayerService;
import com.daqem.knot.api.world.item.ItemTransferService;
import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.permissions.PermissionsService;
import com.daqem.knot.registry.RegistryService;

public interface Services {

    NetworkingService getNetworking();

    RegistryService getRegistry();

    PermissionsService getPermissions();

    FakePlayerService getFakePlayer();

    ItemTransferService getItemTransfer();
}