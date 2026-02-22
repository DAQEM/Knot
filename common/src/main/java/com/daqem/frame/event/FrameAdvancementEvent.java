package com.daqem.frame.event;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.level.ServerPlayer;

public interface FrameAdvancementEvent {

    Event<Advancement> ADVANCEMENT = EventFactory.createLoop(Advancement.class);

    interface Advancement {
        void onAdvancement(ServerPlayer serverPlayer, AdvancementHolder advancementHolder);
    }
}
