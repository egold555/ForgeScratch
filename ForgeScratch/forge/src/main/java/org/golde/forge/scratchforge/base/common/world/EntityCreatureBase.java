package org.golde.forge.scratchforge.base.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityCreatureBase extends EntityCreature{

	public EntityCreatureBase(World p_i1602_1_) {
		super(p_i1602_1_);
	}

	public VariableHolder onDamageFire(World world, VariableHolder variableHolder) {
		
		return variableHolder;
	}
	
	public VariableHolder onDamageExplosion(World world, VariableHolder variableHolder) {
		
		return variableHolder;
	}
	
	public VariableHolder onDamagePotion(World world, VariableHolder variableHolder) {
		
		return variableHolder;
	}
	
	public VariableHolder onDamageArrow(World world, VariableHolder variableHolder) {
		
		return variableHolder;
	}
	
	public VariableHolder onDamagePlayer(World world, VariableHolder variableHolder, EntityPlayer player) {
		
		return variableHolder;
	}
	
	public VariableHolder onDamageEntity(World world, VariableHolder variableHolder) {
		
		return variableHolder;
	}
	
	public VariableHolder onDamageOther(World world, VariableHolder variableHolder) {
		
		return variableHolder;
	}
	
	

	//Attack entity event
	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {

		if(this.worldObj.isRemote) {return super.attackEntityFrom(source, damage);}
		
		final VariableHolder variableHolder = new VariableHolder();
		variableHolder.entity = this;
		variableHolder.damage = damage;
		
		if(source.isFireDamage()) {
			//Fire damage
			onDamageFire(this.worldObj, variableHolder);
		}
		else if(source.isExplosion()) {
			//explosion event
			onDamageExplosion(this.worldObj, variableHolder);
		}
		else if(source.isMagicDamage()) {
			//Potion damage
			onDamagePotion(this.worldObj, variableHolder);
		}
		else if(source.isProjectile()) {
			//Arrows
			onDamageArrow(this.worldObj, variableHolder);
		}
		else {
			Entity damager = source.getEntity();
			if(damager != null) {

				if(damager instanceof EntityPlayer) {
					//Player damaged
					onDamagePlayer(this.worldObj, variableHolder, (EntityPlayer)damager);
				}
				else {
					//Entity did damage (wolf)
					onDamageEntity(this.worldObj, variableHolder);
				}


			}
			else {
				//Fall damage or other
				onDamageOther(this.worldObj, variableHolder);
			}
		}


		return super.attackEntityFrom(source, variableHolder.damage);
	}
	
}
