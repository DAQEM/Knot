package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

/**
 * Events related to the In-Game Heads Up Display (HUD) overlay.
 */
public interface ClientHudEvent {

    Event<Render> RENDER = EventFactory.createLoop(Render.class);
    Event<DebugText> DEBUG_TEXT_LEFT = EventFactory.createLoop(DebugText.class);
    Event<DebugText> DEBUG_TEXT_RIGHT = EventFactory.createLoop(DebugText.class);

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

    interface DebugText {
        /**
         * Fired when the F3 debug screen is gathering text, allowing you to add custom info.
         */
        void onGatherDebugText(List<String> textList);
    }
}