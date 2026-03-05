package com.daqem.knot.api.world.entity;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

public interface IAbstractArrow {

    ItemStack knot$getPickupItem();

    AbstractArrow knot$getArrow();
}
