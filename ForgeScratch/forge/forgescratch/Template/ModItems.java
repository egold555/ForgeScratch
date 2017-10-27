package org.golde.forge.scratchforge.mods./*Mod Package*/;

import org.golde.forge.scratchforge.base.common.item.ItemBase;
import org.golde.forge.scratchforge.base.common.item.SpawnEgg;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.golde.forge.scratchforge.mods./*Mod Package*/.CommonProxy.*;

public class ModItems {
	
	@GameRegistry.ObjectHolder(ForgeMod.MOD_ID + ":spawnEgg")
	public static SpawnEgg spawnEgg;

	/*Variables - Item*/
	

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		if(spawnEgg!=null) {spawnEgg.initModel();}
		/*Variables - Item - Models*/
	}
	
}
