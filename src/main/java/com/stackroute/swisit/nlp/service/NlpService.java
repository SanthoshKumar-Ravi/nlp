package com.stackroute.swisit.nlp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stackroute.swisit.nlp.domain.NlpOutput;


@Service
public interface NlpService {
	
	
	public String getNlpBytextword(String Textword);
	public List<String> stopWords(String text) throws IllegalArgumentException;
	public List<NlpOutput> getOutput(List<String> list);

}
