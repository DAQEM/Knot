package com.daqem.knot.registry;

import com.daqem.knot.registry.client.EntityRendererRegistry;
import com.daqem.knot.registry.client.ScreenRegistry;
import com.daqem.knot.registry.creativetab.CreativeTabsRegistry;
import com.daqem.knot.registry.entity.EntityAttributesRegistry;
import com.daqem.knot.registry.menu.MenuRegistry;

public interface RegistryService {

    Registrar getRegistrar();

    MenuRegistry getMenuRegistry();

    CreativeTabsRegistry getCreativeTabsRegistry();

    EntityAttributesRegistry getEntityAttributesRegistry();

    EntityRendererRegistry getEntityRendererRegistry();

    ScreenRegistry getScreenRegistry();
}
