package com.daqem.knot.event.client;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import com.daqem.knot.event.EventResult;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;

public interface KnotTooltipEvent {
    /**
     * Fired when the game is building the list of text lines for a tooltip.
     * Use this to add, remove, or modify the lines of text on an item.
     */
    Event<GatherComponents> GATHER_COMPONENTS = EventFactory.createLoop(GatherComponents.class);

    /**
     * Fired just before the tooltip box is rendered. Allows for total cancellation.
     */
    Event<BeforeRender> BEFORE_RENDER = EventFactory.createEventResult(BeforeRender.class);

    /**
     * Fired to allow shifting the tooltip's final screen coordinates.
     */
    Event<AdjustPosition> ADJUST_POSITION = EventFactory.createLoop(AdjustPosition.class);

    interface GatherComponents {
        void onGatherTooltipComponents(ItemStack stack, Item.TooltipContext context, TooltipFlag flag, List<Component> lines);
    }

    interface BeforeRender {
        EventResult onBeforeRenderTooltip(GuiGraphics graphics, List<ClientTooltipComponent> components, int x, int y);
    }

    interface AdjustPosition {
        void onAdjustTooltipPosition(GuiGraphics graphics, int mouseX, int mouseY, MutableInt targetX, MutableInt targetY);
    }
}