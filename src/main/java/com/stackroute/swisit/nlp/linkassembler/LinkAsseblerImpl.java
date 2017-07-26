package com.stackroute.swisit.nlp.linkassembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.stackroute.swisit.nlp.controllers.NlpRestController;
import com.stackroute.swisit.nlp.domain.Nlp;
@Component
public class LinkAsseblerImpl implements LinkAssembler {

	

	public List getLinkText(String[] nlp) {
		Link allLink= linkTo(NlpRestController.class).slash("").withRel("all");
		Link selfLink= linkTo(NlpRestController.class).slash("").withSelfRel();
		
		List list = new ArrayList();
		list.add(allLink);
		list.add(selfLink);
		return list;
		

	}

	@Override
	public List getLinkText(Nlp nlp) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	
	
	
}
