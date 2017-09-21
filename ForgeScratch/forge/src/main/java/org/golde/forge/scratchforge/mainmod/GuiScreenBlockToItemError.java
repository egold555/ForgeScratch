package org.golde.forge.scratchforge.mainmod;

import cpw.mods.fml.common.StartupQuery;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenBlockToItemError extends GuiScreenDummy{

	public GuiScreenBlockToItemError(GuiScreen pastScreen) {
		super(pastScreen, new String[] {
			"The world state is utterly corrupted and this save is NOT loadable",
			"",
			"There is a high probability that a mod has broken the",
			"ID map and there is",
			"NOTHING FML or Forge can do to recover this save.",
			"",
			"If you changed your mods, try reverting the change"	
		});
	}
	
	
	@Override
	public void onDoneButtonClicked() {
		sendBackToPastGuiScreen();
	}
}
