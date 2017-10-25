package org.golde.forge.scratchforge.mainmod.guis;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.FMLClientHandler;

public class GuiMessage extends GuiAbstractMessage{

	public GuiMessage(GuiScreen pastScreen, String[] lines) {
		super(pastScreen, lines);
	}

	@Override
	public void onDoneButtonClicked() {
		FMLClientHandler.instance().showGuiScreen(pastScreen);
	}

}
