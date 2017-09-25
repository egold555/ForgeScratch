package org.golde.forge.scratchforge.base.common.world;

import java.util.HashMap;

import net.minecraft.entity.EntityLiving;

public class EntityIdList {

	//Max ids we should have (if used correctly 100 should be plenty)
	private final int MAX_IDS = 100;
	
	//      ID       isBeingUsed
	private HashMap<Integer, Boolean> usingId = new HashMap<Integer, Boolean>();
	
	//Entities for Id
	private EntityLiving[] entities = new EntityLiving[MAX_IDS];
	
	public EntityIdList() {
		//Start off everything as an empty list
		for(int i = 0; i < MAX_IDS; i++) {
			entities[i] = null;
			usingId.put(i, false);
		}
	}
	
	//Returns next id that is false
	public int getNextId() {
		for(int i = 0; i < MAX_IDS; i++) {
			if(!usingId.get(i)) {
				return i;
			}
		}
		return -1;
	}
	
	//get the entity from the id and mark it as were using it
	public EntityLiving getEntity(int id) {
		return entities[id];
	}
	
	public void setEntity(int id, EntityLiving entity) {
		usingId.put(id, true);
		entities[id] = entity;
	}
	
	//mark that were finished with the id
	public void finished(int id) {
		entities[id] = null;
		usingId.put(id, false);
	}
	
}
