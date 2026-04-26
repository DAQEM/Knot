package com.daqem.knot.test.block;

import com.daqem.knot.Knot;
import com.daqem.knot.test.menu.BoxOfSecretsMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BoxOfSecretsBlock extends Block {

    public BoxOfSecretsBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {

            // Define the MenuProvider on the fly
            MenuProvider containerProvider = new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return Component.literal("Box of Secrets");
                }

                @Override
                public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
                    // We use the raw BlockPos 'pos' from the useWithoutItem method.
                    return new BoxOfSecretsMenu(i, inventory, pos);
                }
            };

            // Use Knot to open the menu and tell the client about the position
            Knot.MENU_REGISTRY.open(serverPlayer, containerProvider, buf -> buf.writeBlockPos(pos));
        }
        return ItemInteractionResult.SUCCESS;
    }
}