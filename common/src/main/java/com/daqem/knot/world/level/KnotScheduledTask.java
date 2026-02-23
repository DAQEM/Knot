package com.daqem.knot.world.level;

import org.jetbrains.annotations.NotNull;

public record KnotScheduledTask(long executionTime, Runnable action) implements Comparable<KnotScheduledTask> {
    @Override
    public int compareTo(@NotNull KnotScheduledTask o) {
        return Long.compare(this.executionTime, o.executionTime);
    }
}