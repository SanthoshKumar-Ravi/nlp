package com.stackroute.swisit.nlp.controllers;
import com.stackroute.swisit.nlp.domain.Nlp;
import com.stackroute.swisit.nlp.domain.NlpOutput;
import com.stackroute.swisit.nlp.linkassembler.LinkAssembler;
import com.stackroute.swisit.nlp.service.NlpService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.neo4j.driver.v1.*;
import org.neo4j.unsafe.impl.batchimport.input.Input;

import static org.neo4j.driver.v1.Values.parameters;

@CrossOrigin
@Api(value="Nlpsearcher", description="Operations pertaining to the Nlp searcher App")
@RestController
@RequestMapping("v1/api/nlp")
public class NlpRestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	NlpService nlpService;

	@Autowired
	LinkAssembler linkAssembler;

	// get method to get the input from user

	@ApiOperation(value = "Search a text with an Textword",response = Nlp.class)
	@CrossOrigin
	@RequestMapping(value = "/{textword}", method= RequestMethod.GET)
	public List getNlp(@PathVariable String textword){
		List<String> Intent = new ArrayList<String>();
		logger.debug("Get Nlp with Textword "+textword);
		List<NlpOutput> final_nlp=new ArrayList<>();
		System.out.println("in controller "+textword);
		List<String> list = nlpService.stopWords(textword);
		List<NlpOutput> nlpOutput = nlpService.getOutput(list);
		return nlpOutput;
	}}	
		

