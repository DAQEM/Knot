package com.daqem.knot.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.function.Supplier;

public interface KnotBlockEvent {

    Event<BreakBlock> BREAK_BLOCK = EventFactory.createEventResult(BreakBlock.class);
    Event<PlaceBlock> PLACE_BLOCK = EventFactory.createEventResult(PlaceBlock.class);
    Event<RightClickBlock> RIGHT_CLICK_BLOCK = EventFactory.createEventResult(RightClickBlock.class);

    Event<PlantCrop> PLANT_CROP = EventFactory.createEventResult(PlantCrop.class);
    Event<HarvestCrop> HARVEST_CROP = EventFactory.createEventResult(HarvestCrop.class);

    Event<GetDestroySpeed> GET_DESTROY_SPEED = EventFactory.createEventResult(GetDestroySpeed.class);

    Event<TillSoil> TILL_SOIL = EventFactory.createLoop(TillSoil.class);
    Event<FarmlandTrample> FARMLAND_TRAMPLE = EventFactory.createEventResult(FarmlandTrample.class);

    Event<FallingLand> FALLING_LAND = EventFactory.createLoop(FallingLand.class);
    Event<LeftClickBlock> LEFT_CLICK_BLOCK = EventFactory.createEventResult(LeftClickBlock.class);

    interface BreakBlock {
        EventResult onBreakBlock(ServerLevel level, BlockPos blockPos, BlockState blockState, ServerPlayer serverPlayer);
    }

    interface PlaceBlock {
        EventResult onPlaceBlock(Level level, BlockPos blockPos, BlockState blockState, Entity placer);
    }

    interface RightClickBlock {
        EventResult onRightClickBlock(ItemStack itemStack, Level level, Player player, InteractionHand hand, BlockState state, BlockPos blockPos);
    }

    interface PlantCrop {
        EventResult onPlantCrop(Level level, BlockPos blockPos, BlockState blockState, Player planter);
    }

    interface HarvestCrop {
        EventResult onHarvestCrop(ServerLevel level, BlockPos blockPos, BlockState blockState, ServerPlayer serverPlayer);
    }

    interface GetDestroySpeed {
        EventResult onGetDestroySpeed(Player player, BlockState blockState, BlockPos blockPos, ItemStack itemStack, MutableFloat speed);
    }

    interface TillSoil {
        void onTillSoil(Level level, BlockPos blockPos, BlockState resultingState, Player player, ItemStack hoe);
    }

    interface FarmlandTrample {
        EventResult onFarmlandTrample(Level level, BlockPos pos, BlockState state, double fallDistance, Entity entity);
    }

    interface FallingLand {
        /**
         * Fired when a gravity-affected block (sand, gravel) finishes falling and solidifies into a block.
         */
        void onFallingLand(Level level, BlockPos pos, BlockState fallState, BlockState landState, FallingBlockEntity entity);
    }

    interface LeftClickBlock {
        /**
         * Fired when a player left-clicks a block, before any breaking logic executes.
         */
        EventResult onLeftClickBlock(Player player, InteractionHand hand, BlockPos pos, Direction face);
    }
}