package com.daqem.knot.fabric;

import com.daqem.knot.Services;
import com.daqem.knot.fabric.networking.FabricNetworkingService;
import com.daqem.knot.fabric.permissions.FabricPermissionsService;
import com.daqem.knot.fabric.registry.FabricRegistryService;
import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.permissions.PermissionsService;
import com.daqem.knot.registry.RegistryService;

public class FabricServices implements Services {

    public static final FabricNetworkingService NETWORKING = new FabricNetworkingService();
    public static final FabricRegistryService REGISTRY = new FabricRegistryService();
    public static final FabricPermissionsService PERMISSIONS = new FabricPermissionsService();

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
}
