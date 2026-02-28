package com.daqem.knot.fabric.registry;

import com.daqem.knot.fabric.registry.client.FabricBlockEntityRendererRegistry;
import com.daqem.knot.fabric.registry.client.FabricEntityRendererRegistry;
import com.daqem.knot.fabric.registry.client.FabricScreenRegistry;
import com.daqem.knot.fabric.registry.creativetab.FabricCreativeTabsRegistry;
import com.daqem.knot.fabric.registry.entity.FabricEntityAttributesRegistry;
import com.daqem.knot.fabric.registry.menu.FabricMenuRegistry;
import com.daqem.knot.fabric.registry.resource.FabricReloadRegistry;
import com.daqem.knot.registry.Registrar;
import com.daqem.knot.registry.RegistryService;
import com.daqem.knot.registry.client.BlockEntityRendererRegistry;
import com.daqem.knot.registry.client.EntityRendererRegistry;
import com.daqem.knot.registry.client.ScreenRegistry;
import com.daqem.knot.registry.creativetab.CreativeTabsRegistry;
import com.daqem.knot.registry.entity.EntityAttributesRegistry;
import com.daqem.knot.registry.menu.MenuRegistry;
import com.daqem.knot.registry.resource.ReloadRegistry;

public class FabricRegistryService implements RegistryService {

    public static final Registrar REGISTRAR = new FabricRegistrar();
    public static final MenuRegistry MENU_REGISTRY = new FabricMenuRegistry();
    public static final CreativeTabsRegistry CREATIVE_TABS_REGISTRY = new FabricCreativeTabsRegistry();
    public static final EntityAttributesRegistry ENTITY_ATTRIBUTES_REGISTRY = new FabricEntityAttributesRegistry();
    public static final EntityRendererRegistry ENTITY_RENDERER_REGISTRY = new FabricEntityRendererRegistry();
    public static final BlockEntityRendererRegistry BLOCK_ENTITY_RENDERER_REGISTRY = new FabricBlockEntityRendererRegistry();
    public static final ScreenRegistry SCREEN_REGISTRY = new FabricScreenRegistry();
    public static final ReloadRegistry RELOAD_REGISTRY = new FabricReloadRegistry();

    @Override
    public Registrar getRegistrar() {
        return REGISTRAR;
    }

    @Override
    public MenuRegistry getMenuRegistry() {
        return MENU_REGISTRY;
    }

    @Override
    public CreativeTabsRegistry getCreativeTabsRegistry() {
        return CREATIVE_TABS_REGISTRY;
    }

    @Override
    public EntityAttributesRegistry getEntityAttributesRegistry() {
        return ENTITY_ATTRIBUTES_REGISTRY;
    }

    @Override
    public EntityRendererRegistry getEntityRendererRegistry() {
        return ENTITY_RENDERER_REGISTRY;
    }

    @Override
    public BlockEntityRendererRegistry getBlockEntityRendererRegistry() {
        return BLOCK_ENTITY_RENDERER_REGISTRY;
    }

    @Override
    public ScreenRegistry getScreenRegistry() {
        return SCREEN_REGISTRY;
    }

    @Override
    public ReloadRegistry getReloadRegistry() {
        return RELOAD_REGISTRY;
    }
}
