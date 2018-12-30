package com.workspan.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.workspan.json.model.Entity;
import com.workspan.json.model.EntityGraph;
import com.workspan.json.model.Link;

/**
 * This class is used to handle the test cases for {@link EntityManager}
 * 
 * @author deekshasingh
 *
 */
public class EntityManagerTest {

    private EntityGraph eg;
    private EntityManager em;


    @Before
    public void setup(){
    	eg = new EntityGraph();
    	eg.getEntities().add(getEntity("e1", "e1", 1));
    	eg.getEntities().add(getEntity("e2", "e2", 2));
    	eg.getEntities().add(getEntity("e3", "e3", 3));
    	eg.getEntities().add(getEntity("e4", "e4", 4)); // No From links; 2 in links
    	eg.getEntities().add(getEntity("e5", "e5", 5));
    	eg.getEntities().add(getEntity("e6", "e6", 6)); // No links
    	
    	eg.getEntities().add(getEntity("e7", "e7", 7)); // circular link to inital entity
    	eg.getEntities().add(getEntity("e8", "e8", 8));
    	
    	eg.getLinks().add(new Link(1, 2));
    	eg.getLinks().add(new Link(2, 3));
    	eg.getLinks().add(new Link(3, 2));
    	eg.getLinks().add(new Link(3, 4));
    	eg.getLinks().add(new Link(5, 4));
    	
    	eg.getLinks().add(new Link(7, 8));
    	eg.getLinks().add(new Link(8, 7));
    	
    	

        em=new EntityManager(eg);
    }
    
    @Test
    public void testEntityManager() {
    	 Assert.assertEquals(eg.getEntities().size(),em.getEntityMap().size());
    	 Assert.assertEquals(eg.getEntities().size(),em.getVisited().size());
    	
    }

	private Entity getEntity(String disc, String name, int id) {
		Entity e1 = new Entity();
		e1.setDescription(disc);
        e1.setEntityId(id);
        e1.setName(name);
        return e1;
	}

    /**
     * Check for Empty Cloned Map if an unknown id is passed
     */
    @Test
    public void testCreateClonesInvalidEntity(){

        em.createClones(45);
        Assert.assertTrue(em.getCloned().isEmpty());
    }
    
    /**
     * Check for Clones of Entity With No Links
     * No new link should be added
     */
    @Test
    public void testCreateClonesEntityWithNoLinks(){
    	int entityId = 6;
    	int linkSize = em.getEg().getLinks().size();
        em.createClones(entityId);
        Assert.assertTrue(em.getCloned().containsKey(entityId));
        Assert.assertTrue(em.getVisited().get(entityId));
        Entity e1 = getEntity("e6", "e6", em.getCloned().get(entityId));
        Assert.assertTrue(em.getEg().getEntities().contains(e1));
        Assert.assertEquals(linkSize, em.getEg().getLinks().size());
    }
    
    /**
     * Check for Clones of Entity With Only To Links
     * Only Two Links should be added with new Cloned Entity
     * Check the links created for the cloned Id
     */
    @Test
    public void testCreateClonesEntityWithOnlyToLinks(){
    	int entityId = 4;
        em.createClones(entityId);
        Assert.assertTrue(em.getCloned().containsKey(entityId));
        Assert.assertTrue(em.getVisited().get(entityId));
        
        //New Links for Clone Id present 
        int cloneId = em.getCloned().get(entityId);
        List<Link> list = new ArrayList<>();
        list.add(new Link(3, cloneId));
        list.add(new Link(5, cloneId));
        Assert.assertTrue(em.getEg().getLinks().containsAll(list));

    }


    /**
     *Check for cycle in the middle.
     *Two links for the cloned will be created.
     */
    @Test
    public void testCreateClonesWithCycleInMiddle(){

        em.createClones(1);
        // check for Id 2 and 3
        //New Links for Clone Id present 
        int cloneId2 = em.getCloned().get(2);
        int cloneId3 = em.getCloned().get(3);
        List<Link> list = new ArrayList<>();
        list.add(new Link(cloneId2, cloneId3));
        list.add(new Link(cloneId3, cloneId2));
        Assert.assertTrue(em.getEg().getLinks().containsAll(list));
    }

    /**
     * Link back to initial Entity check.
     */
    @Test
    public void testCreateClonesWithLinkBackToInitial(){

        em.createClones(7);
        Assert.assertTrue(em.getCloned().containsKey(7));
        Assert.assertTrue(em.getCloned().containsKey(8));
        int cloneId7 = em.getCloned().get(7);
        int cloneId8 = em.getCloned().get(8);
        List<Link> list = new ArrayList<>();
        list.add(new Link(cloneId7, cloneId8));
        list.add(new Link(cloneId8, cloneId7));
        
        // Link back to Initial Entity should generate a link for TO links
        list.add(new Link(8, cloneId7));      
        Assert.assertTrue(em.getEg().getLinks().containsAll(list));

    }

}
