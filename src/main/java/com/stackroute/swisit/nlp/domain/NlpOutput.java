package com.stackroute.swisit.nlp.domain;

public class NlpOutput {
private String url;
private String title;
private String desc;
private Float confidencescore;
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public Float getConfidencescore() {
	return confidencescore;
}
public void setConfidencescore(Float confidencescore) {
	this.confidencescore = confidencescore;
}

}
