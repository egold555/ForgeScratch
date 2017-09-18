package org.golde.forge.scratchforge.basemodfiles;

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

public class ModHelpers {

	private static Random random = new Random();
	
	//                      old,    new
	private static HashMap<String, String> translationList = new HashMap<String, String>();

	public static void addTranslation(BlockBase block, String to) {
		ItemStack item = new ItemStack(block, 1, 0);
		String from = item.getItem().getUnlocalizedName(item);
		from+=".name";
		addTranslation(from, to);
	}

	public static void addTranslation(Item item, String to) {
		addTranslation(item.getUnlocalizedName() + ".name", to);
	}

	public static void addTranslation(CreativeTabs creativeTab, String to) {
		addTranslation(creativeTab.getTranslatedTabLabel(), to);
	}

	public static void addTranslation(String from, String to) {
		LanguageRegistry.instance().addStringLocalization(from, to);
		translationList.put(from, to);
	}

	public static String getTranslation(String old) {

		if(!old.endsWith(".name")) {
			old+=".name";
		}
		return translationList.getOrDefault(old, "Failed to find translation!");
	}

	public static Entity spawnEntityInWorld(World world, double x, double y, double z, String entity) {
		if(world.isRemote) {return null;}
		Entity theEntity = EntityList.createEntityByName(entity, world);
		if(theEntity == null) {
			PLog.error("Entity '" + entity + "' does not exist!");
			return null;
		}
		theEntity.setPosition(x + 0.5f, y + 1, z + 0.5f);
		world.spawnEntityInWorld(theEntity);
		return theEntity;
	}

	public static void addPotionToEntity(Entity entity, int potion, int seconds, int amp, boolean invis) {
		if(entity instanceof EntityLiving) {
			((EntityLiving)entity).addPotionEffect(new PotionEffect(potion, seconds * 20, amp, invis));
		}
		else if(entity instanceof EntityPlayer) {
			((EntityPlayer)entity).addPotionEffect(new PotionEffect(potion, seconds * 20, amp, invis));
		}
		
	}
	
	
	public static EntityFireworkRocket getRandomFirework(World world, double x, double y, double z) {
	    ItemStack firework = new ItemStack(Items.fireworks);
	    firework.stackTagCompound = new NBTTagCompound();
	    NBTTagCompound expl = new NBTTagCompound();
	    expl.setBoolean("Flicker", true);
	    expl.setBoolean("Trail", true);

	    int[] colors = new int[random.nextInt(8) + 1];
	    for (int i = 0; i < colors.length; i++) {
	      colors[i] = ItemDye.field_150922_c[random.nextInt(16)];
	    }
	    expl.setIntArray("Colors", colors);
	    byte type = (byte) (random.nextInt(3) + 1);
	    type = type == 3 ? 4 : type;
	    expl.setByte("Type", type);

	    NBTTagList explosions = new NBTTagList();
	    explosions.appendTag(expl);

	    NBTTagCompound fireworkTag = new NBTTagCompound();
	    fireworkTag.setTag("Explosions", explosions);
	    fireworkTag.setByte("Flight", (byte) 1);
	    firework.stackTagCompound.setTag("Fireworks", fireworkTag);

	    EntityFireworkRocket e = new EntityFireworkRocket(world, x, y, z, firework);
	    return e;
	  }

}
