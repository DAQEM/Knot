package com.daqem.knot.event.client;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import net.minecraft.world.item.crafting.RecipeAccess;

public interface KnotClientRecipeEvent {

    Event<Update> UPDATE = EventFactory.createLoop(Update.class);

    interface Update {
        /**
         * Fired on the client when the server sends an updated recipe book.
         */
        void onRecipeUpdate(RecipeAccess recipeAccess);
    }
}