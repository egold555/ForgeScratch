package org.golde.forge.scratchforge.base.common.item;

import org.golde.forge.scratchforge.base.helpers.JavaHelpers;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item{
	
	public ItemBase(String MOD_ID, CreativeTabs creativeTab, String rawName, int maxStackSize) {
		
		setCreativeTab(creativeTab);
		setMaxStackSize(maxStackSize);
		canRepair = false;

		String name = JavaHelpers.makeJavaId(rawName);
		setUnlocalizedName(name);
		setRegistryName(name);
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
	
}
