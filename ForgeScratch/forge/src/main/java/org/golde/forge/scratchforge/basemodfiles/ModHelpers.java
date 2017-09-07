package org.golde.forge.scratchforge.basemodfiles;

import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
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

	public static void sendChatMessage(EntityPlayer player, String msg) {
		player.addChatMessage(new ChatComponentText(msg));
	}

	public static String joinStrings(List<String> list, String conjunction, int iequals)
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(int i = iequals; i < list.size(); ++i)
		{
			String item = list.get(i);

			if (first)
				first = false;
			else
				sb.append(conjunction);
			sb.append(item);
		}
		return sb.toString();
	}


	public static void spawnEntityInWorld(World world, int x, int y, int z, String entity) {
		if(world.isRemote) {return;}
		Entity theEntity = EntityList.createEntityByName(entity, world);
		if(theEntity == null) {
			PLog.error("Entity '" + entity + "' does not exist!");
			return;
		}
		theEntity.setPosition(x + 0.5f, y + 1, z + 0.5f);
		world.spawnEntityInWorld(theEntity);
	}

	public static void addPotionToPlayer(EntityPlayer player, int potion, int seconds, int amp, boolean invis) {
		player.addPotionEffect(new PotionEffect(potion, seconds * 20, amp, invis));
	}

	public static String makeJavaId(String name) {
		String result = "";
		for (int i = 0; i < name.length(); ++i) {
			char c = name.charAt(i);
			if (isJavaId(c)) {
				result = result + c;
			}
			else {
				result = result + "_";
			}
		}

		return result;
	}

	public static boolean isJavaId(char c) {
		if (c >= 'A' && c <= 'Z')
			return true;
		else if (c >= 'a' && c <= 'z') 
			return true;
		else if (c >= '0' && c <= '9') 
			return true;

		return false;
	}

}
