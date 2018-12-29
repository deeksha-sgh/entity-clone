package com.workspan.entity;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.workspan.entity.model.EntityNode;
import com.workspan.json.model.Entity;
import com.workspan.json.model.EntityGraph;
import com.workspan.json.model.Link;

/**
 * 
 * This class handles the mapping of entity to Id, 
 * creating clones with new Ids and updating the links.
 * @author deekshasingh
 *
 */
public class EntityManager {
	private static final Logger logger = Logger.getLogger(EntityManager.class);
	
	private Map<Integer, EntityNode> entityMap = new HashMap<>();
	private Map<Integer, Boolean> visited = new HashMap<>();
	private Map<Integer, Integer> cloned = new HashMap<>();
	private Integer maxId = Integer.MIN_VALUE;
	private EntityGraph eg;
	

	public EntityManager(EntityGraph eg) {
		this.eg = eg;
		generateEntityMap();
	}


	/**
	 * Method checks whether the Entity is present or not.
	 * Generates clone of Initial Entity.
	 * Creates clones of Adjacent Entities.
	 * Updates link for the entity reaching the initial entity.
	 * 
	 * @param id: Initial Entity id which is to be cloned.
	 */
	public void createClones(int id) {

		if(!entityMap.containsKey(id)) {
			logger.error("No Entity present with the id "+ id);
			return;
		}
		// Generate clone of the initial entity
		generateClone(id);
		// Generate clone for adjacent nodes
		createAdjacentClones(id);
		// Add clone of initial entity to the links 
		updateLinkToCloneEntity(id);	
	}


	/**
	 * Adds link for any entities that link to initial entity to now link to cloned entity.
	 * Add link in the {@link EntityGraph}
	 * @param id: Initial Entity id which was cloned
	 */
	private void updateLinkToCloneEntity(int id) {
		for(Integer i : visited.keySet()) {
			if(entityMap.get(i).getAjacentEntity().contains(id)) {
				eg.getLinks().add(new Link(i, cloned.get(id)));
			}
		}
	}
	
	
	/**
	 * Marks the nodes visited and creates clones of the adjacent nodes in DFS manner.
	 * Creates a clone of the adjacent node.
	 * @param id: Entity Id whose adjacent are to be cloned.
	 */
	private void createAdjacentClones(int id) {
		visited.put(id, true);
		for(Integer ajacentNodeId : entityMap.get(id).getAjacentEntity()) {
			if(!visited.get(ajacentNodeId)) {
				//generate a clone for Adjacent
				generateClone(ajacentNodeId);
				createNodeLink(id, ajacentNodeId);
				createAdjacentClones(ajacentNodeId);
			} else {
				createNodeLink(id, ajacentNodeId);
			}
		}		
		
	}


	/**

	 * Creates a link from the cloned initial node to cloned adjacent node.
	 * @param id: from entity id.
	 * @param ajacentNodeId : to Entity id.
	 */
	private void createNodeLink(int id, Integer ajacentNodeId) {		
		// initial cloned entity and adjacent cloned entity are present in cloned map; create a link
		eg.getLinks().add(new Link(cloned.get(id), cloned.get(ajacentNodeId)));	
		
	}


	/**
	 * Create a clone of the entity. 
	 * Updates the max Id and sets the entity id of the cloned entity.
	 * Adds the cloned entity against the original in map.
	 * Adds the cloned entity in the {@link EntityGraph} for final result.
	 * 
	 * @param id: Entity id which is to be cloned
	 */
	private void generateClone(int id) {
		Entity clonedEntity = entityMap.get(id).getEntity().clone();
		setMaxId(maxId+1);
		clonedEntity.setEntityId(getMaxId());
		cloned.put(id, clonedEntity.getEntityId());
		eg.getEntities().add(clonedEntity);
	}


	/**
	 * Method prepares Entity Map from Entity Graph.
	 * It also creates link for the entities.
	 */
	public void generateEntityMap() {	
		fillEntityMap();
		prepareLinks();	
	}
	
	/**
	 * Takes the links from the {@link EntityGraph} and adds it the adjacent list of entity.
	 * 
	 */
	private void prepareLinks() {
		for(Link link : eg.getLinks()) {
			if(entityMap.containsKey(link.getFrom())) {
				entityMap.get(link.getFrom()).getAjacentEntity().add(link.getTo());
			}
		}	
	}

	/**
	 * Prepares the Entity Id to Entity Map for easy access.
	 * Adds entity to the visited map with initial status as false.
	 * Fetches the max Id.
	 */
	private void fillEntityMap() {
		for(Entity entity : eg.getEntities()) {
			entityMap.put(entity.getEntityId(), new EntityNode(entity));
			visited.put(entity.getEntityId(), false);
			setMaxId(Math.max(maxId, entity.getEntityId()));	
		}
	}


	public int getMaxId() {
		return maxId;
	}


	public void setMaxId(int maxId) {
		this.maxId = maxId;
	}

	public EntityGraph getEg() {
		return eg;
	}

	public void setEg(EntityGraph eg) {
		this.eg = eg;
	}


	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}
	
}
