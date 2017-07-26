package com.stackroute.swisit.nlp.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.stackroute.swisit.nlp.controllers.NlpRestController;
import com.stackroute.swisit.nlp.domain.BaseIntent;
import com.stackroute.swisit.nlp.domain.Nlp;
import com.stackroute.swisit.nlp.linkassembler.LinkAssembler;
import com.stackroute.swisit.nlp.repository.NlpRepository;
import com.stackroute.swisit.nlp.service.NlpService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = NlpRestController.class)
public class NlpServiceTest {
	@Autowired
    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private NlpService nlpService ;
    
    @MockBean
    private Nlp nlp;
    
    @MockBean
    private BaseIntent baseIntent;
    
    @MockBean
    private NlpRepository nlpRepository;
    
    @InjectMocks
    private NlpRestController nlpRestController;
    
    
    @MockBean
    LinkAssembler linkAssembler;
    
    @Test
    public void  stopWords()
    {
    	String sample="what is java";
    	List<String> words = Arrays.asList("what","java");
    	System.out.println(words);
    	Assert.assertTrue(words.contains("what"));
    	Assert.assertTrue(words.contains("java"));
    }

}
