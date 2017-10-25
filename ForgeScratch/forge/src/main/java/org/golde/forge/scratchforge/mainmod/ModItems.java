package org.golde.forge.scratchforge.mainmod;

import org.golde.forge.scratchforge.mainmod.CommonProxy.DebugItem;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

	@GameRegistry.ObjectHolder("scratchforge:debugger")
	public static DebugItem debugger;

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		debugger.initModel();
	}

}
