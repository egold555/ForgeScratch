package org.golde.forge.scratchforge.mainmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = ForgeModScratchForge.MOD_ID, name=ForgeModScratchForge.MOD_NAME, version="1.0", canBeDeactivated=false)
public class ForgeModScratchForge {
	public static final String MOD_NAME = "scratchforge";
	public static final String MOD_ID = MOD_NAME;
	public static final String BLOCK_ID = MOD_ID + ":";
	public static final String MOD_PACKAGE = "org.golde.forge.scratchforge.mainmod";
    
    @SidedProxy(clientSide = MOD_PACKAGE + ".ClientProxy", serverSide = MOD_PACKAGE + ".CommonProxy")
	public static CommonProxy PROXY;

	public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_NAME.replaceFirst(" ", "_")) {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Items.BOOK);
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
