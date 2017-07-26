package com.stackroute.swisit.nlp.domain;
/*to get the concepts of concept graph*/
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Concept {
	@GraphId
	private Long id;
	String name;
	String nodeid;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	@Override
	public String toString() {
		return "Concept [id=" + id + ", name=" + name + ", nodeid=" + nodeid + "]";
	}
	public Concept(String name, String nodeid) {
		super();
		this.name = name;
		this.nodeid = nodeid;
	}
	public Concept() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
