package com.stackroute.swisit.nlp.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class BaseIntent {
	
	public BaseIntent() {
		super();
		// TODO Auto-generated constructor stub
	}

	@GraphId
	private Long id;
	
	public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	String node_id;
	String name;

	@Override
	public String toString() {
		return "BaseIntent [id=" + id + ", node_id=" + node_id + ", name=" + name + "]";
	}
	public BaseIntent(String node_id, String name) {
		super();
		this.node_id = node_id;
		this.name = name;
	}
}