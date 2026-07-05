package com.daqem.knot;

import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.permissions.PermissionsService;
import com.daqem.knot.registry.RegistryService;

public interface Services {

    NetworkingService getNetworking();

    RegistryService getRegistry();

    PermissionsService getPermissions();
}
