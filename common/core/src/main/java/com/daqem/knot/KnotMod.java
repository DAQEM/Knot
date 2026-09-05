package com.daqem.knot;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.pack.GlobalPackPaths;

public final class KnotMod {

    public static final String MOD_ID = Constants.MOD_ID;
    public static final Knot API = new Knot(MOD_ID);

    public static void init() {
        GlobalPackPaths.init();
    }
}