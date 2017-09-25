package org.golde.forge.scratchforge.mods.action;

import org.golde.forge.scratchforge.base.common.block.*;
import org.golde.forge.scratchforge.base.common.item.*;
import org.golde.forge.scratchforge.base.common.world.*;
import org.golde.forge.scratchforge.base.helpers.*;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.settings.*;
import net.minecraft.command.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.dispenser.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.rcon.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.profiler.*;
import net.minecraft.server.*;
import net.minecraft.server.dedicated.*;
import net.minecraft.server.gui.*;
import net.minecraft.server.integrated.*;
import net.minecraft.server.management.*;
import net.minecraft.src.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.world.demo.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.storage.*;

import net.minecraftforge.classloading.*;
import net.minecraftforge.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.sound.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.item.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.minecart.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.terraingen.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.oredict.*;
import net.minecraftforge.transformers.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.relauncher.*;

import org.apache.logging.log4j.*;

import java.text.*;
import java.util.*;
import java.lang.*;

import io.netty.buffer.*;
import io.netty.channel.*;

public class CommonProxy {

	/* Block Variables */
	static Mcblock_World___Console mcblock_World___Console;
static Mcblock_World___Time_Dropdown mcblock_World___Time_Dropdown;
static Mcblock_World___Spawn_Mob mcblock_World___Spawn_Mob;
static Mcblock_Player___Potion_Effect mcblock_Player___Potion_Effect;
static Mcblock_Player___Sound mcblock_Player___Sound;
static Mcblock_Player___Give_item mcblock_Player___Give_item;
static Mcblock_Player___Chat_Message mcblock_Player___Chat_Message;
static Mcblock_World___Lightning mcblock_World___Lightning;
static Mcblock_World___Time mcblock_World___Time;
static Mcblock_Entity___Health mcblock_Entity___Health;
static Mcblock_World___Explosion mcblock_World___Explosion;
static Mcblock_Player___Velocity mcblock_Player___Velocity;
static Mcblock_Entity___Potion mcblock_Entity___Potion;
static Mcblock_World___Firework mcblock_World___Firework;
static Mcblock_Player___Set_Health mcblock_Player___Set_Health;
static Mcblock_Player___Teleport mcblock_Player___Teleport;
static Mcblock_World___Spawn_Item mcblock_World___Spawn_Item;
static Mcblock_Entity___Velocity mcblock_Entity___Velocity;
static Mcblock_Entity___Name mcblock_Entity___Name;
static Mcblock_Entity___Teleport mcblock_Entity___Teleport;
static Mcblock_World___Break_Block mcblock_World___Break_Block;
static Mcblock_World___Place_Block mcblock_World___Place_Block;


	/* BlockFlower Variables */
	

	/* BlockPlant Variables */
	

	/* Item Variables */
	public static SFItemMonsterPlacer scItemMonsterPlacer;
	

	/* Entity Variables */
	/*Variables - Entity*/

