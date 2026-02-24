package com.daqem.knot.mixin;

import com.daqem.knot.world.entity.IAbstractArrow;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractArrow.class)
public abstract class MixinAbstractArrow implements IAbstractArrow {

    @Shadow protected abstract ItemStack getPickupItem();

    @Override
    public ItemStack knot$getPickupItem() {
        return getPickupItem();
    }

    @Override
    public AbstractArrow knot$getArrow() {
        return (AbstractArrow) (Object) this;
    }
}
