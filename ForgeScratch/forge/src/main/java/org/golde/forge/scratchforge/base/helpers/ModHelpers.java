package org.golde.forge.scratchforge.base.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ModHelpers {

	private static Random random = new Random();
	
	public static EntityLiving getEntity(World world, int entity) {
		Entity theEntity = EntityList.createEntityByID(entity, world);
		if(theEntity == null) {
			PLog.error("Entity '" + entity + "' does not exist!");
			return null;
		}
		if(!(theEntity instanceof EntityLiving)) {
			PLog.error("Entity '" + entity + "' does not extend EntityLivingBase!");
			return null;
		}
		return (EntityLiving)theEntity;
	}

	public static EntityLiving spawnEntityInWorld(World world, double x, double y, double z, int entity) {
		if(world.isRemote) {return null;}
		EntityLiving theEntity = getEntity(world, entity);
		theEntity.setPosition(x + 0.5f, y + 1, z + 0.5f);
		world.spawnEntityInWorld(theEntity);
		return theEntity;
	}

	public static void addPotionToEntity(EntityLivingBase entity, int potion, int seconds, int amp, boolean invis) {
		if(entity instanceof EntityLiving) {
			
			((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.getPotionById(potion), seconds * 20, amp, false, invis));
		}
		else if(entity instanceof EntityPlayer) {
			((EntityPlayer)entity).addPotionEffect(new PotionEffect(Potion.getPotionById(potion), seconds * 20, amp, false, invis));
		}

	}


	public static EntityFireworkRocket getRandomFirework(World world, double x, double y, double z) {
		ItemStack firework = new ItemStack(Items.FIREWORKS);
		firework.setTagCompound(new NBTTagCompound());
		NBTTagCompound expl = new NBTTagCompound();
		expl.setBoolean("Flicker", true);
		expl.setBoolean("Trail", true);

		int[] colors = new int[random.nextInt(8) + 1];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = ItemDye.DYE_COLORS[random.nextInt(16)];
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
		firework.setTagInfo("Fireworks", fireworkTag);

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
		ItemStack firework = new ItemStack(Items.FIREWORKS);
		firework.setTagCompound(new NBTTagCompound());
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
		firework.setTagInfo("Fireworks", fireworkTag);

		return new EntityFireworkRocket(world, x, y, z, firework);
	}
	
	public static TextFormatting getChatColorFromHex(String hex) {
		switch(hex) {
		case "#000": return TextFormatting.BLACK;
		case "#0000aa": return TextFormatting.DARK_BLUE;
		case "#00aa00": return TextFormatting.DARK_GREEN;
		case "#00aaaa": return TextFormatting.DARK_AQUA;
		case "#aa0000": return TextFormatting.DARK_RED;
		case "#aa00aa": return TextFormatting.DARK_PURPLE;
		case "#ffaa00": return TextFormatting.GOLD;
		case "#aaaaaa": return TextFormatting.GRAY;
		case "#555555": return TextFormatting.DARK_GRAY;
		case "#5555ff": return TextFormatting.BLUE;
		case "#55ff55": return TextFormatting.GREEN;
		case "#55ffff": return TextFormatting.AQUA;
		case "#ff5555": return TextFormatting.RED;
		case "#ff55ff": return TextFormatting.LIGHT_PURPLE;
		case "#ffff55": return TextFormatting.YELLOW;
		case "#ffffff": return TextFormatting.WHITE;
		default: return TextFormatting.RESET; 
		}
	}

}
