package com.stackroute.swisit.nlp.domain;
/* textword is the input given by the user to search*/
import javax.validation.constraints.NotNull;

//import javax.persistence.Entity;

//import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Nlp extends ResourceSupport {
	@NotNull
	@Id
	private String textword;



	public Nlp(String textword) {
		super();
		this.textword = textword;

	}
	@Override
	public String toString() {
		return "Nlp [textword=" + textword  + "]";
	}
	public Nlp() {}
	public String getTextWord() {
		return textword;
	}
	public void settextword(String textword) {
		this.textword = textword;
	}


}
