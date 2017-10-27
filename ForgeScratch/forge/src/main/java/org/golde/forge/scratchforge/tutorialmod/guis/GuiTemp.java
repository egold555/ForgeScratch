package org.golde.forge.scratchforge.tutorialmod.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.demo.DemoWorldServer;

public class GuiTemp extends GuiScreen{

	/*@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(1, this.width / 2, this.height / 2, "Test"));
	}

	@Override
	protected void actionPerformed(GuiButton btn) {
		if(btn.id == 1) {
			this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
		}
	}*/
	
	@Override
	public void drawScreen(int x, int y, float p_73863_3_) {
		drawDefaultBackground();
		drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Hi... You should not see this...", this.width / 2, this.height / 2, 0xFFFFFF);
		super.drawScreen(x, y, p_73863_3_);
	}
	
	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {/*Do nothing*/}

}
