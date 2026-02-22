package com.daqem.frame.world.entity;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;

public interface IAbstractArrow {

    ItemStack frame$getPickupItem();

    AbstractArrow frame$getArrow();
}
