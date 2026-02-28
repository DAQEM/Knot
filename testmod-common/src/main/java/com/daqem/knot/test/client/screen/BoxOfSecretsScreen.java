package com.daqem.knot.test.client.screen;

import com.daqem.knot.test.menu.BoxOfSecretsMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BoxOfSecretsScreen extends AbstractContainerScreen<@NotNull BoxOfSecretsMenu> {

    public BoxOfSecretsScreen(BoxOfSecretsMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 114 + 6 * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        graphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF000000);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        this.renderTooltip(graphics, mouseX, mouseY);

        String coordText = "Opened at: " + menu.getPos().toShortString();
        graphics.drawString(this.font, coordText, leftPos + 8, topPos + 6, 0x404040, false);
    }
}