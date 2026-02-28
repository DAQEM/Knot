package com.daqem.knot.registry.client;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Registry interface for binding renderers to Block Entities.
 * This must be accessed strictly on the physical client.
 */
public interface BlockEntityRendererRegistry {

    /**
     * Binds a renderer to a specific Block Entity Type.
     *
     * @param type     A supplier returning the BlockEntityType (to handle safe mod initialization).
     * @param provider The provider factory for the renderer (e.g., ChestRenderer::new).
     * @param <T>      The class of the Block Entity.
     */
    <T extends BlockEntity, S extends BlockEntityRenderState> void bind(Supplier<? extends BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T, @NotNull S> provider);
}