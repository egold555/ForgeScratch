package org.golde.forge.scratchforge.basemodfiles;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBase extends Block{
	
	public BlockBase(String blockId, CreativeTabs creativeTab, String name) {
		this(blockId, creativeTab, name, Material.rock, Block.soundTypeStone);
	}
	
	public BlockBase(String blockId, CreativeTabs creativeTab, String name, Material material) {
		this(blockId, creativeTab, name, material, Block.soundTypeStone);
	}
	
	public BlockBase(String blockId, CreativeTabs creatibeTab, String rawName, Material material, SoundType sound) {
		super(material);
		String name = ModHelpers.makeJavaId(rawName);
		setBlockName(name);
		setBlockTextureName(blockId + name);
		this.setHardness(1.5f);
        this.setResistance(10.0F);
        setStepSound(sound);
        setCreativeTab(creatibeTab);
        
        GameRegistry.registerBlock(this, this.getUnlocalizedName().substring(5));
        ModHelpers.addTranslation(this, rawName);
	}
	

	
}
