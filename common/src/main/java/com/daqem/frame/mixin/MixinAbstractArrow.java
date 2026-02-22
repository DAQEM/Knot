package com.daqem.frame.mixin;

import com.daqem.frame.world.entity.IAbstractArrow;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractArrow.class)
public abstract class MixinAbstractArrow implements IAbstractArrow {

    @Shadow protected abstract ItemStack getPickupItem();

    @Override
    public ItemStack frame$getPickupItem() {
        return getPickupItem();
    }

    @Override
    public AbstractArrow frame$getArrow() {
        return (AbstractArrow) (Object) this;
    }
}
