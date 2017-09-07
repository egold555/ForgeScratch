package org.golde.forge.scratchforge.basemodfiles;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBaseFood extends ItemFood{

	public ItemBaseFood(CreativeTabs creatibeTab, String id, String rawName, int maxStackSize, int healAmount, boolean willWolfsEat) {
		this(creatibeTab, id, rawName, maxStackSize, healAmount, 0.6F, willWolfsEat);
	}
	
	public ItemBaseFood(CreativeTabs creatibeTab, String id, String rawName, int maxStackSize, int healAmount, float saturation, boolean willWolfsEat) {
		super(healAmount, saturation, willWolfsEat);
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
	protected void onFoodEaten(ItemStack itemstack, World world, EntityPlayer player) {
		
		/* Code here */
		
		super.onFoodEaten(itemstack, world, player);
	}

}
