package com.workspan.entity.model;

import java.util.HashSet;
import java.util.Set;

import com.workspan.json.model.Entity;

/**
 * 
 * Contains info about the entity and its reachable node.
 * @author deekshasingh
 *
 */
public class EntityNode {

	private Entity entity;
	private Set<Integer> ajacentEntity = new HashSet<Integer>();
	public EntityNode(Entity entity) {
		super();
		this.entity = entity;
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	public Set<Integer> getAjacentEntity() {
		return ajacentEntity;
	}
	public void setAjacentEntity(Set<Integer> ajacentEntity) {
		this.ajacentEntity = ajacentEntity;
	}
	
	
}
