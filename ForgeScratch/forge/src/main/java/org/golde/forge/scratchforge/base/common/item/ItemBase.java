package org.golde.forge.scratchforge.base.common.item;

import java.util.ArrayList;
import java.util.List;

import org.golde.forge.scratchforge.base.helpers.JavaHelpers;
import org.golde.forge.scratchforge.base.helpers.ModHelpers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBase extends Item{

	public static List<Item> REGISTRY = new ArrayList<Item>();
	
	public ItemBase(String id, CreativeTabs creativeTab, String rawName, int maxStackSize) {
		
		setCreativeTab(creativeTab);
		setMaxStackSize(maxStackSize);
		canRepair = false;

		String name = JavaHelpers.makeJavaId(rawName);
		setUnlocalizedName(name);
		setRegistryName(id, name);
		
		REGISTRY.add(this);
		ModHelpers.addTranslation(this, rawName);
	}
	
}
