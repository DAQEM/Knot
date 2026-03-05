package com.daqem.knot.test.client;

import com.daqem.knot.Knot;
import com.daqem.knot.test.Test;
import com.daqem.knot.test.client.screen.BoxOfSecretsScreen;
import com.daqem.knot.test.registry.TestEntityTypes;
import com.daqem.knot.test.registry.TestMenus;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;

import java.util.function.Supplier;

public class TestClient {

    public static void init() {
        Knot.ENTITY_RENDERER_REGISTRY.register(TestEntityTypes.TEST_ENTITY, PigRenderer::new);
        Knot.SCREEN_REGISTRY.bind(TestMenus.BOX_OF_SECRETS, BoxOfSecretsScreen::new);
    }
}
