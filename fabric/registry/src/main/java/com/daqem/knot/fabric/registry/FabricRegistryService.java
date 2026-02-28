package com.daqem.knot.fabric.registry;

import com.daqem.knot.fabric.registry.client.*;
import com.daqem.knot.fabric.registry.creativetab.FabricCreativeTabsRegistry;
import com.daqem.knot.fabric.registry.entity.FabricEntityAttributesRegistry;
import com.daqem.knot.fabric.registry.fuel.FabricFuelRegistry;
import com.daqem.knot.fabric.registry.menu.FabricMenuRegistry;
import com.daqem.knot.fabric.registry.resource.FabricReloadRegistry;
import com.daqem.knot.fabric.registry.villager.FabricVillagerTradeRegistry;
import com.daqem.knot.registry.Registrar;
import com.daqem.knot.registry.RegistryService;
import com.daqem.knot.registry.client.*;
import com.daqem.knot.registry.creativetab.CreativeTabsRegistry;
import com.daqem.knot.registry.entity.EntityAttributesRegistry;
import com.daqem.knot.registry.fuel.FuelRegistry;
import com.daqem.knot.registry.menu.MenuRegistry;
import com.daqem.knot.registry.resource.ReloadRegistry;
import com.daqem.knot.registry.villager.VillagerTradeRegistry;

public class FabricRegistryService implements RegistryService {

    public static final Registrar REGISTRAR = new FabricRegistrar();
    public static final MenuRegistry MENU_REGISTRY = new FabricMenuRegistry();
    public static final CreativeTabsRegistry CREATIVE_TABS_REGISTRY = new FabricCreativeTabsRegistry();
    public static final EntityAttributesRegistry ENTITY_ATTRIBUTES_REGISTRY = new FabricEntityAttributesRegistry();
    public static final EntityRendererRegistry ENTITY_RENDERER_REGISTRY = new FabricEntityRendererRegistry();
    public static final BlockEntityRendererRegistry BLOCK_ENTITY_RENDERER_REGISTRY = new FabricBlockEntityRendererRegistry();
    public static final ScreenRegistry SCREEN_REGISTRY = new FabricScreenRegistry();
    public static final ReloadRegistry RELOAD_REGISTRY = new FabricReloadRegistry();
    public static final KeyMappingRegistry KEY_MAPPING_REGISTRY = new FabricKeyMappingRegistry();
    public static final ColorHandlerRegistry COLOR_HANDLER_REGISTRY = new FabricColorHandlerRegistry();
    public static final RenderTypeRegistry RENDER_TYPE_REGISTRY = new FabricRenderTypeRegistry();
    public static final EntityModelLayerRegistry ENTITY_MODEL_LAYER_REGISTRY = new FabricEntityModelLayerRegistry();
    public static final ParticleProviderRegistry PARTICLE_PROVIDER_REGISTRY = new FabricParticleProviderRegistry();
    public static final ConfigScreenRegistry CONFIG_SCREEN_REGISTRY = new FabricConfigScreenRegistry();
    public static final FuelRegistry FUEL_REGISTRY = new FabricFuelRegistry();
    public static final VillagerTradeRegistry VILLAGER_TRADE_REGISTRY = new FabricVillagerTradeRegistry();

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
    public RenderTypeRegistry getRenderTypeRegistry() {
        return RENDER_TYPE_REGISTRY;
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

    @Override
    public VillagerTradeRegistry getVillagerTradeRegistry() {
        return VILLAGER_TRADE_REGISTRY;
    }
}
