package com.daqem.frame.world.level;

import org.jetbrains.annotations.NotNull;

public record FrameScheduledTask(long executionTime, Runnable action) implements Comparable<FrameScheduledTask> {
    @Override
    public int compareTo(@NotNull FrameScheduledTask o) {
        return Long.compare(this.executionTime, o.executionTime);
    }
}