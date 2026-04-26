package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collection;

public interface ClientRecipeEvent {

    Event<Update> UPDATE = EventFactory.createLoop(Update.class);

    interface Update {
        /**
         * Fired on the client when the server sends an updated recipe book.
         */
        void onRecipeUpdate(Collection<RecipeHolder<?>> recipes);
    }
}