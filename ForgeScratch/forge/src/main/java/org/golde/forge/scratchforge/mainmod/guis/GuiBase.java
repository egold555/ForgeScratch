package org.golde.forge.scratchforge.mainmod.guis;

import org.golde.forge.scratchforge.mainmod.ForgeModScratchForge;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiBase extends GuiScreen{

	public final GuiScreen pastScreen;
	private final ResourceLocation logoTexture = new ResourceLocation(ForgeModScratchForge.MOD_ID.toLowerCase(), "textures/gui/logo.png");

	public GuiBase(GuiScreen pastScreen) {
		this.pastScreen = pastScreen;
	}

	

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		//Draw Logo
		this.mc.getTextureManager().bindTexture(logoTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect((this.width / 2 - 274 / 2), 30, 0, 0, 256, 49);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	
}
