package com.beetrootmonkey.sfm.blocks.trough;

import java.awt.Color;

import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class TroughContainerGui extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 137;
    
    public TroughTE tileEntity;
    public TroughContainer container;

    private static final ResourceLocation background = new ResourceLocation(Main.MOD_ID, "textures/gui/trough.png");

    public TroughContainerGui(TroughTE tileEntity, TroughContainer container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
        this.tileEntity = tileEntity;
        this.container = container;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        super.renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(tileEntity.getDisplayName().getUnformattedText(), 8, 6, Color.darkGray.getRGB());
		fontRenderer.drawString(container.getPlayerInventory().getDisplayName().getUnformattedText(), 8, 39, Color.darkGray.getRGB());
	}
}
