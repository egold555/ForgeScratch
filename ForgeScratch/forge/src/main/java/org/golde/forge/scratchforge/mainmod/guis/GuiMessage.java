package org.golde.forge.scratchforge.mainmod.guis;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.gui.GuiScreen;

public class GuiMessage extends GuiAbstractMessage{

	public GuiMessage(GuiScreen pastScreen, String[] lines) {
		super(pastScreen, lines);
	}

	@Override
	public void onDoneButtonClicked() {
		FMLClientHandler.instance().showGuiScreen(pastScreen);
	}

}
