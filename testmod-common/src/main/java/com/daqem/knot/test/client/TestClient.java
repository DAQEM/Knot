package com.daqem.knot.test.client;

import com.daqem.knot.client.registry.KnotEntityRendererRegistry;
import com.daqem.knot.test.registry.TestEntityTypes;
import net.minecraft.client.renderer.entity.PigRenderer;

public class TestClient {

    public static void init() {
        KnotEntityRendererRegistry.register(TestEntityTypes.TEST_ENTITY, PigRenderer::new);
    }
}
