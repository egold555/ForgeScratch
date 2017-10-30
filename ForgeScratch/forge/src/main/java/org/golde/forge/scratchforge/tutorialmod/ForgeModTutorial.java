package org.golde.forge.scratchforge.tutorialmod;

import org.golde.forge.scratchforge.mainmod.Config;
import org.golde.forge.scratchforge.mainmod.Config.ConfigKeys;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;

@Mod(modid = ForgeModTutorial.MOD_ID, name = ForgeModTutorial.MOD_NAME, version="1.0", canBeDeactivated = false)
public class ForgeModTutorial {
	public static final String MOD_NAME = "ScratchForgeTutorial";
	public static final String MOD_ID = MOD_NAME;
	public static final String BLOCK_ID = MOD_ID + ":";
	public static final String MOD_PACKAGE = "org.golde.forge.scratchforge.tutorialmod";
    
    @SidedProxy(clientSide = MOD_PACKAGE + ".ClientProxy", serverSide = MOD_PACKAGE + ".CommonProxy")
	public static CommonProxy PROXY;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if(Config.getBoolean(ConfigKeys.TUTORIAL_ENABLED)) {PROXY.init(event, Config.getInt(ConfigKeys.TUTORIAL_PLACE));}
	}
	
	
	
}
