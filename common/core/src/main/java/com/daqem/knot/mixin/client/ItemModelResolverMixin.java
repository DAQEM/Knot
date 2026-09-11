package com.daqem.knot.mixin.client;

import com.daqem.knot.client.KnotModClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {

    @WrapOperation(
            method = "appendItemLayers",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;")
    )
    private Object knot$overrideItemModel(ItemStack instance, DataComponentType<?> type, Operation<Object> original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) ItemOwner itemOwner, @Local(argsOnly = true) int seed, @Local(argsOnly = true) ItemDisplayContext displayContext) {
        Object result = original.call(instance, type);
        if (type == DataComponents.ITEM_MODEL) {
            LivingEntity entity = itemOwner != null ? itemOwner.asLivingEntity() : null;
            ClientLevel clientLevel = level instanceof ClientLevel cl ? cl : null;
            Identifier override = KnotModClient.ITEM_OVERRIDE_MANAGER.getOverrideModel(instance, clientLevel, entity, seed, displayContext);
            if (override != null) {
                return override;
            }
        }
        return result;
    }

    @WrapOperation(
            method = "shouldPlaySwapAnimation",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;")
    )
    private Object knot$overrideSwapAnimationModel(ItemStack instance, DataComponentType<?> type, Operation<Object> original) {
        Object result = original.call(instance, type);
        if (type == DataComponents.ITEM_MODEL) {
            Identifier override = KnotModClient.ITEM_OVERRIDE_MANAGER.getOverrideModel(instance, null, null, 0, ItemDisplayContext.NONE);
            if (override != null) {
                return override;
            }
        }
        return result;
    }

    @WrapOperation(
            method = "swapAnimationScale",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;")
    )
    private Object knot$overrideSwapAnimationScaleModel(ItemStack instance, DataComponentType<?> type, Operation<Object> original) {
        Object result = original.call(instance, type);
        if (type == DataComponents.ITEM_MODEL) {
            Identifier override = KnotModClient.ITEM_OVERRIDE_MANAGER.getOverrideModel(instance, null, null, 0, ItemDisplayContext.NONE);
            if (override != null) {
                return override;
            }
        }
        return result;
    }
}