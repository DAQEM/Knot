package com.daqem.knot.fabric;

import com.daqem.knot.Services;
import com.daqem.knot.events.EventsService;
import com.daqem.knot.fabric.events.FabricEventsService;
import com.daqem.knot.fabric.networking.FabricNetworkingService;
import com.daqem.knot.fabric.registry.FabricRegistryService;
import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.registry.RegistryService;

public class FabricServices implements Services {

    public static final FabricNetworkingService NETWORKING = new FabricNetworkingService();
    public static final FabricEventsService EVENTS = new FabricEventsService();
    public static final FabricRegistryService REGISTRY = new FabricRegistryService();

    @Override
    public NetworkingService getNetworking() {
        return NETWORKING;
    }

    @Override
    public EventsService getEvents() {
        return EVENTS;
    }

    @Override
    public RegistryService getRegistry() {
        return REGISTRY;
    }
}
