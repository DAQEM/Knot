package com.daqem.knot.fabric.events.mixin.client;

import com.daqem.knot.events.client.ClientTooltipEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import org.apache.commons.lang3.mutable.MutableInt;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {

    /**
     * Wrap the positioner call. This is highly compatible with other mods.
     * We intercept the parameters, fire the Knot event to adjust them,
     * and then pass the results back into the 'original' operation chain.
     */
    @WrapOperation(
            method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/ResourceLocation;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;positionTooltip(IIIIII)Lorg/joml/Vector2ic;")
    )
    private Vector2ic knot$wrapTooltipPositioning(
            ClientTooltipPositioner positioner,
            int guiWidth, int guiHeight, int mouseX, int mouseY, int tooltipWidth, int tooltipHeight,
            Operation<Vector2ic> original
    ) {
        // Create mutable containers for the coordinates
        MutableInt targetX = new MutableInt(mouseX);
        MutableInt targetY = new MutableInt(mouseY);

        // Fire the Knot position adjustment event
        ClientTooltipEvent.ADJUST_POSITION.invoker().onAdjustTooltipPosition(
                (GuiGraphics) (Object) this,
                mouseX,
                mouseY,
                targetX,
                targetY
        );

        // Call the original method (or the next mod in the Mixin chain)
        // with the potentially modified X and Y values.
        return original.call(positioner, guiWidth, guiHeight, (int) targetX.getValue(), (int) targetY.getValue(), tooltipWidth, tooltipHeight);
    }
}