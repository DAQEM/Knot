package com.daqem.knot.neoforge.registry;

import com.daqem.knot.neoforge.registry.client.*;
import com.daqem.knot.neoforge.registry.creativetab.NeoForgeCreativeTabsRegistry;
import com.daqem.knot.neoforge.registry.entity.NeoForgeEntityAttributesRegistry;
import com.daqem.knot.neoforge.registry.fuel.NeoForgeFuelRegistry;
import com.daqem.knot.neoforge.registry.menu.NeoForgeMenuRegistry;
import com.daqem.knot.neoforge.registry.resource.NeoForgeReloadRegistry;
import com.daqem.knot.registry.Registrar;
import com.daqem.knot.registry.RegistryService;
import com.daqem.knot.registry.client.*;
import com.daqem.knot.registry.creativetab.CreativeTabsRegistry;
import com.daqem.knot.registry.entity.EntityAttributesRegistry;
import com.daqem.knot.registry.fuel.FuelRegistry;
import com.daqem.knot.registry.menu.MenuRegistry;
import com.daqem.knot.registry.resource.ReloadRegistry;

public class NeoForgeRegistryService implements RegistryService {

    public static final Registrar REGISTRAR = new NeoForgeRegistrar();
    public static final MenuRegistry MENU_REGISTRY = new NeoForgeMenuRegistry();
    public static final CreativeTabsRegistry CREATIVE_TABS_REGISTRY = new NeoForgeCreativeTabsRegistry();
    public static final EntityAttributesRegistry ENTITY_ATTRIBUTES_REGISTRY = new NeoForgeEntityAttributesRegistry();
    public static final EntityRendererRegistry ENTITY_RENDERER_REGISTRY = new NeoForgeEntityRendererRegistry();
    public static final BlockEntityRendererRegistry BLOCK_ENTITY_RENDERER_REGISTRY = new NeoForgeBlockEntityRendererRegistry();
    public static final ScreenRegistry SCREEN_REGISTRY = new NeoForgeScreenRegistry();
    public static final ReloadRegistry RELOAD_REGISTRY = new NeoForgeReloadRegistry();
    public static final KeyMappingRegistry KEY_MAPPING_REGISTRY = new NeoForgeKeyMappingRegistry();
    public static final ColorHandlerRegistry COLOR_HANDLER_REGISTRY = new NeoForgeColorHandlerRegistry();
    public static final EntityModelLayerRegistry ENTITY_MODEL_LAYER_REGISTRY = new NeoForgeEntityModelLayerRegistry();
    public static final ParticleProviderRegistry PARTICLE_PROVIDER_REGISTRY = new NeoForgeParticleProviderRegistry();
    public static final ConfigScreenRegistry CONFIG_SCREEN_REGISTRY = new NeoForgeConfigScreenRegistry();
    public static final FuelRegistry FUEL_REGISTRY = new NeoForgeFuelRegistry();
    
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

    @Override
    public KeyMappingRegistry getKeyMappingRegistry() {
        return KEY_MAPPING_REGISTRY;
    }

    @Override
    public ColorHandlerRegistry getColorHandlerRegistry() {
        return COLOR_HANDLER_REGISTRY;
    }

    @Override
    public EntityModelLayerRegistry getEntityModelLayerRegistry() {
        return ENTITY_MODEL_LAYER_REGISTRY;
    }

    @Override
    public ParticleProviderRegistry getParticleProviderRegistry() {
        return PARTICLE_PROVIDER_REGISTRY;
    }

    @Override
    public ConfigScreenRegistry getConfigScreenRegistry() {
        return CONFIG_SCREEN_REGISTRY;
    }

    @Override
    public FuelRegistry getFuelRegistry() {
        return FUEL_REGISTRY;
    }
}
