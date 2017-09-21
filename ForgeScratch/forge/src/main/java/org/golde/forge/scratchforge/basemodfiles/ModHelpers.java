package org.golde.forge.scratchforge.basemodfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ModHelpers {

	private static Random random = new Random();

	//                      old,    new
	private static HashMap<String, String> translationList = new HashMap<String, String>();

	public static void addTranslation(Block block, String to) {
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
	
	public static Entity getEntity(World world, String entity) {
		Entity theEntity = EntityList.createEntityByName(entity, world);
		if(theEntity == null) {
			PLog.error("Entity '" + entity + "' does not exist!");
			return null;
		}
		return theEntity;
	}

	public static Entity spawnEntityInWorld(World world, double x, double y, double z, String entity) {
		if(world.isRemote) {return null;}
		Entity theEntity = getEntity(world, entity);
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

		return new EntityFireworkRocket(world, x, y, z, firework);
	}
	
	public static EntityFireworkRocket getFirework(World world, double x, double y, double z, boolean flicker, boolean trail, List colors, int type, int power) {
		int[] intColors = new int[colors.size()];
		for(int i = 0; i < intColors.length; i++) {
			if(colors.get(i) instanceof String) {
				try {
					intColors[i] = JavaHelpers.hexToMinecraftColor((String)colors.get(i));
				}
				catch(Exception e) { }
			}
		}
		return getFirework(world, x, y, z, flicker, trail, intColors, type, power);
	}
	
	
	public static EntityFireworkRocket getFirework(World world, double x, double y, double z, boolean flicker, boolean trail, String color, int type, int power) {
		return getFirework(world, x, y, z, flicker, trail, JavaHelpers.hexToMinecraftColor(color), type, power);
	}
	

	public static EntityFireworkRocket getFirework(World world, double x, double y, double z, boolean flicker, boolean trail, int color, int type, int power) {
		return getFirework(world, x, y, z, flicker, trail, new int[] {color}, type, power);
	}

	public static EntityFireworkRocket getFirework(World world, double x, double y, double z, boolean flicker, boolean trail, int[] colors, int type, int power) {
		ItemStack firework = new ItemStack(Items.fireworks);
		firework.stackTagCompound = new NBTTagCompound();
		NBTTagCompound expl = new NBTTagCompound();
		
		expl.setBoolean("Flicker", flicker);
		expl.setBoolean("Trail", trail);
		expl.setIntArray("Colors", colors);
		expl.setByte("Type", (byte)type);

		NBTTagList explosions = new NBTTagList();
		explosions.appendTag(expl);

		NBTTagCompound fireworkTag = new NBTTagCompound();
		fireworkTag.setTag("Explosions", explosions);
		fireworkTag.setByte("Flight", (byte) power);
		firework.stackTagCompound.setTag("Fireworks", fireworkTag);

		return new EntityFireworkRocket(world, x, y, z, firework);
	}

	



}
