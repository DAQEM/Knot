package com.daqem.knot.mixin;

import com.daqem.knot.item.creativetab.KnotCreativeTabs;
import com.daqem.knot.item.creativetab.KnotItemPropertiesExtension;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class MixinItem {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void knot$assignCreativeTab(Item.Properties properties, CallbackInfo ci) {
        // Retrieve the tab key assigned in Item.Properties via our extension
        ResourceKey<CreativeModeTab> tabKey = ((KnotItemPropertiesExtension) properties).knot$getTabKey();

        if (tabKey != null) {
            // Because our modify() method safely queues modifications for both Fabric and NeoForge,
            // we can just directly call it right here in the constructor!
            KnotCreativeTabs.modify(tabKey, populator -> populator.add((Item) (Object) this));
        }
    }
}