package com.daqem.knot.neoforge;

import com.daqem.knot.Services;
import com.daqem.knot.neoforge.networking.NeoForgeNetworkingService;
import com.daqem.knot.neoforge.registry.NeoForgeRegistryService;
import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.registry.RegistryService;

public class NeoForgeServices implements Services {

    public static final NeoForgeNetworkingService NETWORKING = new NeoForgeNetworkingService();
    public static final NeoForgeRegistryService REGISTRY = new NeoForgeRegistryService();

    @Override
    public NetworkingService getNetworking() {
        return NETWORKING;
    }

    @Override
    public RegistryService getRegistry() {
        return REGISTRY;
    }
}
