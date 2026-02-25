package com.daqem.knot.test.client;

import com.daqem.knot.Knot;
import com.daqem.knot.test.Test;
import com.daqem.knot.test.client.screen.BoxOfSecretsScreen;
import com.daqem.knot.test.registry.TestEntityTypes;
import com.daqem.knot.test.registry.TestMenus;
import net.minecraft.client.renderer.entity.PigRenderer;

public class TestClient {

    public static void init() {
        Knot.ENTITY_RENDERER.registerRenderer(TestEntityTypes.TEST_ENTITY, PigRenderer::new);
        Knot.SCREENS.bind(TestMenus.BOX_OF_SECRETS, BoxOfSecretsScreen::new);
    }
}
