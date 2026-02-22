package com.daqem.frame.test;

import com.daqem.frame.Frame;
import com.daqem.frame.test.event.ServerTickEvent;
import com.daqem.frame.test.network.TestNetworking;

public class Test {

    public static final String MOD_ID = "frame_test";

    public static final Frame API = new Frame(MOD_ID);

    public static void init() {
        TestNetworking.init();
        ServerTickEvent.registerEvent();
    }
}
