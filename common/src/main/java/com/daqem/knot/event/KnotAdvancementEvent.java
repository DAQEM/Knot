package com.daqem.knot.event;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.level.ServerPlayer;

public interface KnotAdvancementEvent {

    Event<Advancement> ADVANCEMENT = EventFactory.createLoop(Advancement.class);

    interface Advancement {
        void onAdvancement(ServerPlayer serverPlayer, AdvancementHolder advancementHolder);
    }
}
