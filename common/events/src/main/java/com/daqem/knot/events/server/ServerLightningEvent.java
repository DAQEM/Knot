package com.daqem.knot.events.server;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface ServerLightningEvent {

    Event<Strike> STRIKE = EventFactory.createLoop(Strike.class);

    interface Strike {
        /**
         * Fired when a lightning bolt strikes, right after it gathers the entities in its radius.
         */
        void onStrike(LightningBolt bolt, Level level, Vec3 pos, List<Entity> toStrike);
    }
}