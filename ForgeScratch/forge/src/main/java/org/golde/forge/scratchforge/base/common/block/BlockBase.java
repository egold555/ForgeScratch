package org.golde.forge.scratchforge.base.common.block;

import java.util.ArrayList;
import java.util.List;

import org.golde.forge.scratchforge.base.helpers.JavaHelpers;
import org.golde.forge.scratchforge.base.helpers.ModHelpers;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBase extends Block{
	
	public BlockBase(String MOD_ID, CreativeTabs creativeTab, String name) {
		this(MOD_ID, creativeTab, name, Material.ROCK, SoundType.STONE);
	}
	
	public BlockBase(String MOD_ID, CreativeTabs creativeTab, String name, Material material) {
		this(MOD_ID, creativeTab, name, material, SoundType.STONE);
	}
	
	public BlockBase(String MOD_ID, CreativeTabs creatibeTab, String rawName, Material material, SoundType sound) {
		super(material);
		String name = JavaHelpers.makeJavaId(rawName);
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setHardness(1.5f);
        this.setResistance(10.0F);
        setSoundType(sound);
        setCreativeTab(creatibeTab);
	}
	

	
}
