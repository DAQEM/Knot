package com.daqem.knot.events.mixin.common.entity.player;

import com.daqem.knot.api.world.item.IAbstractCookingRecipe;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Shadow
    @Final
    private Object2IntOpenHashMap<ResourceLocation> recipesUsed;

    @Inject(at = @At("HEAD"), method = "awardUsedRecipesAndPopExperience")
    private void awardUsedRecipesAndPopExperience(ServerPlayer serverPlayer, CallbackInfo ci) {
        ServerLevel serverLevel = serverPlayer.serverLevel();
        this.recipesUsed.forEach((recipeId, recipeCount) -> serverLevel.getRecipeManager().byKey(recipeId).ifPresent((recipe) -> {
            if (recipe.value() instanceof IAbstractCookingRecipe cookingRecipe) {
                for (int i = 0; i < recipeCount; i++) {
                    PlayerEvent.SMELT_ITEM.invoker().onSmeltItem(serverPlayer, recipe.value(), cookingRecipe.knot$getResult(),
                            ((AbstractFurnaceBlockEntity) (Object) this).getBlockPos(), serverLevel);
                }
            }
        }));
    }
}
