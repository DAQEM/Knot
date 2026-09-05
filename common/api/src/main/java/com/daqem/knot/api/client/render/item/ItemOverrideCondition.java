package com.daqem.knot.api.client.render.item;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ItemOverrideCondition {
    Identifier getTypeId();
    boolean test(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext);
}