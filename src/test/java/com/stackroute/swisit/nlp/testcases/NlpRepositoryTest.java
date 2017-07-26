package com.stackroute.swisit.nlp.testcases;

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
public class NlpRepositoryTest {
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
	public void findBaseIntents()
	{
		BaseIntent  baseIntentList = new BaseIntent();
		baseIntentList.setName("what");
		baseIntentList.setNode_id("69");
		nlpRepository.save(baseIntentList);
		List<BaseIntent> findBaseIntents = nlpRepository.findBaseIntents("what");
		Assert.assertEquals("what",baseIntentList.getName());
		Assert.assertEquals("69", baseIntentList.getNode_id());
	
	}
}
