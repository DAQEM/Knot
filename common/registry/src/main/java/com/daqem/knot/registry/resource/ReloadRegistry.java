package com.daqem.knot.registry.resource;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.NotNull;

/**
 * A unified registry for registering Resource and Data pack reload listeners.
 */
public interface ReloadRegistry {

    /**
     * Registers a listener for the **Server Data Pack** reload cycle.
     * <p>
     * This listener will run on the server (or logical server) whenever `/reload` is run
     * or the server starts up.
     * </p>
     *
     * @param id           The unique identifier for this listener.
     * @param listener     The listener implementation.
     * @param dependencies Optional IDs of other listeners that must run *before* this one.
     */
    void registerData(@NotNull Identifier id, @NotNull PreparableReloadListener listener, @NotNull Identifier... dependencies);

    /**
     * Registers a listener for the **Client Resource Pack** reload cycle.
     * <p>
     * This listener will run on the physical client whenever (F3+T) is pressed,
     * resource packs are changed, or the game starts up.
     * </p>
     * <p>
     * <b>Note:</b> Calling this on a Dedicated Server is safe (it will be ignored),
     * but the listener itself should ideally be client-only code.
     * </p>
     *
     * @param id           The unique identifier for this listener.
     * @param listener     The listener implementation.
     * @param dependencies Optional IDs of other listeners that must run *before* this one.
     */
    void registerAssets(@NotNull Identifier id, @NotNull PreparableReloadListener listener, @NotNull Identifier... dependencies);
}