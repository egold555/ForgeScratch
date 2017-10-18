package org.golde.forge.scratchforge.mods.test;

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
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.common.gameevent.TickEvent.*;
import cpw.mods.fml.common.gameevent.InputEvent.*;
import cpw.mods.fml.common.gameevent.PlayerEvent.*;
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

public class CommonProxy {

	public static Scheduler scheduler = new Scheduler();

	/* Block Variables */


	/* BlockFlower Variables */


	/* BlockPlant Variables */


	/* Item Variables */
	public static SpawnEgg spawnEgg;


	/* Entity Variables */
	/*Variables - Entity*/

	public void preInit(FMLPreInitializationEvent event){
		/* Block Constructor Calls */


		/* BlockFlower Constructor Calls */


		/* BlockPlant Constructor Calls */


		/* Item Constructor Calls */
		spawnEgg = new SpawnEgg(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB);


		/* Entity Constructor Calls */
		createEntity(Mcentity_Creature_Name.class, Mcentity_Creature_Name.RAW_NAME, Mcentity_Creature_Name.NAME, Mcentity_Creature_Name.EGG_P, Mcentity_Creature_Name.EGG_S); //BatNew

	}

	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);

		/* Recipe Registry */

	}

	public void serverLoad(FMLServerStartingEvent event) {
		/* Command Registry */

	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		scheduler.update();
	}

	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		for (int i = 0; i < 20; i++) {
			int x = chunkX + random.nextInt(16) + 8;
			int y = random.nextInt(128);
			int z = chunkZ + random.nextInt(16) + 8;

			/*Overworld world generation for flowers*/


			/*Overworld world generation for plants*/


		}
	}

	//TODO: Implement nether plant generation?
	public void generateNether(World world, Random random, int chunkX, int chunkZ) {
		for (int i = 0; i < 20; i++) {
			int x = chunkX + random.nextInt(16) + 8;
			int y = random.nextInt(128);
			int z = chunkZ + random.nextInt(16) + 8;

			/*Nether generation for flowers*/
			/*WorldGen - Nether - Flowers*/

			/*Nether generation for plants*/
			/*WorldGen - Nether - Plant*/

		}
	}

	public void createEntity(Class entityClass, String rawEntityName, String entityName, int solidColor, int spotColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, rawEntityName, id);
		EntityRegistry.registerModEntity(entityClass, rawEntityName, id, ForgeMod.INSTANCE, 64, 1, true);
		if(solidColor != -1 && spotColor != -1) {
			createEgg(id, solidColor, spotColor);
		}
		//TODO: Add language
	}

	private void createEgg(int id, int solidColor, int spotColor) {
		spawnEgg.entityEggs.put(Integer.valueOf(id), new EntityList.EntityEggInfo(id, solidColor, spotColor));
	}


	/*type:global*/





	/*type:entity*/
	/*model:BatNew*/
	public static class Mcentity_Creature_Name extends EntityCreature {
		public static final String RAW_NAME = "Creature Name";
		public static final String NAME = "Creature_Name";
		public static final boolean SPAWN_NATURALLY = false;
		public static final int EGG_P = 0xff0000;
		public static final int EGG_S = 0x33ff33;

		public Mcentity_Creature_Name(World world){
			super(world);
			this.targetTasks.addTask(targetTasks.taskEntries.size() + 1, new CustomAITest(this));
		}
		
		class CustomAITest extends EntityAIBase {
			
			private final EntityCreature entity;

			public CustomAITest (EntityCreature entity) {
				this.entity = entity;
			}
			
			
			
			@Override
			public boolean shouldExecute() {
				return !entity.worldObj.isDaytime();
			}
			
			@Override
			public void startExecuting() {
				entity.setHealth(0);
			}
			
			
		}


	}


}
