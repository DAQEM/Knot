package com.daqem.knot.world.entity;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;

public interface IAbstractArrow {

    ItemStack knot$getPickupItem();

    AbstractArrow knot$getArrow();
}
