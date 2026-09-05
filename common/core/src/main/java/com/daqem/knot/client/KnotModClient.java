package com.daqem.knot.client;

import com.daqem.knot.Knot;
import com.daqem.knot.KnotMod;
import com.daqem.knot.api.client.render.item.ItemOverrideConditionTypes;
import com.daqem.knot.api.client.render.item.condition.*;
import com.daqem.knot.client.render.item.ItemOverrideManager;

public class KnotModClient {

    public static final ItemOverrideManager ITEM_OVERRIDE_MANAGER = new ItemOverrideManager();

    public static void init() {
        ItemOverrideConditionTypes.register(StoredEnchantmentCondition.ID, StoredEnchantmentCondition.CODEC);
        ItemOverrideConditionTypes.register(EnchantmentCondition.ID, EnchantmentCondition.CODEC);
        ItemOverrideConditionTypes.register(CustomNameCondition.ID, CustomNameCondition.CODEC);
        ItemOverrideConditionTypes.register(DamageCondition.ID, DamageCondition.CODEC);
        ItemOverrideConditionTypes.register(StackSizeCondition.ID, StackSizeCondition.CODEC);
        ItemOverrideConditionTypes.register(DisplayContextCondition.ID, DisplayContextCondition.CODEC);
        ItemOverrideConditionTypes.register(ComponentCondition.ID, ComponentCondition.CODEC);

        Knot.RELOAD_REGISTRY.registerAssets(KnotMod.API.getId("item_overrides"), ITEM_OVERRIDE_MANAGER);
    }
}