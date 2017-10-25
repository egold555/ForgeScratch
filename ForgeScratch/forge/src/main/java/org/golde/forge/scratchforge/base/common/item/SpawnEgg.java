package org.golde.forge.scratchforge.base.common.item;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//Based off Mojang's spawn egg class
//Modified to work for my needs
public class SpawnEgg extends ItemBase{

	public HashMap entityEggs = new LinkedHashMap();

	//Need to change name from Spawn Egg to Spawn for language change
	public SpawnEgg(String BLOCK_ID, CreativeTabs CREATIVE_TAB) {
		super(BLOCK_ID, CREATIVE_TAB, "Spawn Egg", 64);
		setHasSubtypes(true);
	}


	  public String getItemStackDisplayName(ItemStack p_getItemStackDisplayName_1_)
	  {
	    String s = ("" + I18n.translateToLocal(new StringBuilder().append(getUnlocalizedName()).append(".name").toString())).trim();
	    String s1 = EntityList.getTranslationName(getNamedIdFrom(p_getItemStackDisplayName_1_));
	    if (s1 != null) {
	      s = s + " " + I18n.translateToLocal(new StringBuilder().append("entity.").append(s1).append(".name").toString());
	    }
	    return s;
	  }

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_)
	{
		EntityList.EntityEggInfo entityegginfo = (EntityList.EntityEggInfo)entityEggs.get(Integer.valueOf(p_82790_1_.getItemDamage()));
		return entityegginfo != null ? (p_82790_2_ == 0 ? entityegginfo.primaryColor : entityegginfo.secondaryColor) : 16777215;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	public EnumActionResult onItemUse(EntityPlayer p_onItemUse_1_, World p_onItemUse_2_, BlockPos p_onItemUse_3_, EnumHand p_onItemUse_4_, EnumFacing p_onItemUse_5_, float p_onItemUse_6_, float p_onItemUse_7_, float p_onItemUse_8_)
	  {
	    ItemStack itemstack = p_onItemUse_1_.getHeldItem(p_onItemUse_4_);
	    if (p_onItemUse_2_.isRemote) {
	      return EnumActionResult.SUCCESS;
	    }
	    if (!p_onItemUse_1_.canPlayerEdit(p_onItemUse_3_.offset(p_onItemUse_5_), p_onItemUse_5_, itemstack)) {
	      return EnumActionResult.FAIL;
	    }
	    IBlockState iblockstate = p_onItemUse_2_.getBlockState(p_onItemUse_3_);
	    Block block = iblockstate.getBlock();
	    if (block == Blocks.MOB_SPAWNER)
	    {
	      TileEntity tileentity = p_onItemUse_2_.getTileEntity(p_onItemUse_3_);
	      if ((tileentity instanceof TileEntityMobSpawner))
	      {
	        MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
	        mobspawnerbaselogic.setEntityId(getNamedIdFrom(itemstack));
	        tileentity.markDirty();
	        p_onItemUse_2_.notifyBlockUpdate(p_onItemUse_3_, iblockstate, iblockstate, 3);
	        if (!p_onItemUse_1_.capabilities.isCreativeMode) {
	          itemstack.shrink(1);
	        }
	        return EnumActionResult.SUCCESS;
	      }
	    }
	    BlockPos blockpos = p_onItemUse_3_.offset(p_onItemUse_5_);
	    double d0 = getYOffset(p_onItemUse_2_, blockpos);
	    Entity entity = spawnCreature(p_onItemUse_2_, getNamedIdFrom(itemstack), blockpos.getX() + 0.5D, blockpos.getY() + d0, blockpos.getZ() + 0.5D);
	    if (entity != null)
	    {
	      if (((entity instanceof EntityLivingBase)) && (itemstack.hasDisplayName())) {
	        entity.setCustomNameTag(itemstack.getDisplayName());
	      }
	      applyItemEntityDataToEntity(p_onItemUse_2_, p_onItemUse_1_, itemstack, entity);
	      if (!p_onItemUse_1_.capabilities.isCreativeMode) {
	        itemstack.shrink(1);
	      }
	    }
	    return EnumActionResult.SUCCESS;
	  }

	protected double getYOffset(World p_getYOffset_1_, BlockPos p_getYOffset_2_)
	  {
	    AxisAlignedBB axisalignedbb = new AxisAlignedBB(p_getYOffset_2_).expand(0.0D, -1.0D, 0.0D);
	    List<AxisAlignedBB> list = p_getYOffset_1_.getCollisionBoxes((Entity)null, axisalignedbb);
	    if (list.isEmpty()) {
	      return 0.0D;
	    }
	    double d0 = axisalignedbb.minY;
	    for (AxisAlignedBB axisalignedbb1 : list) {
	      d0 = Math.max(axisalignedbb1.maxY, d0);
	    }
	    return d0 - p_getYOffset_2_.getY();
	  }
	  
	  public static void applyItemEntityDataToEntity(World p_applyItemEntityDataToEntity_0_, @Nullable EntityPlayer p_applyItemEntityDataToEntity_1_, ItemStack p_applyItemEntityDataToEntity_2_, @Nullable Entity p_applyItemEntityDataToEntity_3_)
	  {
	    MinecraftServer minecraftserver = p_applyItemEntityDataToEntity_0_.getMinecraftServer();
	    if ((minecraftserver != null) && (p_applyItemEntityDataToEntity_3_ != null))
	    {
	      NBTTagCompound nbttagcompound = p_applyItemEntityDataToEntity_2_.getTagCompound();
	      if ((nbttagcompound != null) && (nbttagcompound.hasKey("EntityTag", 10)))
	      {
	        if ((!p_applyItemEntityDataToEntity_0_.isRemote) && (p_applyItemEntityDataToEntity_3_.ignoreItemEntityData()) && ((p_applyItemEntityDataToEntity_1_ == null) || (!minecraftserver.getPlayerList().canSendCommands(p_applyItemEntityDataToEntity_1_.getGameProfile())))) {
	          return;
	        }
	        NBTTagCompound nbttagcompound1 = p_applyItemEntityDataToEntity_3_.writeToNBT(new NBTTagCompound());
	        UUID uuid = p_applyItemEntityDataToEntity_3_.getUniqueID();
	        nbttagcompound1.merge(nbttagcompound.getCompoundTag("EntityTag"));
	        p_applyItemEntityDataToEntity_3_.setUniqueId(uuid);
	        p_applyItemEntityDataToEntity_3_.readFromNBT(nbttagcompound1);
	      }
	    }
	  }
	  
	  public ActionResult<ItemStack> onItemRightClick(World p_onItemRightClick_1_, EntityPlayer p_onItemRightClick_2_, EnumHand p_onItemRightClick_3_)
	  {
	    ItemStack itemstack = p_onItemRightClick_2_.getHeldItem(p_onItemRightClick_3_);
	    if (p_onItemRightClick_1_.isRemote) {
	      return new ActionResult(EnumActionResult.PASS, itemstack);
	    }
	    RayTraceResult raytraceresult = rayTrace(p_onItemRightClick_1_, p_onItemRightClick_2_, true);
	    if ((raytraceresult != null) && (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK))
	    {
	      BlockPos blockpos = raytraceresult.getBlockPos();
	      if (!(p_onItemRightClick_1_.getBlockState(blockpos).getBlock() instanceof BlockLiquid)) {
	        return new ActionResult(EnumActionResult.PASS, itemstack);
	      }
	      if ((p_onItemRightClick_1_.isBlockModifiable(p_onItemRightClick_2_, blockpos)) && (p_onItemRightClick_2_.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack)))
	      {
	        Entity entity = spawnCreature(p_onItemRightClick_1_, getNamedIdFrom(itemstack), blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
	        if (entity == null) {
	          return new ActionResult(EnumActionResult.PASS, itemstack);
	        }
	        if (((entity instanceof EntityLivingBase)) && (itemstack.hasDisplayName())) {
	          entity.setCustomNameTag(itemstack.getDisplayName());
	        }
	        applyItemEntityDataToEntity(p_onItemRightClick_1_, p_onItemRightClick_2_, itemstack, entity);
	        if (!p_onItemRightClick_2_.capabilities.isCreativeMode) {
	          itemstack.shrink(1);
	        }
	        p_onItemRightClick_2_.addStat(StatList.getObjectUseStats(this));
	        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
	      }
	      return new ActionResult(EnumActionResult.FAIL, itemstack);
	    }
	    return new ActionResult(EnumActionResult.PASS, itemstack);
	  }
	  
	  @Nullable
	  public static Entity spawnCreature(World p_spawnCreature_0_, @Nullable ResourceLocation p_spawnCreature_1_, double p_spawnCreature_2_, double p_spawnCreature_4_, double p_spawnCreature_6_)
	  {
	    ;
	    ;
	    if ((p_spawnCreature_1_ != null) && (EntityList.ENTITY_EGGS.containsKey(p_spawnCreature_1_)))
	    {
	      Entity entity = null;
	      for (int i = 0; i < 1; i++)
	      {
	        entity = EntityList.createEntityByIDFromName(p_spawnCreature_1_, p_spawnCreature_0_);
	        if ((entity instanceof EntityLiving))
	        {
	          EntityLiving entityliving = (EntityLiving)entity;
	          entity.setLocationAndAngles(p_spawnCreature_2_, p_spawnCreature_4_, p_spawnCreature_6_, MathHelper.wrapDegrees(p_spawnCreature_0_.rand.nextFloat() * 360.0F), 0.0F);
	          entityliving.rotationYawHead = entityliving.rotationYaw;
	          entityliving.renderYawOffset = entityliving.rotationYaw;
	          entityliving.onInitialSpawn(p_spawnCreature_0_.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData)null);
	          p_spawnCreature_0_.spawnEntity(entity);
	          entityliving.playLivingSound();
	        }
	      }
	      return entity;
	    }
	    return null;
	  }
	  
	  public void getSubItems(CreativeTabs p_getSubItems_1_, NonNullList<ItemStack> p_getSubItems_2_)
	  {
	    if (isInCreativeTab(p_getSubItems_1_)) {
	      for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.ENTITY_EGGS.values())
	      {
	        ItemStack itemstack = new ItemStack(this, 1);
	        applyEntityIdToItemStack(itemstack, entitylist$entityegginfo.spawnedID);
	        p_getSubItems_2_.add(itemstack);
	      }
	    }
	  }
	  
	  public static void applyEntityIdToItemStack(ItemStack p_applyEntityIdToItemStack_0_, ResourceLocation p_applyEntityIdToItemStack_1_)
	  {
	    NBTTagCompound nbttagcompound = p_applyEntityIdToItemStack_0_.hasTagCompound() ? p_applyEntityIdToItemStack_0_.getTagCompound() : new NBTTagCompound();
	    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	    nbttagcompound1.setString("id", p_applyEntityIdToItemStack_1_.toString());
	    nbttagcompound.setTag("EntityTag", nbttagcompound1);
	    p_applyEntityIdToItemStack_0_.setTagCompound(nbttagcompound);
	  }
	  
	  @Nullable
	  public static ResourceLocation getNamedIdFrom(ItemStack p_getNamedIdFrom_0_)
	  {
	    NBTTagCompound nbttagcompound = p_getNamedIdFrom_0_.getTagCompound();
	    if (nbttagcompound == null) {
	      return null;
	    }
	    if (!nbttagcompound.hasKey("EntityTag", 10)) {
	      return null;
	    }
	    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("EntityTag");
	    if (!nbttagcompound1.hasKey("id", 8)) {
	      return null;
	    }
	    String s = nbttagcompound1.getString("id");
	    ResourceLocation resourcelocation = new ResourceLocation(s);
	    if (!s.contains(":")) {
	      nbttagcompound1.setString("id", resourcelocation.toString());
	    }
	    return resourcelocation;
	  }

}
