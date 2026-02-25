package com.daqem.knot.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface KnotLightningEvent {

    Event<Strike> STRIKE = EventFactory.createLoop(Strike.class);

    interface Strike {
        /**
         * Fired when a lightning bolt strikes, right after it gathers the entities in its radius.
         */
        void onStrike(LightningBolt bolt, Level level, Vec3 pos, List<Entity> toStrike);
    }
}