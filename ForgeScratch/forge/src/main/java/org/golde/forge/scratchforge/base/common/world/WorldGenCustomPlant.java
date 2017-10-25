package org.golde.forge.scratchforge.base.common.world;

import java.util.Random;

import org.golde.forge.scratchforge.base.common.block.BlockBasePlant;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/*
 * Based off of WorldGenReed
 */
public class WorldGenCustomPlant extends WorldGenerator
{
	private BlockBasePlant block;

	public WorldGenCustomPlant(BlockBasePlant block) {
		super();
		this.block = block;
	}

	public boolean generate(World world, Random random, BlockPos pos)
	{
		for (int i = 0; i < 20; i++)
		{
			BlockPos newPos = new BlockPos(pos.getX() + random.nextInt(4) - random.nextInt(4), pos.getY(), pos.getZ() + + random.nextInt(4) - random.nextInt(4));
			if(block.doesGenerateInWorld()) {
				if(world.isAirBlock(pos)){
					if(block.doesRequireWater()) {
						if((world.getBlockState(new BlockPos(newPos.getX() - 1, newPos.getY() - 1, newPos.getZ())).getMaterial() == Material.WATER) || (world.getBlockState(new BlockPos(newPos.getX() + 1, newPos.getY() -1, newPos.getZ())).getMaterial() == Material.WATER) || (world.getBlockState(new BlockPos(newPos.getX(), newPos.getY() - 1, newPos.getZ() - 1)).getMaterial() == Material.WATER) || (world.getBlockState(new BlockPos(newPos.getX(), newPos.getY() - 1, newPos.getZ() + 1)).getMaterial() == Material.WATER))
						{
							doGen(world, random, newPos);
						}
					}
					else {
						doGen(world, random, newPos);
					}
				}
			}


		}
		return true;
	}

	private void doGen(World world, Random random, BlockPos pos) {
		int randomHeight = 2 + random.nextInt(random.nextInt(block.getGrowHeight()) + 1);
		for (int i1 = 0; i1 < randomHeight; i1++) {
			BlockPos newPos = new BlockPos(pos.getX(), pos.getY() + i1, pos.getZ());
			if (block.canBlockStay(world, newPos)) {
				world.setBlockState(newPos, block.getBlockState().getBaseState(), 2);
			}
		}
	}
}
