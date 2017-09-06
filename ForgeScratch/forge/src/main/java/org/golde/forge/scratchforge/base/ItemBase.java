package org.golde.forge.scratchforge.base;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBase extends Item{

	public ItemBase(CreativeTabs creatibeTab, String id, String rawName, int maxStackSize) {
		
		setCreativeTab(creatibeTab);
		setMaxStackSize(maxStackSize);
		canRepair = false;
		
		String name = rawName.replace(" ", "_");
		setUnlocalizedName(name);
		setTextureName(id + name);
		
		GameRegistry.registerItem(this, this.getUnlocalizedName().substring(5));
		ModHelpers.addTranslation(this, rawName);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {

		return super.onItemRightClick(itemstack, world, player);
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
		World world = player.worldObj;
		
		return super.onBlockStartBreak(itemstack, X, Y, Z, player);
	}
	
	
}
