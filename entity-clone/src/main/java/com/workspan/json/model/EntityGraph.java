package com.workspan.json.model;

import java.util.List;

public class EntityGraph {

	private List<Entity> entities;
	private List<Link> links;
	public List<Entity> getEntities() {
		return entities;
	}
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
