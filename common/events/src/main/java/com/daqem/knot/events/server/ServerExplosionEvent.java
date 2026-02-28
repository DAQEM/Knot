package com.daqem.knot.events.server;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import com.daqem.knot.events.EventResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import java.util.List;

public interface ServerExplosionEvent {

    Event<Pre> PRE = EventFactory.createEventResult(Pre.class);
    Event<Detonate> DETONATE = EventFactory.createLoop(Detonate.class);

    interface Pre {
        /**
         * Fired right before an explosion executes its block breaking/entity hurting logic.
         * Returning INTERRUPT will completely cancel the explosion.
         */
        EventResult onPreExplosion(Level level, Explosion explosion);
    }

    interface Detonate {
        /**
         * Fired while an explosion is resolving to provide a list of the entities it has affected.
         */
        void onDetonate(Level level, Explosion explosion, List<Entity> affectedEntities);
    }
}