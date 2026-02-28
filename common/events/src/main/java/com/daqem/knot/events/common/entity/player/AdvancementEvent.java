package com.daqem.knot.events.common.entity.player;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.world.entity.player.Player;

public interface AdvancementEvent {

    Event<Advancement> ADVANCEMENT = EventFactory.createLoop(Advancement.class);

    interface Advancement {
        void onAdvancement(Player player, AdvancementHolder advancementHolder);
    }
}