	public void preInit(FMLPreInitializationEvent event){
		/* Block Constructor Calls */
		mcblock_World___Console = new Mcblock_World___Console();
mcblock_World___Time_Dropdown = new Mcblock_World___Time_Dropdown();
mcblock_World___Spawn_Mob = new Mcblock_World___Spawn_Mob();
mcblock_Player___Potion_Effect = new Mcblock_Player___Potion_Effect();
mcblock_Player___Sound = new Mcblock_Player___Sound();
mcblock_Player___Give_item = new Mcblock_Player___Give_item();
mcblock_Player___Chat_Message = new Mcblock_Player___Chat_Message();
mcblock_World___Lightning = new Mcblock_World___Lightning();
mcblock_World___Time = new Mcblock_World___Time();
mcblock_Entity___Health = new Mcblock_Entity___Health();
mcblock_World___Explosion = new Mcblock_World___Explosion();
mcblock_Player___Velocity = new Mcblock_Player___Velocity();
mcblock_Entity___Potion = new Mcblock_Entity___Potion();
mcblock_World___Firework = new Mcblock_World___Firework();
mcblock_Player___Set_Health = new Mcblock_Player___Set_Health();
mcblock_Player___Teleport = new Mcblock_Player___Teleport();
mcblock_World___Spawn_Item = new Mcblock_World___Spawn_Item();
mcblock_Entity___Velocity = new Mcblock_Entity___Velocity();
mcblock_Entity___Name = new Mcblock_Entity___Name();
mcblock_Entity___Teleport = new Mcblock_Entity___Teleport();
mcblock_World___Break_Block = new Mcblock_World___Break_Block();
mcblock_World___Place_Block = new Mcblock_World___Place_Block();


		/* BlockFlower Constructor Calls */
		

		/* BlockPlant Constructor Calls */
		

		/* Item Constructor Calls */
		scItemMonsterPlacer = new SFItemMonsterPlacer();
		

		/* Entity Constructor Calls */
		
	}

	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		for (int i = 0; i < 20; i++) {
			int x = chunkX + random.nextInt(16) + 8;
			int y = random.nextInt(128);
			int z = chunkZ + random.nextInt(16) + 8;

			/*Overworld world generation for flowers*/
			

			/*Overworld world generation for plants*/
			

		}
	}

	//TODO: Implement nether plant generation?
	public void generateNether(World world, Random random, int chunkX, int chunkZ) {
		for (int i = 0; i < 20; i++) {
			int x = chunkX + random.nextInt(16) + 8;
			int y = random.nextInt(128);
			int z = chunkZ + random.nextInt(16) + 8;

			/*Nether generation for flowers*/
			/*WorldGen - Nether - Flowers*/

			/*Nether generation for plants*/
			/*WorldGen - Nether - Plant*/

		}
	}

	/*
	 * Based off of Minecrafts Spawn Egg Code
	 */
	static class SFItemMonsterPlacer extends ItemBase{
		
		@SideOnly(Side.CLIENT)
		private IIcon theIcon;

		public static HashMap entityEggs = new LinkedHashMap();
		
		public SFItemMonsterPlacer() {
			super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Spawn Egg", 64);
			setHasSubtypes(true);
		}
		
		public String getItemStackDisplayName(ItemStack p_77653_1_)
		{
			String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
			String s1 = EntityList.getStringFromID(p_77653_1_.getItemDamage());

			if (s1 != null)
			{
				s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
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
		public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
		{
			if (p_77648_3_.isRemote)
			{
				return true;
			}
			else
			{
				Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
				p_77648_4_ += Facing.offsetsXForSide[p_77648_7_];
				p_77648_5_ += Facing.offsetsYForSide[p_77648_7_];
				p_77648_6_ += Facing.offsetsZForSide[p_77648_7_];
				double d0 = 0.0D;

				if (p_77648_7_ == 1 && block.getRenderType() == 11)
				{
					d0 = 0.5D;
				}

				Entity entity = spawnCreature(p_77648_3_, p_77648_1_.getItemDamage(), (double)p_77648_4_ + 0.5D, (double)p_77648_5_ + d0, (double)p_77648_6_ + 0.5D);

				if (entity != null)
				{
					if (entity instanceof EntityLivingBase && p_77648_1_.hasDisplayName())
					{
						((EntityLiving)entity).setCustomNameTag(p_77648_1_.getDisplayName());
					}

					if (!p_77648_2_.capabilities.isCreativeMode)
					{
						--p_77648_1_.stackSize;
					}
				}

				return true;
			}
		}

		/**
		 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
		 */
		public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
		{
			if (p_77659_2_.isRemote)
			{
				return p_77659_1_;
			}
			else
			{
				MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, true);

				if (movingobjectposition == null)
				{
					return p_77659_1_;
				}
				else
				{
					if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
					{
						int i = movingobjectposition.blockX;
						int j = movingobjectposition.blockY;
						int k = movingobjectposition.blockZ;

						if (!p_77659_2_.canMineBlock(p_77659_3_, i, j, k))
						{
							return p_77659_1_;
						}

						if (!p_77659_3_.canPlayerEdit(i, j, k, movingobjectposition.sideHit, p_77659_1_))
						{
							return p_77659_1_;
						}

						if (p_77659_2_.getBlock(i, j, k) instanceof BlockLiquid)
						{
							Entity entity = spawnCreature(p_77659_2_, p_77659_1_.getItemDamage(), (double)i, (double)j, (double)k);

							if (entity != null)
							{
								if (entity instanceof EntityLivingBase && p_77659_1_.hasDisplayName())
								{
									((EntityLiving)entity).setCustomNameTag(p_77659_1_.getDisplayName());
								}

								if (!p_77659_3_.capabilities.isCreativeMode)
								{
									--p_77659_1_.stackSize;
								}
							}
						}
					}

					return p_77659_1_;
				}
			}
		}

		/**
		 * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
		 * Parameters: world, entityID, x, y, z.
		 */
		public static Entity spawnCreature(World p_77840_0_, int p_77840_1_, double p_77840_2_, double p_77840_4_, double p_77840_6_)
		{
			if (!entityEggs.containsKey(Integer.valueOf(p_77840_1_)))
			{
				return null;
			}
			else
			{
				Entity entity = null;

				for (int j = 0; j < 1; ++j)
				{
					entity = EntityList.createEntityByID(p_77840_1_, p_77840_0_);

					if (entity != null && entity instanceof EntityLivingBase)
					{
						EntityLiving entityliving = (EntityLiving)entity;
						entity.setLocationAndAngles(p_77840_2_, p_77840_4_, p_77840_6_, MathHelper.wrapAngleTo180_float(p_77840_0_.rand.nextFloat() * 360.0F), 0.0F);
						entityliving.rotationYawHead = entityliving.rotationYaw;
						entityliving.renderYawOffset = entityliving.rotationYaw;
						entityliving.onSpawnWithEgg((IEntityLivingData)null);
						p_77840_0_.spawnEntityInWorld(entity);
						entityliving.playLivingSound();
					}
				}

				return entity;
			}
		}

		@SideOnly(Side.CLIENT)
		public boolean requiresMultipleRenderPasses()
		{
			return true;
		}

		/**
		 * Gets an icon index based on an item's damage value and the given render pass
		 */
		@SideOnly(Side.CLIENT)
		public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
		{
			return p_77618_2_ > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
		}

		/**
		 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
		 */
		@SideOnly(Side.CLIENT)
		public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
		{
			Iterator iterator = entityEggs.values().iterator();

			while (iterator.hasNext())
			{
				EntityList.EntityEggInfo entityegginfo = (EntityList.EntityEggInfo)iterator.next();
				p_150895_3_.add(new ItemStack(p_150895_1_, 1, entityegginfo.spawnedID));
			}
		}

		@SideOnly(Side.CLIENT)
		public void registerIcons(IIconRegister p_94581_1_)
		{
			super.registerIcons(p_94581_1_);
			this.theIcon = p_94581_1_.registerIcon(this.getIconString() + "_overlay");
		}
		
	}
	
	public static void createEntity(Class entityClass, String rawEntityName, String entityName, int solidColor, int spotColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, rawEntityName, id);
		EntityRegistry.registerModEntity(entityClass, rawEntityName, id, ForgeMod.INSTANCE, 64, 1, true);
		if(solidColor != -1 && spotColor != -1) {
			createEgg(id, solidColor, spotColor);
		}
		//TODO: Add language
	}
	
	private static void createEgg(int id, int solidColor, int spotColor) {
		SFItemMonsterPlacer.entityEggs.put(Integer.valueOf(id), new EntityList.EntityEggInfo(id, solidColor, spotColor));
	}

	
