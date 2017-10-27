package org.golde.forge.scratchforge.tutorialmod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.golde.forge.scratchforge.base.helpers.PLog;
import org.golde.forge.scratchforge.base.helpers.Title;
import org.golde.forge.scratchforge.tutorialmod.guis.GuiLimitedIngameOptions;
import org.golde.forge.scratchforge.tutorialmod.guis.GuiTemp;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.SetMultimap;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	private static int place;

	@Override
	public void init(FMLInitializationEvent event, int place) {
		this.place = place;
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	

	//=============================== [ Gui Switching ] ===============================
	@SubscribeEvent
	public void onGuiChange(GuiOpenEvent event) {
		if(event.gui instanceof GuiMainMenu) {
			event.gui = new GuiTemp();
		} else if(event.gui instanceof GuiIngameMenu) {
			event.gui = new GuiLimitedIngameOptions();
			titles.add(new Title("Oh, this is cool"));
		}
	}
	//==================================================================================
	
	
	
	//=============================== [ Start Integrated Server ] ===============================
	private boolean isInGame = false;
	
	@SubscribeEvent
	public void renderEvent(TickEvent.RenderTickEvent event) {
		if(!isInGame) {
			isInGame = true;
			Minecraft.getMinecraft().launchIntegratedServer("ScratchForge_Tutorial", "ScratchForge_Tutorial", new WorldSettings(0, GameType.CREATIVE, false, false, WorldType.FLAT));
		}
	}
	//============================================================================================
	
	
	
	//=============================== [ Handle Titles ] ===============================
	private List<Title> titles = new ArrayList<Title>();
	
	@SubscribeEvent
	public void renderTextEvent(RenderGameOverlayEvent.Text event) { //This will fill up with titles... Not sure how to remove them
		if(event.isCanceled()) {return;}
		for(Title title:titles) {
			if(title.shouldRender()) {
				title.render();
				title.setSubTitle("" + title.getRenderTime());
			}
		}
	}
	//=================================================================================
	
	

}
