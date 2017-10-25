package org.golde.forge.scratchforge.base.common.block;

import java.util.Random;

import org.golde.forge.scratchforge.base.helpers.JavaHelpers;
import org.golde.forge.scratchforge.base.helpers.ModHelpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBasePlant extends BlockReed {

	private String texture;
	private EnumPlantType plantType;
	private int growHeight = 3;
	private boolean requiresWater = true;
	private boolean doesGenerateInWorld = true;

	public BlockBasePlant(String MOD_ID, CreativeTabs creatibeTab, String rawName, EnumPlantType plantType, boolean doesGenInWorld, boolean needsWaterToGen, int maxGrowHeight) {
		super();
		this.plantType = plantType;
		String name = JavaHelpers.makeJavaId(rawName);
		setUnlocalizedName(MOD_ID + "." + name);
		setRegistryName(rawName);
		this.setHardness(0.01F);
		this.setResistance(2.0F);
		setCreativeTab(creatibeTab);

		this.requiresWater = needsWaterToGen;
		this.doesGenerateInWorld = doesGenInWorld;
		this.growHeight = maxGrowHeight;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		BlockPos newPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
		Block block = world.getBlockState(newPos).getBlock();
		return (block.canSustainPlant(block.getDefaultState(), world, newPos, EnumFacing.UP, this) || block == this);
	}

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
		return 16777215;
	}

	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if ((world.getBlockState(pos.down()).getBlock() == this) || (checkForDrop(world, pos, state))) {
			if (world.isAirBlock(pos.up()))
			{
				for (int i = 1; world.getBlockState(pos.down(i)).getBlock() == this; i++) {
					if (i < 3)
					{
						int j = ((Integer)state.getValue(AGE)).intValue();
						if (ForgeHooks.onCropsGrowPre(world, pos, state, true))
						{
							if (j == 15)
							{
								world.setBlockState(pos.up(), getDefaultState());
								world.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(0)), 4);
							}
							else
							{
								world.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(j + 1)), 4);
							}
							ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
						}
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
