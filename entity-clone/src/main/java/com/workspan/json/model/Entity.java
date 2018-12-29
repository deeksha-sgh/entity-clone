package com.workspan.json.model;


import org.apache.log4j.Logger;

import com.google.gson.annotations.SerializedName;
/**
 * Modeled from JSON object. Holds entity information.
 * Implements cloneable to use the clone method of the object class.
 * 
 * 
 * @author deekshasingh
 *
 */
public class Entity implements Cloneable{
	
	private static final Logger logger = Logger.getLogger(Entity.class);
	@SerializedName("entity_id")
	private int entityId;
	private String name;
	private String description;
	public int getEntityId() {
		return entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public Entity clone() {
		Entity clone = null;
        try{
            clone = (Entity) super.clone();
         
        }catch(CloneNotSupportedException cns){
            logger.error("Error while cloning Entity", cns);
        }
        return clone;
	}

}
