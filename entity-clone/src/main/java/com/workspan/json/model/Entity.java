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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + entityId;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (entityId != other.entityId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
