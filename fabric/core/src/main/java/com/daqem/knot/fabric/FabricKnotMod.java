package com.daqem.knot.fabric;

import com.daqem.knot.KnotMod;
import com.daqem.knot.fabric.events.FabricEventHooks;
import com.daqem.knot.fabric.registry.recipe.FabricKnotComponentsIngredient;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;

public class FabricKnotMod implements ModInitializer {

    @Override
    public void onInitialize() {
        KnotMod.init();
        FabricEventHooks.register();
        CustomIngredientSerializer.register(FabricKnotComponentsIngredient.Serializer.INSTANCE);
    }
}
