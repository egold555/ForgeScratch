package org.golde.forge.scratchforge.mods.test;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.golde.forge.scratchforge.mods.test.CommonProxy.*;
public class ModBlocks {

	@GameRegistry.ObjectHolder(ForgeMod.MOD_ID + ":block_name")
public static Mcblock_Block_Name mcblock_Block_Name;



	@SideOnly(Side.CLIENT)
	public static void initModels() {
		mcblock_Block_Name.initModel();

	}
	
}
