package org.golde.forge.scratchforge.mainmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod(modid = ForgeModScratchForge.MOD_ID, name=ForgeModScratchForge.MOD_NAME, version="1.0", canBeDeactivated=false)
public class ForgeModScratchForge {
	public static final String MOD_NAME = "ScratchForge";
	public static final String MOD_ID = MOD_NAME;
	public static final String BLOCK_ID = MOD_ID + ":";
	public static final String MOD_PACKAGE = "org.golde.forge.scratchforge.mainmod";
    
    @SidedProxy(clientSide = MOD_PACKAGE + ".ClientProxy", serverSide = MOD_PACKAGE + ".CommonProxy")
	public static CommonProxy PROXY;

	public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_NAME.replaceFirst(" ", "_")) {

		@Override
		public Item getTabIconItem() {
			return Items.book;
		}

	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PROXY.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PROXY.init(event);
	}

	/*@EventHandler
	public void serverPreInit(FMLServerAboutToStartEvent event) {
		PROXY.serverPreInit(event);
	}*/
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		PROXY.serverInit(event);
	}
}
