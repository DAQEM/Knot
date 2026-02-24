package com.daqem.knot.test;

import com.daqem.knot.Knot;
import com.daqem.knot.test.event.ServerTickEvent;
import com.daqem.knot.test.network.TestNetworking;
import com.daqem.knot.test.registry.*;

public class Test {

    public static final String MOD_ID = "knot_test";

    public static final Knot API = new Knot(MOD_ID);

    public static void init() {
        TestNetworking.init();
        ServerTickEvent.registerEvent();
        TestRegistries.init();
    }
}
