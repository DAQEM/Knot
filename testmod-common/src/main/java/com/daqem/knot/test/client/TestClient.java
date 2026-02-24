package com.daqem.knot.test.client;

import com.daqem.knot.test.Test;
import com.daqem.knot.test.registry.TestEntityTypes;
import net.minecraft.client.renderer.entity.PigRenderer;

public class TestClient {

    public static void init() {
        Test.API.registerEntityRenderer(TestEntityTypes.TEST_ENTITY, PigRenderer::new);
    }
}
