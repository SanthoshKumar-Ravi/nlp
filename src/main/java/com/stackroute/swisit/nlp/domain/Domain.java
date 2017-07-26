package com.stackroute.swisit.nlp.domain;
/*to get the Domain in domain graph*/
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Domain {
	@GraphId
	private Long id;
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Domain [id=" + id + ", name=" + name + "]";
	}
	public Domain(String name) {
		super();
		this.name = name;
	}
	public Domain() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
