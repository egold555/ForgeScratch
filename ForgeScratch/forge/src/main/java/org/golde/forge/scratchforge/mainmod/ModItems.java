package org.golde.forge.scratchforge.mainmod;

import org.golde.forge.scratchforge.mainmod.CommonProxy.DebugItem;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

	public static DebugItem debugger;

	public static void initItems() {
		debugger = new DebugItem();
	}
	
	public static void registerRenders() {
		debugger.registerRender();
	}

}
