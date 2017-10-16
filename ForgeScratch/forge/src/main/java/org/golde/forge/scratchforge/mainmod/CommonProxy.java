package org.golde.forge.scratchforge.mainmod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.golde.forge.scratchforge.base.common.item.ItemBase;
import org.golde.forge.scratchforge.base.helpers.ModHelpers;
import org.golde.forge.scratchforge.base.helpers.PLog;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		new DebugItem();
	}
	
	public void init(FMLInitializationEvent event) {
		
	}
	
	/*public void serverPreInit(FMLServerAboutToStartEvent event) {
		
	}*/
	
	public void serverInit(FMLServerStartingEvent event) {
		MinecraftServer server = event.getServer();
		if(!server.isDedicatedServer()) {
			server.setOnlineMode(false);
			((IntegratedServer)server).setMOTD("Cool MOTD");
		}
	}
	
	class DebugItem extends ItemBase{

		public DebugItem() {
			super(ForgeModScratchForge.BLOCK_ID, ForgeModScratchForge.CREATIVE_TAB, "Debugger", 1);
		}
		
		private List<String> getSubBlocks(Block block) {
			List<ItemStack> items = new ArrayList<ItemStack>();
			block.getSubBlocks(Item.getItemFromBlock(block), null, items);
			List<String> names = new ArrayList<String>();
			for(ItemStack i:items) {
				names.add("§6"+i.getDisplayName());
			}
			
			return names;
		}
		
		private String fixColorCoding(String raw) {
			String[] charToReplace = {",", "[", "]", ":", "(", ")"};
			for(String to:charToReplace) {
				raw = raw.replace(to, "§f" + to);
			}
			return raw;
		}

		@Override
		public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world,
				int x, int y, int z, int data, float dx,
				float dy, float dz) {

			if(world.isRemote) {
				List<String> l = new ArrayList<String>();

				Block block = world.getBlock(x, y, z);

				if(block != null) {
					l.add(fixColorCoding("§bBlock§f: §a" + block.getLocalizedName() + "§f (§6" + block.getUnlocalizedName() + "§f, §6" + Block.getIdFromBlock(block) + "§f)"));
					l.add(fixColorCoding("§bSide§f: §a" + data));
					
					l.add(fixColorCoding("§bSub: [" + Arrays.asList(getSubBlocks(block)).toString().replace(",", ",§6").replace("]", "").replace("[", "") + "]"));
				}
				//l.add("Material: " + block.getMaterial().toString());
				l.add(fixColorCoding("§bX: §a" + x + " §bY: §a" + y + " §bZ: §a" + z));
				l.add(fixColorCoding("§bDX: §a" + dx + " §bDY: §a" + dy + " §bDZ: §a" + dz));
				
				

				l.add(" ");

				for(String s:l)
				{
					if(player != null) {player.addChatMessage(new ChatComponentText(s));}
				}
			}

			return super.onItemUse(itemstack, player, world, x, y, z,
					data, dx, dy, dz);
		}

	}
	
}
