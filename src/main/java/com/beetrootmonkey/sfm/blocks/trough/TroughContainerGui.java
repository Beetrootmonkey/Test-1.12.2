package com.beetrootmonkey.sfm.blocks.trough;

import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class TroughContainerGui extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 137;

    private static final ResourceLocation background = new ResourceLocation(Main.MOD_ID, "textures/gui/trough.png");

    public TroughContainerGui(TroughTE tileEntity, TroughContainer container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        super.renderHoveredToolTip(mouseX, mouseY);
    }
}
