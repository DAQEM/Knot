package com.daqem.knot.event.client;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import com.daqem.knot.event.EventResult;
import net.minecraft.client.Minecraft;

/**
 * Events related to raw peripheral inputs before they are passed to screens or game logic.
 */
public interface KnotClientRawInputEvent {

    Event<KeyPressed> KEY_PRESSED = EventFactory.createEventResult(KeyPressed.class);
    Event<MouseClicked> MOUSE_CLICKED_PRE = EventFactory.createEventResult(MouseClicked.class);
    Event<MouseClickedPost> MOUSE_CLICKED_POST = EventFactory.createLoop(MouseClickedPost.class);
    Event<MouseScrolled> MOUSE_SCROLLED = EventFactory.createEventResult(MouseScrolled.class);

    interface KeyPressed {
        EventResult onKeyPressed(Minecraft client, int keyCode, int scanCode, int action, int modifiers);
    }

    interface MouseClicked {
        EventResult onMouseClicked(Minecraft client, int button, int action, int modifiers);
    }

    interface MouseClickedPost {
        void onMouseClicked(Minecraft client, int button, int action, int modifiers);
    }

    interface MouseScrolled {
        EventResult onMouseScrolled(Minecraft client, double amountX, double amountY);
    }
}