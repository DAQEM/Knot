package com.daqem.knot.platform;

/**
 * Represents the physical distribution/executable the game is currently running in.
 * <p>
 * <b>DO NOT</b> use this to check for the logical server/client threads during gameplay
 * (e.g. do not use this to check if you are rendering or ticking).
 * For game logic, use {@code level.isClientSide()} instead.
 */
public enum KnotEnvironment {
    /**
     * The physical client executable.
     * This includes BOTH the client render thread and the Integrated Server thread.
     */
    PHYSICAL_CLIENT,

    /**
     * The physical dedicated server executable.
     * This has no client code (no rendering, no screens, no audio) present whatsoever.
     */
    DEDICATED_SERVER
}