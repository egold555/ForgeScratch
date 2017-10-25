package org.golde.forge.scratchforge.mainmod.guis.unused;

import org.golde.forge.scratchforge.mainmod.guis.GuiAbstractMessage;
import org.golde.forge.scratchforge.mainmod.guis.GuiMessage;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.StartupQuery;

public class GuiScreenBlockToItemError extends GuiMessage{

	StartupQuery query;
	
	public GuiScreenBlockToItemError(GuiScreen pastScreen, StartupQuery query) {
		super(pastScreen, new String[] {
			"The world state is utterly corrupted and this save is NOT loadable",
			"",
			"There is a high probability that a mod has broken the",
			"ID map and there is",
			"NOTHING FML or Forge can do to recover this save.",
			"",
			"If you changed your mods, try reverting the change"	
		});
		this.query = query;
	}
	
	
	@Override
	public void onDoneButtonClicked() {
		super.onDoneButtonClicked();
		query.finish();
	}
}
