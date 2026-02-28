package com.daqem.knot.api.util;

import com.daqem.knot.api.mixin.AxeItemAccessor;
import com.daqem.knot.api.mixin.ShovelItemAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;

public interface ToolHooks {

    /**
     * Adds a new stripping interaction to the game.
     * @param input  The block to be stripped
     * @param result The resulting block
     */
    static void addStrippable(Block input, Block result) {
        HashMap<Block, Block> map = new HashMap<>(AxeItemAccessor.knot$getStrippables());
        map.put(input, result);
        AxeItemAccessor.knot$setStrippables(map);
    }

    /**
     * Adds new stripping interactions to the game.
     * @param strippables A map of blocks to be stripped and their resulting blocks
     */
    static void addStrippables(HashMap<Block, Block> strippables) {
        HashMap<Block, Block> map = new HashMap<>(AxeItemAccessor.knot$getStrippables());
        map.putAll(strippables);
        AxeItemAccessor.knot$setStrippables(map);
    }

    /**
     * Removes a stripping interaction from the game.
     * @param input  The block to be unstripped
     * @param result The resulting block to be removed
     */
    static void removeStrippable(Block input, Block result) {
        HashMap<Block, Block> map = new HashMap<>(AxeItemAccessor.knot$getStrippables());
        map.remove(input, result);
        AxeItemAccessor.knot$setStrippables(map);
    }

    /**
     * Adds a new flattening interaction to the game.
     * @param input  The block to be flattened
     * @param result The resulting block state
     */
    static void addFlattenable(Block input, BlockState result) {
        HashMap<Block, BlockState> map = new HashMap<>(ShovelItemAccessor.knot$getFlattenables());
        map.put(input, result);
        ShovelItemAccessor.knot$setFlattenables(map);
    }

    /**
     * Adds new flattening interactions to the game.
     * @param flattenables A map of blocks to be flattened and their resulting block states
     */
    static void addFlattenables(HashMap<Block, BlockState> flattenables) {
        HashMap<Block, BlockState> map = new HashMap<>(ShovelItemAccessor.knot$getFlattenables());
        map.putAll(flattenables);
        ShovelItemAccessor.knot$setFlattenables(map);
    }

    /**
     * Removes a flattening interaction from the game.
     * @param input  The block to be unflattened
     * @param result The resulting block state to be removed
     */
    static void removeFlattenable(Block input, BlockState result) {
        HashMap<Block, BlockState> map = new HashMap<>(ShovelItemAccessor.knot$getFlattenables());
        map.remove(input, result);
        ShovelItemAccessor.knot$setFlattenables(map);
    }
}