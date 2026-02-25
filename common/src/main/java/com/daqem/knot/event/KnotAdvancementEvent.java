package com.daqem.knot.event;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public interface KnotAdvancementEvent {

    Event<Advancement> ADVANCEMENT = EventFactory.createLoop(Advancement.class);

    interface Advancement {
        void onAdvancement(Player player, AdvancementHolder advancementHolder);
    }
}
