package org.golde.forge.scratchforge.base.common.block;

import java.util.List;
import java.util.Random;

import org.golde.forge.scratchforge.base.helpers.JavaHelpers;
import org.golde.forge.scratchforge.base.helpers.ModHelpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBaseFlower extends BlockFlower{

	private String texture;
	
	public BlockBaseFlower(String MOD_ID, CreativeTabs creatibeTab, String rawName) {
		super();
		String name = JavaHelpers.makeJavaId(rawName);
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setHardness(0.01F);
        this.setResistance(2.0F);
        setCreativeTab(creatibeTab);

        GameRegistry.register(this);
		ItemBlock item = new ItemBlock(this);
		item.setRegistryName(this.getRegistryName());
		GameRegistry.register(item);
	}
	
	public void registerRender() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		for (int i = 0; i < 1; ++i) {
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
		}
	}

	public int quantityDropped(Random par1Random) {
		return 1;
	}

	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public EnumFlowerColor getBlockType() { //Never gets called anywhere so I am returning null
		return null;
	}
	
}
