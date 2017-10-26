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
import org.golde.forge.scratchforge.mainmod.guis.GuiNewLan;
import org.golde.forge.scratchforge.mainmod.guis.GuiNewMainMenu;
import org.golde.forge.scratchforge.mainmod.guis.GuiNewMultiplayer;

import com.google.common.collect.SetMultimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid=ForgeModScratchForge.MOD_ID, value=Side.CLIENT)
public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		//Mod Config Folder -> Minecraft Dir - > For dir -> root
		PLog.info(event.getModConfigurationDirectory().getParentFile().getParentFile().getParentFile().getAbsolutePath());
		Config.load(event.getModConfigurationDirectory().getParentFile().getParentFile().getParentFile());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        //ModBlocks.initModels();
        ModItems.initModels();
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
			if(player != null) {player.sendMessage(new TextComponentString(Language.CHAT_MISSING_TEXTURES.replace("%failed%", failed)));}
		}
	}

	@SubscribeEvent
	public void onGuiChange(GuiOpenEvent event) {

		if(event.getGui() != null) {
			//PLog.info("Gui Change: " + event.gui.getClass().getName());
		}

		//Remove the missing blocks message that forge puts
		//Kids might get confused so we will just remove it
		if(event.getGui() instanceof GuiConfirmation) {

			//Make forge not do backups because were skipping the gui screen
			System.getProperties().setProperty("fml.doNotBackup", "true");

			//Use reflection to automatically simulate pushing the OK button
			try {
				GuiConfirmation gui = (GuiConfirmation)event.getGui();
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
			event.setGui(null); //Don't display the screen
		}

		//Custom main menu screen
		if(event.getGui() instanceof GuiMainMenu) {
			event.setGui(new GuiNewMainMenu());
		}

		//Custom ingame options to get rid of forge test
		if(event.getGui() instanceof GuiIngameMenu) {
			event.setGui(new GuiNewIngameOptions());
		}

		//Handle Mod Rejections
		if(event.getGui() instanceof GuiDisconnected) {
			try {
				GuiDisconnected gui = (GuiDisconnected)event.getGui();
				Class clazz = gui.getClass();
				Field field = JavaHelpers.getField(clazz, "message");
				field.setAccessible(true);
				ITextComponent message = (ITextComponent)field.get(gui);
				
				field = JavaHelpers.getField(clazz, "parentScreen");
				field.setAccessible(true);
				GuiScreen pastGuiScreen = (GuiScreen)field.get(gui);
				if(message.getUnformattedText().startsWith("Mod rejections ")) {
					event.setGui(new GuiMessage(pastGuiScreen, Language.LAN_REJECT)); 
				}
				else if(message.getUnformattedText().contains("1.7.10")) {
					event.setGui(new GuiMessage(pastGuiScreen, Language.V_1_7_10_WORLD)); 
				}
			}
			catch(Exception e) {
				PLog.error(e, "Failed to do reflection!");
			}
		}

	}

}
