package com.daqem.knot.mixin;

import com.daqem.knot.item.creativetab.KnotItemPropertiesExtension;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.Properties.class)
public abstract class MixinItemProperties implements KnotItemPropertiesExtension {

    @Unique
    private ResourceKey<CreativeModeTab> knot$tabKey;

    @Override
    public Item.Properties knot$tab(ResourceKey<CreativeModeTab> tabKey) {
        this.knot$tabKey = tabKey;
        return (Item.Properties) (Object) this;
    }

    @Override
    public ResourceKey<CreativeModeTab> knot$getTabKey() {
        return this.knot$tabKey;
    }
}