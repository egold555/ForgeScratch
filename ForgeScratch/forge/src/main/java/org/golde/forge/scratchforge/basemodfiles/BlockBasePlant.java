package org.golde.forge.scratchforge.basemodfiles;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBasePlant extends BlockReed {
	
	private IIcon la;
	private String texture;
	private EnumPlantType plantType;
	private int growHeight = 3;
	private boolean requiresWater = true;
	private boolean doesGenerateInWorld = true;
	
	public BlockBasePlant(String blockId, CreativeTabs creativeTab, String name, EnumPlantType plantType, boolean doesGenInWorld, boolean needsWaterToGen, int maxGrowHeight) {
		this(blockId, creativeTab, name, plantType, doesGenInWorld, needsWaterToGen, maxGrowHeight, Block.soundTypeStone);
	}
	
	public BlockBasePlant(String blockId, CreativeTabs creatibeTab, String rawName,EnumPlantType plantType, boolean doesGenInWorld, boolean needsWaterToGen, int maxGrowHeight, SoundType sound) {
		super();
		this.plantType = plantType;
		String name = JavaHelpers.makeJavaId(rawName);
		setBlockName(name);
		texture = blockId + name;
		setBlockTextureName(texture);
		this.setHardness(0.01F);
        this.setResistance(2.0F);
        setCreativeTab(creatibeTab);
        
        this.requiresWater = needsWaterToGen;
        this.doesGenerateInWorld = doesGenInWorld;
        this.growHeight = maxGrowHeight;
        
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

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return plantType;
	}

	@Override
	public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
		Block block = p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_);
		return (block.canSustainPlant(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_, ForgeDirection.UP, this) || block == this);
	}

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
		return 16777215;
	}

	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
		if (p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_) == this
				|| this.func_150170_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)) {
			if (p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_)) {
				int l;
				for (l = 1; p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - l, p_149674_4_) == this; ++l) {
					;
				}
				if (l < 10) {
					int i1 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
					if (i1 == 15) {
						p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, this);
						p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, 0, 4);
					} else {
						p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, i1 + 1, 4);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return Item.getItemFromBlock(this);
	}

	public int quantityDropped(Random par1Random) {
		return 1;
	}

	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Item.getItemFromBlock(this);
	}
	
	public int getGrowHeight() {
		return growHeight;
	}
	
	public boolean doesRequireWater() {
		return requiresWater;
	}
	
	public boolean doesGenerateInWorld() {
		return doesGenerateInWorld;
	}
	
}
