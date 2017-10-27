package org.golde.forge.scratchforge.mainmod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.golde.forge.scratchforge.base.helpers.JavaHelpers;
import org.golde.forge.scratchforge.base.helpers.ModHelpers;
import org.golde.forge.scratchforge.base.helpers.PLog;
import org.golde.forge.scratchforge.mainmod.guis.GuiMessage;
import org.golde.forge.scratchforge.mainmod.guis.GuiNewIngameOptions;
import org.golde.forge.scratchforge.mainmod.guis.GuiNewMainMenu;
import org.golde.forge.scratchforge.mainmod.guis.GuiNewMultiplayer;

import com.google.common.collect.SetMultimap;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.GuiConfirmation;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.StartupQuery;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		//Mod Config Folder -> Minecraft Dir - > For dir -> root
		PLog.info(event.getModConfigurationDirectory().getParentFile().getParentFile().getParentFile().getAbsolutePath());
		Config.load(event.getModConfigurationDirectory().getParentFile().getParentFile().getParentFile());

		ModHelpers.addTranslation(ForgeModScratchForge.CREATIVE_TAB.getTranslatedTabLabel(), ForgeModScratchForge.MOD_NAME);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		//Let people know that they are missing textures
		Set<String> failedTextures = new HashSet<String>();
		try {
			FMLClientHandler client = FMLClientHandler.instance();
			Class clazz = client.getClass();
			Field field = clazz.getDeclaredField("missingTextures");
			field.setAccessible(true);
			SetMultimap<String,ResourceLocation> map = (SetMultimap<String,ResourceLocation>)field.get(client);
			for(ResourceLocation s: map.values()) {
				failedTextures.add(
						s.getResourcePath()
						.replace("textures/blocks/", "")
						.replace("textures/items/", "")
						.replace(".png", "")
						.replace("_", " ")
						);
			}
		}
		catch(Exception e) {
			PLog.error(e, "Failed to do reflection!");
		}

		if(failedTextures.size() > 0) {
			String failed = JavaHelpers.joinStrings(new ArrayList<String>(failedTextures), ", ", 0);
			EntityPlayer player = event.player;
			if(player != null) {player.addChatMessage(new ChatComponentText(Language.CHAT_MISSING_TEXTURES.replace("%failed%", failed)));}
		}
	}

	@SubscribeEvent
	public void onGuiChange(GuiOpenEvent event) {

		if(event.gui != null) {
			//PLog.info("Gui Change: " + event.gui.getClass().getName());
		}

		//Remove the missing blocks message that forge puts
		//Kids might get confused so we will just remove it
		if(event.gui instanceof GuiConfirmation) {

			//Make forge not do backups because were skipping the gui screen
			System.getProperties().setProperty("fml.doNotBackup", "true");

			//Use reflection to automatically simulate pushing the OK button
			try {
				GuiConfirmation gui = (GuiConfirmation)event.gui;
				Class clazz = gui.getClass();
				Field field = JavaHelpers.getField(clazz, "query");
				field.setAccessible(true);
				StartupQuery query = (StartupQuery) field.get(gui);
				FMLClientHandler.instance().showGuiScreen(null);
				query.setResult(true);
				query.finish();
			}
			catch(Exception e) {
				PLog.error(e, "Failed to do reflection!");
			}
			event.gui = null; //Don't display the screen
		}

		//Custom main menu screen
		if(event.gui instanceof GuiMainMenu) {
			if(Config.isTutorial()) {return;}
			event.gui = new GuiNewMainMenu();
		}

		//Custom ingame options to get rid of forge test
		if(event.gui instanceof GuiIngameMenu) {
			if(Config.isTutorial()) {return;}
			event.gui = new GuiNewIngameOptions();
		}

		//Handle Mod Rejections
		if(event.gui instanceof GuiDisconnected) {
			try {
				GuiDisconnected gui = (GuiDisconnected)event.gui;
				Class clazz = gui.getClass();
				Field field = JavaHelpers.getField(clazz, "field_146304_f");
				field.setAccessible(true);
				IChatComponent message = (IChatComponent)field.get(gui);
				
				field = JavaHelpers.getField(clazz, "field_146307_h");
				field.setAccessible(true);
				GuiScreen pastGuiScreen = (GuiScreen)field.get(gui);
				if(message.getUnformattedText().startsWith("Mod rejections ")) {
					event.gui = new GuiMessage(pastGuiScreen, Language.LAN_REJECT);
				}
			}
			catch(Exception e) {
				PLog.error(e, "Failed to do reflection!");
			}
		}

		//If is limited, display custom screen
		if(Config.isMultiplayerLimitedToLan()) {
			if(event.gui instanceof GuiMultiplayer) {
				event.gui = new GuiNewMultiplayer();
			}
		}
	}

}
