package org.golde.forge.scratchforge.tutorialmod;

import java.util.ArrayList;
import java.util.List;

import org.golde.forge.scratchforge.base.helpers.ModHelpers;
import org.golde.forge.scratchforge.base.helpers.Title;
import org.golde.forge.scratchforge.mainmod.Config;
import org.golde.forge.scratchforge.tutorialmod.guis.GuiLimitedIngameOptions;
import org.golde.forge.scratchforge.tutorialmod.guis.GuiTemp;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ClientProxy extends CommonProxy {

	EnumTutorialProgress place;

	@Override
	public void init(FMLInitializationEvent event, int place) {
		this.place = EnumTutorialProgress.get(place);
		if(this.place == null) {
			FMLClientHandler.instance().haltGame("Invallid progress!", new NullPointerException("Invallid progress!"));
		}
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	//=============================== [ Util Event Stuff ] ===============================

	private boolean isInGame = false;
	private List<Title> titles = new ArrayList<Title>();

	@SubscribeEvent
	public void onGuiChange(GuiOpenEvent event) {
		if(event.gui instanceof GuiMainMenu) {
			event.gui = new GuiTemp();
		} 
		else if(event.gui instanceof GuiIngameMenu) {
			event.gui = new GuiLimitedIngameOptions();
		} 
		else if(event.gui instanceof GuiChat) {

		}
	}

	@SubscribeEvent
	public void renderEvent(TickEvent.RenderTickEvent event) {
		if(!isInGame) {
			isInGame = true;
			Minecraft.getMinecraft().launchIntegratedServer("ScratchForge_Tutorial", "ScratchForge_Tutorial", new WorldSettings(0, GameType.CREATIVE, false, false, WorldType.FLAT));
		}
	}



	@SubscribeEvent
	public void renderTextEvent(RenderGameOverlayEvent.Text event) { //This will fill up with titles... Not sure how to remove them
		if(event.isCanceled()) {return;}
		for(Title title:titles) {
			if(title.shouldRender()) {
				title.render();
			}
		}
	}

	private void completePuzzle(EntityPlayer player) {
		titles.add(new Title(EnumChatFormatting.GREEN + "Congratulations!", EnumChatFormatting.YELLOW + "§ePuzzle " + EnumChatFormatting.GOLD + place.getId() + EnumChatFormatting.YELLOW + " completed."));
		//ModHelpers.playSound(player, "random.levelup");
		player.worldObj.playSoundAtEntity(player, "random.levelup", 1.0f, 1.0f);
		ModHelpers.sendChatMessage(player, EnumChatFormatting.GOLD + "You have completed puzzle "+ EnumChatFormatting.YELLOW + place.getId() + EnumChatFormatting.GOLD + " successfully!");
		if(place.getSuccessMessage() != null) {ModHelpers.sendChatMessage(player, place.getSuccessMessage());}
		Config.updateTutorialPlace();
	}

	//=================================================================================


	//Joining game and other events that deal with position are here

	@SubscribeEvent
	public void joinGame(PlayerLoggedInEvent event) {
		if(place.getInstructions() != null) {ModHelpers.sendChatMessage(event.player, place.getInstructions());}

		if(place == EnumTutorialProgress.START) {
			completePuzzle(event.player);
		}
	}

}