/*type:block*/
    public class Mcblock_World___Console extends BlockBase {
        public Mcblock_World___Console() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Console", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){
            PLog.game("Hello!");
        }

            return true;
        }
    }

/*type:block*/
    public class Mcblock_World___Time_Dropdown extends BlockBase {
        public Mcblock_World___Time_Dropdown() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Time Dropdown", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){world.setWorldTime(Math.max(0, (long)0));}
            return true;
        }
    }

/*type:block*/
    public class Mcblock_World___Spawn_Mob extends BlockBase {
        public Mcblock_World___Spawn_Mob() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Spawn Mob", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){entity = ModHelpers.spawnEntityInWorld(world, (x), ((y) + 1), (z), "Creeper");}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Player___Potion_Effect extends BlockBase {
        public Mcblock_Player___Potion_Effect() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Player - Potion Effect", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){if(player != null){ModHelpers.addPotionToEntity(player, 1, 10, 0, true);}}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Player___Sound extends BlockBase {
        public Mcblock_Player___Sound() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Player - Sound", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(player != null){world.playSoundAtEntity(player, "ambient.cave", 1, 1);}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Player___Give_item extends BlockBase {
        public Mcblock_Player___Give_item() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Player - Give item", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){{if(player != null){player.inventory.addItemStackToInventory(new ItemStack(Items.baked_potato));}}}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Player___Chat_Message extends BlockBase {
        public Mcblock_Player___Chat_Message() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Player - Chat Message", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(world.isRemote){
            if(player != null) {player.addChatMessage(new ChatComponentText("A chat message"));}
        }

            return true;
        }
    }

