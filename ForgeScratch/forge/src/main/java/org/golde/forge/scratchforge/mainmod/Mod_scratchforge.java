package org.golde.forge.scratchforge.mainmod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.golde.forge.scratchforge.basemodfiles.BlockBase;
import org.golde.forge.scratchforge.basemodfiles.JavaHelpers;
import org.golde.forge.scratchforge.basemodfiles.ModHelpers;
import org.golde.forge.scratchforge.basemodfiles.PLog;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.GuiConfirmation;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.StartupQuery;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Mod_scratchforge.MOD_ID, name=Mod_scratchforge.MOD_NAME, version="1.0")
public class Mod_scratchforge {
	public static final String MOD_NAME = "Scratch Forge";
	public static final String MOD_ID = "sf_" + MOD_NAME;
	public static final String BLOCK_ID = MOD_ID + ":";


	/*public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_NAME.replaceFirst(" ", "_")) {

		@Override
		public Item getTabIconItem() {
			return Items.iron_pickaxe;
		}

	};*/

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//ModHelpers.addTranslation(CREATIVE_TAB.getTranslatedTabLabel(), MOD_NAME);

		//Mod Config Folder -> Minecraft Dir - > For dir -> root
		PLog.info(event.getModConfigurationDirectory().getParentFile().getParentFile().getParentFile().getAbsolutePath());
		Config.load(event.getModConfigurationDirectory().getParentFile().getParentFile().getParentFile());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

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
				failedTextures.add(s.getResourcePath().replace("textures/blocks/", "").replace(".png", "").replace("_", " "));
				failedTextures.add(s.getResourcePath().replace("textures/items/", "").replace(".png", "").replace("_", " "));
			}
		}
		catch(Exception e) {
			PLog.error(e, "Failed to do reflection!");
		}

		if(failedTextures.size() > 0) {
			String failed = JavaHelpers.joinStrings(new ArrayList<String>(failedTextures), ", ", 0);
			ModHelpers.sendChatMessage(event.player, EnumChatFormatting.GOLD + "Hey! It seems like you do not have textures for "+ EnumChatFormatting.RESET + failed + EnumChatFormatting.GOLD + "." );
		}
	}

	@SubscribeEvent
	public void onGuiChange(GuiOpenEvent event) {

		//Remove the missing blocks message that forge puts
		//Kids might get confused so we will just remove it
		if(event.gui instanceof GuiConfirmation) {

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

		if(event.gui instanceof GuiMainMenu) {
			event.gui = new GuiNewMainMenu();
		}

		if(Config.isMultiplayerLimitedToLan()) {
			if(event.gui instanceof GuiMultiplayer) {
				event.gui = new GuiNewMultiplayer();
			}
		}
	}





}
