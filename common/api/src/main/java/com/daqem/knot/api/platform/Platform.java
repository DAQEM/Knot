package com.daqem.knot.api.platform;

import java.util.ServiceLoader;

public interface Platform {

    PlatformInfo INFO = ServiceLoader.load(PlatformInfo.class).findFirst().orElseThrow();
}