/*type:block*/
    public class Mcblock_World___Lightning extends BlockBase {
        public Mcblock_World___Lightning() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Lightning", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){world.addWeatherEffect((new EntityLightningBolt(world, (x), (y), (z))));}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_World___Time extends BlockBase {
        public Mcblock_World___Time() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Time", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){world.setWorldTime(Math.max(0, (long)0));}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Entity___Health extends BlockBase {
        public Mcblock_Entity___Health() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Entity - Health", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){entity = ModHelpers.spawnEntityInWorld(world, (x), ((y) + 1), (z), "Creeper");}
        entity.setHealth(1);

            return true;
        }
    }

/*type:block*/
    public class Mcblock_World___Explosion extends BlockBase {
        public Mcblock_World___Explosion() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Explosion", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){world.newExplosion((Entity)null, (x) + 0.5f, (y) + 0.5f, (z) + 0.5f, 2, false, true);}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Player___Velocity extends BlockBase {
        public Mcblock_Player___Velocity() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Player - Velocity", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(player != null){player.setVelocity(0, 2, 0);}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Entity___Potion extends BlockBase {
        public Mcblock_Entity___Potion() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Entity - Potion", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){entity = ModHelpers.spawnEntityInWorld(world, (x), ((y) + 1), (z), "Creeper");}
        if(!world.isRemote){if(entity != null) {ModHelpers.addPotionToEntity(entity, 1, 10, 0, true);}}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_World___Firework extends BlockBase {
        public Mcblock_World___Firework() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Firework", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){world.spawnEntityInWorld(ModHelpers.getFirework(world, (x), ((y) + 1), (z), true, true, ("#ffff00"), 0, 0));}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Player___Set_Health extends BlockBase {
        public Mcblock_Player___Set_Health() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Player - Set Health", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                player.setHealth(5);

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Player___Teleport extends BlockBase {
        public Mcblock_Player___Teleport() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Player - Teleport", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(player != null){player.setPositionAndUpdate((player.posX), ((player.posY) + 10), (player.posZ));}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_World___Spawn_Item extends BlockBase {
        public Mcblock_World___Spawn_Item() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Spawn Item", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){
            world.spawnEntityInWorld(new EntityItem(world, (x) + 0.5f, ((y) + 1) + 1, (z) + 0.5f, new ItemStack(Items.chicken)));
        }

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Entity___Velocity extends BlockBase {
        public Mcblock_Entity___Velocity() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Entity - Velocity", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){entity = ModHelpers.spawnEntityInWorld(world, (x), ((y) + 1), (z), "Creeper");}
        if(entity != null){entity.setVelocity(0, 2, 0);}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Entity___Name extends BlockBase {
        public Mcblock_Entity___Name() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Entity - Name", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){entity = ModHelpers.spawnEntityInWorld(world, (x), ((y) + 1), (z), "Creeper");}
        if(entity != null){
            entity.setCustomNameTag("Bob");
            entity.setAlwaysRenderNameTag(true);
        }

            return true;
        }
    }

/*type:block*/
    public class Mcblock_Entity___Teleport extends BlockBase {
        public Mcblock_Entity___Teleport() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "Entity - Teleport", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){entity = ModHelpers.spawnEntityInWorld(world, (x), ((y) + 1), (z), "Creeper");}
        if(entity != null){((EntityLiving)entity).setPositionAndUpdate((entity.posX), ((entity.posY) + 10), (entity.posZ));}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_World___Break_Block extends BlockBase {
        public Mcblock_World___Break_Block() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Break Block", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){world.func_147480_a((int)(x), (int)((y) + 1), (int)(z), true);}

            return true;
        }
    }

/*type:block*/
    public class Mcblock_World___Place_Block extends BlockBase {
        public Mcblock_World___Place_Block() {
            super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB, "World - Place Block", Material.ground);

if(false){
    setHardness(-1.0F);
}
if(true){
    setResistance(6000000.0F);
}
        }

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
             EntityLiving entity = null;
                if(!world.isRemote){world.setBlock((int)(x), (int)((y) + 1), (int)(z), (Blocks.wool), (int)3, 3);}

            return true;
        }
    }


}
