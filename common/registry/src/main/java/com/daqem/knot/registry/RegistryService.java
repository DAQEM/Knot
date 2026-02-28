package com.daqem.knot.registry;

import com.daqem.knot.registry.client.*;
import com.daqem.knot.registry.creativetab.CreativeTabsRegistry;
import com.daqem.knot.registry.entity.EntityAttributesRegistry;
import com.daqem.knot.registry.fuel.FuelRegistry;
import com.daqem.knot.registry.menu.MenuRegistry;
import com.daqem.knot.registry.resource.ReloadRegistry;
import com.daqem.knot.registry.villager.VillagerTradeRegistry;

public interface RegistryService {

    Registrar getRegistrar();

    MenuRegistry getMenuRegistry();

    CreativeTabsRegistry getCreativeTabsRegistry();

    EntityAttributesRegistry getEntityAttributesRegistry();

    EntityRendererRegistry getEntityRendererRegistry();

    BlockEntityRendererRegistry getBlockEntityRendererRegistry();

    ScreenRegistry getScreenRegistry();

    ReloadRegistry getReloadRegistry();

    KeyMappingRegistry getKeyMappingRegistry();

    ColorHandlerRegistry getColorHandlerRegistry();

    RenderTypeRegistry getRenderTypeRegistry();

    EntityModelLayerRegistry getEntityModelLayerRegistry();

    ParticleProviderRegistry getParticleProviderRegistry();

    ConfigScreenRegistry getConfigScreenRegistry();

    FuelRegistry getFuelRegistry();

    VillagerTradeRegistry getVillagerTradeRegistry();
}
