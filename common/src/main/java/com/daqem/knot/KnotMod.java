package com.daqem.knot;

import com.daqem.knot.pack.GlobalPackPaths;

public final class KnotMod {
    public static final String MOD_ID = "knot";

    public static final Knot API = new Knot(MOD_ID);

    public static void init() {
        GlobalPackPaths.init();
    }
}