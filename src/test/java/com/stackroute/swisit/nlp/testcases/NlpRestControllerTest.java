package com.stackroute.swisit.nlp.testcases;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.swisit.nlp.controllers.NlpRestController;
import com.stackroute.swisit.nlp.domain.BaseIntent;
import com.stackroute.swisit.nlp.domain.Nlp;
import com.stackroute.swisit.nlp.linkassembler.LinkAssembler;
import com.stackroute.swisit.nlp.repository.NlpRepository;
import com.stackroute.swisit.nlp.service.NlpService;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = NlpRestController.class)


public class NlpRestControllerTest {
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
    public void getNlp() throws Exception {
 
       String textword = "textword";
      
        mockMvc.perform(get("/v1/api/nlp/textword"))
                .andExpect(status().isOk());
         
    }


}