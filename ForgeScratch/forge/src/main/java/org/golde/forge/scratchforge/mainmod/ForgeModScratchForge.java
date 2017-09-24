package org.golde.forge.scratchforge.mainmod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.golde.forge.scratchforge.base.common.item.ItemBase;
import org.golde.forge.scratchforge.base.helpers.JavaHelpers;
import org.golde.forge.scratchforge.base.helpers.ModHelpers;
import org.golde.forge.scratchforge.base.helpers.PLog;

import com.google.common.collect.SetMultimap;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.GuiConfirmation;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.StartupQuery;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ForgeModScratchForge.MOD_ID, name=ForgeModScratchForge.MOD_NAME, version="1.0")
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

}
