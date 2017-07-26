package com.stackroute.swisit.nlp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.swisit.nlp.domain.BaseIntent;
import com.stackroute.swisit.nlp.domain.Concept;
import com.stackroute.swisit.nlp.domain.Domain;



	@Repository
	public interface NlpRepository extends GraphRepository<BaseIntent> {
		/*cipherQuery to get the baseintent from intent graph*/
		@Query("match (s:SubIntent{name:{lemma}})-[r]->(b:BaseIntent) return b;")
		public List<BaseIntent> findBaseIntents(String lemma);
		/*cipherQuery to check the concept from concept graph*/
		@Query("match (c:Concept{name:{lemma})-[r]->(d:Domain) return c")
		public List<Domain> findDomain(String lemma);
		/*cipherQuery to match the baseintent from intent graph and concept from concept graph */
		@Query("match (b:BaseIntent)-[r]->(c:concept) return c AS concept ,r AS relation , b AS base ;")
		public List<Concept> findConcept(String lemma);
		
		@Query("match (c:Concept{name:{0}})-[r:Relates{intent:{1}}]-(d:Document) return d.title AS title,d.url AS url,r.description As description,r.confidenceScore AS cs order by r.confidenceScore DESC")
		public List<Map<String,Object>> getOutput(String concept, String intent);
		
}
