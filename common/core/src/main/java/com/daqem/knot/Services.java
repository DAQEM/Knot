package com.daqem.knot;

import com.daqem.knot.events.EventsService;
import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.registry.RegistryService;

public interface Services {

    NetworkingService getNetworking();

    EventsService getEvents();

    RegistryService getRegistry();
}
