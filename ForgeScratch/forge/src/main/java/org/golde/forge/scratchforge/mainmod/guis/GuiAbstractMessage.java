package org.golde.forge.scratchforge.mainmod.guis;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

/*
 * Based off of forge's GuiNotification class
 */
public abstract class GuiAbstractMessage extends GuiBase{
	private final String[] lines;

	public GuiAbstractMessage(GuiScreen pastScreen, String lines[]) {
		super(pastScreen);
		this.lines = lines;
	}

	@Override
	public final void initGui() {
		buttonList.add(new GuiButton(0, width / 2 - 100, height - 38, I18n.format("gui.done", new Object[0])));
	}

	@Override
	protected final void actionPerformed(GuiButton button)
	{
		if(button.enabled && button.id == 0) {
			onDoneButtonClicked();
		}
	}
	
	public abstract void onDoneButtonClicked();
	
	
	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		//Draw Text
		int spaceAvailable = height - 38 - 20;
		int spaceRequired = Math.min(spaceAvailable, 10 + 10 * lines.length);

		int offset = 10 + (spaceAvailable - spaceRequired) / 2;
		for (String line : lines)
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

	}

}
