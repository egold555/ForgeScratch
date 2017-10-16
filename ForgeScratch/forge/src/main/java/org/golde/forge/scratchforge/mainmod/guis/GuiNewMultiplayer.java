package org.golde.forge.scratchforge.mainmod.guis;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.resources.I18n;

public class GuiNewMultiplayer extends GuiMultiplayer
{

	public GuiNewMultiplayer() {
		super(null);
	}

	@Override
	public void func_146794_g() {
		//this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
		//this.buttonList.add(new GuiButton(8, this.width / 2 + 4, this.height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
		this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
	}
	
	

}
