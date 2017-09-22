package org.golde.forge.scratchforge.mods.entitytest;

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

import java.text.*;
import java.util.*;
import java.lang.*;

import io.netty.buffer.*;
import io.netty.channel.*;

public class CommonProxy {

	/* Block Variables */
	static Mcblock_Entity_Test mcblock_block_Entity_Test;


	/* BlockFlower Variables */
	

	/* BlockPlant Variables */
	

	/* Item Variables */
	

	public void preInit(FMLPreInitializationEvent event){
		/* Block Constructor Calls */
		mcblock_block_Entity_Test = new Mcblock_Entity_Test();


		/* BlockFlower Constructor Calls */
		

		/* BlockPlant Constructor Calls */
		

		/* Item Constructor Calls */
		
	}

	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
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

	






protected double i;
protected double j;
/**
 * Description goes here
 *
 * @param object
 * @return String
 */
public static String blocklyToString(Object object) {
    String result;
    if (object instanceof String) {
        result = (String) object;
    } else {
        // must be a number
        // might be a double
        try {
            Double d = (Double) object;
            // it was a double, so keep going
            NumberFormat formatter = new DecimalFormat("#.#####");
            result = formatter.format(d);

        } catch (Exception ex) {
            // not a double, see if it is an integer
            try {
                Integer i = (Integer) object;
                // format should be number with a decimal point
                result = i.toString();
            } catch (Exception ex2) {
                // not a double or integer
                result = "UNKNOWN";
            }
        }
    }

  return result;
}


    public class Mcblock_Entity_Test extends BlockBase {
        public Mcblock_Entity_Test() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Entity Test", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(false){
    setResistance(6000000.0F);
}
        }

        @Override
        public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {
             EntityLiving entity = null;
                for (i = -10; i<=10; i++) {
            for (j = -10; j<=10; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    world.setBlock((int)((x) + i), (int)(y), (int)((z) + j), (Blocks.gold_block), 0, 3);
                    entity = ModHelpers.spawnEntityInWorld(world, ((x) + i), ((y) + 2), ((z) + j), "PigZombie");
                    if(entity != null){
                        entity.setCustomNameTag(("Bob" + blocklyToString(j)));
                        entity.setAlwaysRenderNameTag(true);
                    }
                }
            } // end for
        } // end for

        }
    }


}
