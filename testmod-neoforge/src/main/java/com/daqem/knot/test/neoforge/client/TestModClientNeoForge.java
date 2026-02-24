package com.daqem.knot.test.neoforge.client;

import com.daqem.knot.test.Test;
import com.daqem.knot.test.client.TestClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = Test.MOD_ID, dist = Dist.CLIENT)
public class TestModClientNeoForge {

    public TestModClientNeoForge() {
        TestClient.init();
    }
}
