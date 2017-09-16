package org.golde.forge.scratchforge.mods;

import org.golde.forge.scratchforge.basemodfiles.*;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.settings.*;
import net.minecraft.command.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.dispenser.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.rcon.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.profiler.*;
import net.minecraft.server.*;
import net.minecraft.server.dedicated.*;
import net.minecraft.server.gui.*;
import net.minecraft.server.integrated.*;
import net.minecraft.server.management.*;
import net.minecraft.src.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.world.demo.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.storage.*;

import net.minecraftforge.classloading.*;
import net.minecraftforge.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.sound.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.item.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.minecart.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.terraingen.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.oredict.*;
import net.minecraftforge.transformers.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.relauncher.*;

import org.apache.logging.log4j.*;

import java.util.*;

import io.netty.buffer.*;
import io.netty.channel.*;

@Mod(modid = Mod_test.MOD_ID, name=Mod_test.MOD_NAME, version="1.0")
public class Mod_test {
	public static final String MOD_NAME = "test";
	public static final String MOD_ID = "sf_" + MOD_NAME;
	public static final String BLOCK_ID = MOD_ID + ":";

	public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_NAME.replaceFirst(" ", "_")) {

		@Override
		public Item getTabIconItem() {
			return Items.iron_axe;
		}

	};
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	/* Block Variables */
	
	
	/* Item Variables */
	static Mcitem_change_me mcblock_item_change_me;


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModHelpers.addTranslation(CREATIVE_TAB.getTranslatedTabLabel(), MOD_NAME);
		
		/* Block Constructor Calls */
		
		
		/* Item Constructor Calls */
		mcblock_item_change_me = new Mcitem_change_me();

		
	}

	





    public class Mcitem_change_me extends ItemBase {
        public Mcitem_change_me() {
            super(BLOCK_ID, CREATIVE_TAB, "change_me", 64);
        }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int meta, float dx, float dy, float dz) {
            ModHelpers.sendChatMessage(player, "chat");
        PLog.game("con");

        return true;
    }    }

}
