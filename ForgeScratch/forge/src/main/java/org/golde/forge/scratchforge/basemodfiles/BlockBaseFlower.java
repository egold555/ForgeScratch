package org.golde.forge.scratchforge.basemodfiles;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockBaseFlower extends BlockFlower{

	private IIcon la;
	private String texture;
	
	public BlockBaseFlower(String blockId, CreativeTabs creativeTab, String name) {
		this(blockId, creativeTab, name, Block.soundTypeStone);
	}
	
	public BlockBaseFlower(String blockId, CreativeTabs creatibeTab, String rawName,SoundType sound) {
		super(0);
		String name = JavaHelpers.makeJavaId(rawName);
		setBlockName(name);
		texture = blockId + name;
		setBlockTextureName(texture);
		this.setHardness(0.01F);
        this.setResistance(2.0F);
        setStepSound(sound);
        setCreativeTab(creatibeTab);
        
        GameRegistry.registerBlock(this, this.getUnlocalizedName().substring(5));
        ModHelpers.addTranslation(this, rawName);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return this.la;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		la = reg.registerIcon(texture);
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		for (int i = 0; i < 1; ++i) {
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
		}
	}

	public int quantityDropped(Random par1Random) {
		return 1;
	}

	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Item.getItemFromBlock(this);
	}
	
}
