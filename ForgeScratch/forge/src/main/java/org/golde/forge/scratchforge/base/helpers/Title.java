package org.golde.forge.scratchforge.base.helpers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

/*
 @SubscribeEvent
	public void renderTextEvent(RenderGameOverlayEvent.Text event) { //This will fill up with titles... Not sure how to remove them
		if(event.isCanceled()) {return;}
		for(Title title:titles) {
			if(title.shouldRender()) {
				title.render();
			}
		}
	}
 */
public class Title {

	private String title, subTitle;
	private int renderTime = 0;
	private boolean renderForever = false;
	private int maxRenderTime = 250;
	
	public Title() {
		this("", "");
	}
	
	public Title(String title) {
		this(title, "");
	}
	
	public Title(String title, String subTitle) {
		this.title = title;
		this.subTitle = subTitle;
	}
	
	public void render() {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledResolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int scaledX = scaledResolution.getScaledWidth();
        int scaledY = scaledResolution.getScaledHeight();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(scaledX / 2), (float)(scaledY / 2), 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        GlStateManager.scale(4.0F, 4.0F, 4.0F);

        mc.fontRenderer.drawString(title, (-mc.fontRenderer.getStringWidth(title) / 2), -10, 0xFFFFFF, true);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        mc.fontRenderer.drawString(subTitle, (-mc.fontRenderer.getStringWidth(subTitle) / 2), 5, 0xFFFFFF, true);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        renderTime++;
	}
	
	public boolean shouldRender() {
		if(renderForever) {return true;}
		if(renderTime > maxRenderTime) {return false;}
		return true;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public int getRenderTime() {
		return renderTime;
	}
	
	public void setMaxRenderTime(int maxRenderTime) {
		this.maxRenderTime = maxRenderTime;
	}
	
	public void setRenderForever(boolean renderForever) {
		this.renderForever = renderForever;
	}
	
}
