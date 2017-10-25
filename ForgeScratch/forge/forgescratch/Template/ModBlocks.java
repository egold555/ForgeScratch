package org.golde.forge.scratchforge.mods./*Mod Package*/;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
	
	@GameRegistry.ObjectHolder(ForgeMod.MOD_ID + ":spawnEgg")
	public static BlockBase spawnEgg;

	/* Block */

	//@GameRegistry.ObjectHolder(ForgeMod.MOD_ID + ":/*name*/")
	

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		
	}
	
}