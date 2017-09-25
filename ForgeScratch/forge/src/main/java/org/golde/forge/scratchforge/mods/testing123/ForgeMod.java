package org.golde.forge.scratchforge.mods.testing123;

import org.golde.forge.scratchforge.base.common.block.*;
import org.golde.forge.scratchforge.base.common.item.*;
import org.golde.forge.scratchforge.base.common.world.*;
import org.golde.forge.scratchforge.base.helpers.*;

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
import net.minecraft.client.renderer.texture.*;
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

import java.text.*;
import java.util.*;
import java.lang.*;

import io.netty.buffer.*;
import io.netty.channel.*;

@Mod(modid = ForgeMod.MOD_ID, name=ForgeMod.MOD_NAME, version="1.0")
public class ForgeMod implements IWorldGenerator{
    
	public static final String MOD_NAME = "testing123";
	public static final String MOD_ID = "sf_" + MOD_NAME;
	public static final String BLOCK_ID = MOD_ID + ":";
    public static final String MOD_PACKAGE = "org.golde.forge.scratchforge.mods.testing123";
    
    @SidedProxy(clientSide = MOD_PACKAGE + ".ClientProxy", serverSide = MOD_PACKAGE + ".CommonProxy")
	public static CommonProxy PROXY;
    
    @Instance(ForgeMod.MOD_ID)
	public static ForgeMod INSTANCE;
    
	public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_NAME.replaceFirst(" ", "_")) {

		@Override
		public Item getTabIconItem() {
			return Items.iron_axe;
		}

	};
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		chunkX = chunkX * 16;
		chunkZ = chunkZ * 16;
		if (world.provider.dimensionId == -1) {
			PROXY.generateNether(world, random, chunkX, chunkZ);
		}	
		if (world.provider.dimensionId == 0) {
			PROXY.generateSurface(world, random, chunkX, chunkZ);
		}
		

	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(this, 1);
        PROXY.init(event);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        PROXY.preInit(event);
	}

}
