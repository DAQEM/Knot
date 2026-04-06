package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import com.daqem.knot.events.EventResult;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.apache.commons.lang3.mutable.MutableObject;

/**
 * Events related to the lifecycle, rendering, and management of GUI Screens.
 */
public interface ClientScreenEvent {

    Event<BeforeOpen> BEFORE_OPEN = EventFactory.createEventResult(BeforeOpen.class);
    Event<BeforeInit> BEFORE_INIT = EventFactory.createEventResult(BeforeInit.class);
    Event<AfterInit> AFTER_INIT = EventFactory.createLoop(AfterInit.class);
    Event<BeforeRender> BEFORE_RENDER = EventFactory.createEventResult(BeforeRender.class);
    Event<AfterRender> AFTER_RENDER = EventFactory.createLoop(AfterRender.class);
    Event<RenderContainerForeground> RENDER_CONTAINER_FOREGROUND = EventFactory.createLoop(RenderContainerForeground.class);

    interface BeforeOpen {
        /**
         * Fired just before a new screen is opened.
         * You can cancel this to prevent the screen from opening, or modify the wrapper to open a completely different screen.
         *
         * @param currentScreen The screen currently open (can be null).
         * @param newScreen     A mutable wrapper containing the screen that is about to open.
         * @return EventResult.INTERRUPT to cancel the opening entirely, otherwise PASS.
         */
        EventResult onBeforeOpen(Screen currentScreen, MutableObject<Screen> newScreen);
    }

    interface BeforeInit {
        /**
         * Fired before a screen builds its widgets and layout.
         */
        EventResult onBeforeInit(Screen screen);
    }

    interface AfterInit {
        /**
         * Fired after a screen finishes building its widgets.
         * Use this to inject custom buttons into vanilla screens.
         */
        void onAfterInit(Screen screen);
    }

    interface BeforeRender {
        /**
         * Fired right before the screen draws its background and widgets.
         */
        EventResult onBeforeRender(Screen screen, GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks);
    }

    interface AfterRender {
        /**
         * Fired after the screen finishes drawing.
         */
        void onAfterRender(Screen screen, GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks);
    }

    interface RenderContainerForeground {
        /**
         * Fired when a container screen (like a Chest or Inventory) draws its foreground layer (items, local text).
         * The graphics context is already translated to the top-left corner of the GUI background.
         */
        void onRenderForeground(AbstractContainerScreen<?> screen, GuiGraphicsExtractor graphics, int mouseX, int mouseY);
    }
}