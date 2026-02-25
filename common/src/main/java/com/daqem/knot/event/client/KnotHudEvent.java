package com.daqem.knot.event.client;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Events related to the In-Game Heads Up Display (HUD) overlay.
 */
public interface KnotHudEvent {

    Event<Render> RENDER = EventFactory.createLoop(Render.class);

    interface Render {
        /**
         * Fired immediately after the vanilla HUD finishes rendering.
         * Perfect for drawing custom bars, text, or overlay icons on the screen.
         *
         * @param graphics     The graphics context used for drawing.
         * @param deltaTracker Tracker for partial ticks and frame time.
         */
        void onRenderHud(GuiGraphics graphics, DeltaTracker deltaTracker);
    }
}