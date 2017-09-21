package org.golde.forge.scratchforge.mainmod;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.StartupQuery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

/*
 * Based off of forge's GuiNotification class
 */
public abstract class GuiScreenDummy extends GuiScreen{

	private final GuiScreen pastScreen;
	private final String[] lines;
	private final ResourceLocation logoTexture = new ResourceLocation(ForgeModScratchForge.MOD_ID.toLowerCase(), "textures/gui/logo.png");

	public GuiScreenDummy(GuiScreen pastScreen, String lines[]) {
		this.pastScreen = pastScreen;
		this.lines = lines;
	}

	@Override
	public final void initGui() {
		buttonList.add(new GuiButton(0, width / 2 - 100, height - 38, I18n.format("gui.done", new Object[0])));
	}

	protected final void actionPerformed(GuiButton button)
	{
		if(button.enabled && button.id == 0) {
			onDoneButtonClicked();
		}
	}
	
	public abstract void onDoneButtonClicked();
	
	public final void sendBackToPastGuiScreen() {
		Minecraft.getMinecraft().displayGuiScreen(pastScreen);
	}

	public final void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		
		
		//Draw Logo
		this.mc.getTextureManager().bindTexture(logoTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect((this.width / 2 - 274 / 2), 30, 0, 0, 256, 49);
		
		
		//Draw Text
		int spaceAvailable = height - 38 - 20;
		int spaceRequired = Math.min(spaceAvailable, 10 + 10 * lines.length);

		int offset = 10 + (spaceAvailable - spaceRequired) / 2;
		for (String line : lines)
		{
			if (offset >= spaceAvailable)
			{
				drawCenteredString(fontRendererObj, "...", width / 2, offset, 16777215);
				break;
			}
			if (!line.isEmpty()) {
				drawCenteredString(fontRendererObj, line, width / 2, offset, 16777215);
			}
			offset += 10;
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
