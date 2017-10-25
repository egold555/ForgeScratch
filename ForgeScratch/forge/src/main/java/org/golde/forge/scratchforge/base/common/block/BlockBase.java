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
	
	public static List<Block> REGISTRY = new ArrayList<Block>();
	
	public BlockBase(String blockId, CreativeTabs creativeTab, String name) {
		this(blockId, creativeTab, name, Material.ROCK, SoundType.STONE);
	}
	
	public BlockBase(String blockId, CreativeTabs creativeTab, String name, Material material) {
		this(blockId, creativeTab, name, material, SoundType.STONE);
	}
	
	public BlockBase(String blockId, CreativeTabs creatibeTab, String rawName, Material material, SoundType sound) {
		super(material);
		String name = JavaHelpers.makeJavaId(rawName);
		setUnlocalizedName(name);
		setRegistryName(blockId, name);
		this.setHardness(1.5f);
        this.setResistance(10.0F);
        setSoundType(sound);
        setCreativeTab(creatibeTab);
        REGISTRY.add(this);
        ModHelpers.addTranslation(this, rawName);
	}
	

	
}
