package org.golde.forge.scratchforge.mainmod;

import java.util.ArrayList;
import java.util.List;

import org.golde.forge.scratchforge.basemodfiles.ItemBase;
import org.golde.forge.scratchforge.basemodfiles.ModHelpers;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		new DebugItem();
	}
	
	public void init(FMLInitializationEvent event) {
		
	}
	
	class DebugItem extends ItemBase{

		public DebugItem() {
			super(ForgeModScratchForge.BLOCK_ID, ForgeModScratchForge.CREATIVE_TAB, "Debugger", 1);
		}

		@Override
		public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world,
				int x, int y, int z, int data, float dx,
				float dy, float dz) {

			if(world.isRemote) {
				List<String> l = new ArrayList<String>();

				Block block = world.getBlock(x, y, z);

				if(block != null) {
					l.add("Block: " + block.getUnlocalizedName() + " (" + block.getLocalizedName() + ")");
					l.add("Data: " + data);
				}
				//l.add("Material: " + block.getMaterial().toString());
				l.add("X: " + x + " Y: " + y + " Z: " + z);
				l.add("DX: " + dx + " DY: " + dy + " DZ: " + dz);



				l.add(" ");

				for(String s:l)
				{
					ModHelpers.sendChatMessage(player, s);
				}
			}

			return super.onItemUse(itemstack, player, world, x, y, z,
					data, dx, dy, dz);
		}

	}
	
}
