package com.daqem.frame.world.level;

public interface ILevel {
    /**
     * Schedules a task to be run on the level thread after a specific number of ticks.
     *
     * @param delayTicks The number of ticks to wait (e.g., 20 for ~1 second).
     * @param action     The code to run.
     */
    void frame$schedule(int delayTicks, Runnable action);
}
