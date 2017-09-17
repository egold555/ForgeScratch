package org.golde.forge.scratchforge.basemodfiles;

import java.util.HashMap;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ModHelpers {

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

}
