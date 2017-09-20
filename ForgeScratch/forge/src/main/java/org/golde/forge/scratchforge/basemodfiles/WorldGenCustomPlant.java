package org.golde.forge.scratchforge.basemodfiles;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
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

	public boolean generate(World world, Random random, int x, int y, int z)
	{
		for (int i = 0; i < 20; i++)
		{
			int newX = x + random.nextInt(4) - random.nextInt(4);
			int newY = y;
			int newZ = z + random.nextInt(4) - random.nextInt(4);
			if(block.doesGenerateInWorld()) {
				if(world.isAirBlock(newX, newY, newZ)){
					if(block.doesRequireWater()) {
						if((world.getBlock(newX - 1, newY - 1, newZ).getMaterial() == Material.water) || (world.getBlock(newX + 1, newY - 1, newZ).getMaterial() == Material.water) || (world.getBlock(newX, newY - 1, newZ - 1).getMaterial() == Material.water) || (world.getBlock(newX, newY - 1, newZ + 1).getMaterial() == Material.water))
						{
							doGen(world, random, newX, newY, newZ);
						}
					}
					else {
						doGen(world, random, newX, newY, newZ);
					}
				}
			}


		}
		return true;
	}

	private void doGen(World world, Random random, int newX, int newY, int newZ) {
		int randomHeight = 2 + random.nextInt(random.nextInt(block.getGrowHeight()) + 1);
		for (int i1 = 0; i1 < randomHeight; i1++) {
			if (block.canBlockStay(world, newX, newY + i1, newZ)) {
				world.setBlock(newX, newY + i1, newZ, block, 0, 2);
			}
		}
	}
}
