package org.golde.forge.scratchforge.mainmod.guis;

import org.golde.forge.scratchforge.mainmod.Language;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

//Better lan describing that both players need to have the mod installed!
public class GuiNewLan extends GuiBase{

	private GuiButton btnAllowCommands;
	private GuiButton btnGamemode;
	private String gameMode = "survival";
	private boolean allowCommands;

	public GuiNewLan(GuiScreen pastScreen) {
		super(pastScreen);
	}

	public void initGui()
	{
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
		this.buttonList.add(new GuiButton(102, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
		this.buttonList.add(this.btnGamemode = new GuiButton(104, this.width / 2 - 155, this.height - 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
		this.buttonList.add(this.btnAllowCommands = new GuiButton(103, this.width / 2 + 5, this.height - 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
		this.updateBtnGamemodeAndCommands();
	}

	private void updateBtnGamemodeAndCommands()
	{
		this.btnGamemode.displayString = (I18n.format("selectWorld.gameMode", new Object[0]) + ": " + I18n.format(new StringBuilder().append("selectWorld.gameMode.").append(this.gameMode).toString(), new Object[0]));
		this.btnAllowCommands.displayString = I18n.format("selectWorld.allowCommands", new Object[0]) + " ";

		if (this.allowCommands)
		{
			this.btnAllowCommands.displayString = this.btnAllowCommands.displayString + I18n.format("options.on", new Object[0]);
		}
		else
		{
			this.btnAllowCommands.displayString = this.btnAllowCommands.displayString + I18n.format("options.off", new Object[0]);
		}
	}

	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 102)
		{
			this.mc.displayGuiScreen(pastScreen);
		}
		else if (button.id == 104)
		{
			if (this.gameMode.equals("survival"))
			{
				this.gameMode = "creative";
			}
			else if (this.gameMode.equals("creative"))
			{
				this.gameMode = "adventure";
			}
			else
			{
				this.gameMode = "survival";
			}

			this.updateBtnGamemodeAndCommands();
		}
		else if (button.id == 103)
		{
			this.allowCommands = !this.allowCommands;
			this.updateBtnGamemodeAndCommands();
		}
		else if (button.id == 101)
		{
			this.mc.displayGuiScreen((GuiScreen)null);
			String ip = this.mc.getIntegratedServer().shareToLAN(GameType.getByName(gameMode), this.allowCommands);
			Object object;

			if (ip != null)
			{
				object = new TextComponentTranslation("commands.publish.started", new Object[] {ip});
			}
			else
			{
				object = new TextComponentString("commands.publish.failed");
			}

			this.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)object);
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		initGui();
		super.drawScreen(mouseX, mouseY, partialTicks);
		int spaceAvailable = height - 38 - 20;
		int spaceRequired = Math.min(spaceAvailable, 10 + 10 * Language.LAN_ALERT.length);

		int offset = 10 + (spaceAvailable - spaceRequired) / 2;
		for (String line : Language.LAN_ALERT)
		{
			if (offset >= spaceAvailable)
			{
				drawCenteredString(fontRenderer, "...", width / 2, offset, 16777215);
				break;
			}
			if (!line.isEmpty()) {
				drawCenteredString(fontRenderer, line, width / 2, offset, 16777215);
			}
			offset += 10;
		}
		this.drawCenteredString(this.fontRenderer, I18n.format("lanServer.otherPlayers", new Object[0]), this.width / 2, this.height - 120, 16777215);
	}

}
