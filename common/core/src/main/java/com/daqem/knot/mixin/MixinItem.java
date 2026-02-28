package com.daqem.knot.mixin;

import com.daqem.knot.Knot;
import com.daqem.knot.registry.creativetab.ItemPropertiesExtension;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This mixin cannot go in the registry package because it needs the CreativeTabRegistry.
 */
@Mixin(Item.class)
public abstract class MixinItem {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void knot$assignCreativeTab(Item.Properties properties, CallbackInfo ci) {
        ResourceKey<CreativeModeTab> tabKey = ((ItemPropertiesExtension) properties).knot$getTabKey();
        if (tabKey != null) {
            Knot.CREATIVE_TABS_REGISTRY.modify(tabKey, populator -> populator.add((Item) (Object) this));
        }
    }
}