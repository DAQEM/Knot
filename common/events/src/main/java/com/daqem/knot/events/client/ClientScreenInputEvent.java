package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import com.daqem.knot.events.EventResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

/**
 * Events related to mouse and keyboard inputs while a GUI Screen is open.
 */
public interface ClientScreenInputEvent {

    Event<MouseScrolled> MOUSE_SCROLLED_PRE = EventFactory.createEventResult(MouseScrolled.class);
    Event<MouseScrolledPost> MOUSE_SCROLLED_POST = EventFactory.createLoop(MouseScrolledPost.class);

    Event<MouseClicked> MOUSE_CLICKED_PRE = EventFactory.createEventResult(MouseClicked.class);
    Event<MouseClickedPost> MOUSE_CLICKED_POST = EventFactory.createLoop(MouseClickedPost.class);

    Event<MouseReleased> MOUSE_RELEASED_PRE = EventFactory.createEventResult(MouseReleased.class);
    Event<MouseReleasedPost> MOUSE_RELEASED_POST = EventFactory.createLoop(MouseReleasedPost.class);

    Event<MouseDragged> MOUSE_DRAGGED_PRE = EventFactory.createEventResult(MouseDragged.class);
    Event<MouseDraggedPost> MOUSE_DRAGGED_POST = EventFactory.createLoop(MouseDraggedPost.class);

    Event<KeyPressed> KEY_PRESSED_PRE = EventFactory.createEventResult(KeyPressed.class);
    Event<KeyPressedPost> KEY_PRESSED_POST = EventFactory.createLoop(KeyPressedPost.class);

    Event<KeyReleased> KEY_RELEASED_PRE = EventFactory.createEventResult(KeyReleased.class);
    Event<KeyReleasedPost> KEY_RELEASED_POST = EventFactory.createLoop(KeyReleasedPost.class);

    Event<CharTyped> CHAR_TYPED_PRE = EventFactory.createEventResult(CharTyped.class);
    Event<CharTypedPost> CHAR_TYPED_POST = EventFactory.createLoop(CharTypedPost.class);

    interface MouseScrolled { EventResult onMouseScrolled(Minecraft client, Screen screen, double mouseX, double mouseY, double scrollX, double scrollY); }
    interface MouseScrolledPost { void onMouseScrolled(Minecraft client, Screen screen, double mouseX, double mouseY, double scrollX, double scrollY); }

    interface MouseClicked { EventResult onMouseClicked(Minecraft client, Screen screen, double mouseX, double mouseY, int button); }
    interface MouseClickedPost { void onMouseClicked(Minecraft client, Screen screen, double mouseX, double mouseY, int button); }

    interface MouseReleased { EventResult onMouseReleased(Minecraft client, Screen screen, double mouseX, double mouseY, int button); }
    interface MouseReleasedPost { void onMouseReleased(Minecraft client, Screen screen, double mouseX, double mouseY, int button); }

    interface MouseDragged { EventResult onMouseDragged(Minecraft client, Screen screen, double mouseX, double mouseY, int button, double dragX, double dragY); }
    interface MouseDraggedPost { void onMouseDragged(Minecraft client, Screen screen, double mouseX, double mouseY, int button, double dragX, double dragY); }

    interface KeyPressed { EventResult onKeyPressed(Minecraft client, Screen screen, int keyCode, int scanCode, int modifiers); }
    interface KeyPressedPost { void onKeyPressed(Minecraft client, Screen screen, int keyCode, int scanCode, int modifiers); }

    interface KeyReleased { EventResult onKeyReleased(Minecraft client, Screen screen, int keyCode, int scanCode, int modifiers); }
    interface KeyReleasedPost { void onKeyReleased(Minecraft client, Screen screen, int keyCode, int scanCode, int modifiers); }

    interface CharTyped { EventResult onCharTyped(Minecraft client, Screen screen, char codePoint); }
    interface CharTypedPost { void onCharTyped(Minecraft client, Screen screen, char codePoint); }
}