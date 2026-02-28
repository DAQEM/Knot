package com.daqem.knot.registry;

import com.daqem.knot.registry.client.BlockEntityRendererRegistry;
import com.daqem.knot.registry.client.EntityRendererRegistry;
import com.daqem.knot.registry.client.ScreenRegistry;
import com.daqem.knot.registry.creativetab.CreativeTabsRegistry;
import com.daqem.knot.registry.entity.EntityAttributesRegistry;
import com.daqem.knot.registry.menu.MenuRegistry;
import com.daqem.knot.registry.resource.ReloadRegistry;

public interface RegistryService {

    Registrar getRegistrar();

    MenuRegistry getMenuRegistry();

    CreativeTabsRegistry getCreativeTabsRegistry();

    EntityAttributesRegistry getEntityAttributesRegistry();

    EntityRendererRegistry getEntityRendererRegistry();

    BlockEntityRendererRegistry getBlockEntityRendererRegistry();

    ScreenRegistry getScreenRegistry();

    ReloadRegistry getReloadRegistry();
}
