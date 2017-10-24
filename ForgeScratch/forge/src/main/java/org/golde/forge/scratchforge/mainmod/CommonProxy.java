package org.golde.forge.scratchforge.mainmod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.golde.forge.scratchforge.base.common.item.ItemBase;
import org.golde.forge.scratchforge.base.helpers.ModHelpers;
import org.golde.forge.scratchforge.base.helpers.PLog;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		new DebugItem();
	}
	
	public void init(FMLInitializationEvent event) {
		
	}
	
	/*public void serverPreInit(FMLServerAboutToStartEvent event) {
		
	}*/
	
	public void serverInit(FMLServerStartingEvent event) {
		if(!event.getServer().isDedicatedServer() && event.getServer() instanceof IntegratedServer) {
			IntegratedServer server = (IntegratedServer)event.getServer();
			server.setOnlineMode(false);
			//server.setMOTD("Cool MOTD");
			
		}
	}
	
	class DebugItem extends ItemBase{

		public DebugItem() {
			super(ForgeModScratchForge.BLOCK_ID, ForgeModScratchForge.CREATIVE_TAB, "Debugger", 1);
		}
		
		private List<String> getSubBlocks(Block block) {
			List<ItemStack> items = new ArrayList<ItemStack>();
			//block.getSubBlocks(Item.getItemFromBlock(block), null, items);
			List<String> names = new ArrayList<String>();
			for(ItemStack i:items) {
				names.add("\u00a76"+i.getDisplayName());
			}
			
			return names;
		}
		
		private String fixColorCoding(String raw) {
			String[] charToReplace = {",", "[", "]", ":", "(", ")"};
			for(String to:charToReplace) {
				raw = raw.replace(to, "\u00a7f" + to);
			}
			return raw;
		}

		@Override
		public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float dx, float dy, float dz) {

			if(world.isRemote) {
				List<String> l = new ArrayList<String>();
				IBlockState blockState = world.getBlockState(pos);
				Block block = blockState.getBlock();

				if(block != null) {
					l.add(fixColorCoding("\u00a7bBlock\u00a7f: \u00a7a" + block.getLocalizedName() + "\u00a7f (\u00a76" + block.getUnlocalizedName() + "\u00a7f, \u00a76" + Block.getIdFromBlock(block) + "\u00a7f)"));
					//l.add(fixColorCoding("\u00a7bSide\u00a7f: \u00a7a" + data));
					
					l.add(fixColorCoding("\u00a7bSub: [" + Arrays.asList(getSubBlocks(block)).toString().replace(",", ",\u00a76").replace("]", "").replace("[", "") + "]"));
				}
				//l.add("Material: " + block.getMaterial().toString());
				l.add(fixColorCoding("\u00a7bX: \u00a7a" + pos.getX() + " \u00a7bY: \u00a7a" + pos.getY() + " \u00a7bZ: \u00a7a" + pos.getZ()));
				l.add(fixColorCoding("\u00a7bDX: \u00a7a" + dx + " \u00a7bDY: \u00a7a" + dy + " \u00a7bDZ: \u00a7a" + dz));
				
				

				l.add(" ");

				for(String s:l)
				{
					if(player != null) {player.sendMessage(new TextComponentString(s));}
				}
			}

			return super.onItemUse(player, world, pos, hand, facing, dx, dy, dz);
		}

	}
	
}
