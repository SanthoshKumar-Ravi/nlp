package com.stackroute.swisit.nlp.service;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stackroute.swisit.nlp.domain.NlpOutput;
import com.stackroute.swisit.nlp.repository.NlpRepository;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

@Service
public class NlpServiceImpl implements NlpService {

	@Autowired
	NlpRepository nlpRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private StanfordCoreNLP pipeline;
	
	String[] stopword = {"a", "as","to","be", "able","of","in","the","not","no","like", 
            "above", "according", "accordingly", "across", "actually",
            "after", "afterwards", "again", "against", "aint", "all",
            "allow", "allows", "almost", "alone", "along", "already",
            "also", "although", "always", "am", "among", "amongst", "an","search","java","me",
            "and", "another", "any", "anybody", "anyhow", "anyone", "anything",
            "anyway", "anyways", "anywhere", "apart", "appear", "appreciate",
            "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking",
            "associated", "at", "available", "away", "awfully", "be", "became", "because",
            "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being",
            "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both",
            "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes",
            "certain", "certainly", "changes", "clearly", "co", "com", "come",
            "comes", "concerning", "consequently", "consider", "considering", "contain",
            "containing",    "contains","corresponding","could", "couldnt", "course", "currently",
            "definitely", "described", "despite", "did", "didnt", "different", "do", "does",
            "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu",
            "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially",
            "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere",
            "ex", "exactly", "except", "far", "few", "ff", "fifth", "first", "five", "followed",   
            "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further",
            "furthermore", "get", "gets", "getting",   "go", "goes", "going", "gone"
                , "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have",
                "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", 
                "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", 
                "hither", "hopefully", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored",
                "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", 
                "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know",
                "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "when",
                "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", 
                "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", 
                "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new",
                "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", 
                "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", 
                "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", 
                "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", 
                "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", 
                "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", 
                "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", 
                "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes",
                "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", 
                "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats",
                "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", 
                "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this",
                "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", 
                "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", 
                "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very",
                "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went",
                "were", "werent",  "whatever", "whence", "whenever",  "wheres", "whereafter", "whereas", "whereby", "wherein", 
                "whereupon", "wherever", "whether", "which", "while", "whither","works","work", "whos", "whoever", "whole", "whom",
                "whose", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", 
                "wouldnt", "yes", "yet", "you", "youd", "youll", "youre","youve", "your", "yours", "yourself", "yourselves"};

	public String getNlpBytextword(String textword){
		String searcherInput = textword;
		
		List<String> list = stopWords(searcherInput);
		
		
		if(searcherInput.startsWith("[^A-Za-z]"))
		{
			return null;
		}
		else{
			searcherInput.replaceAll("[^a-zA-Z0-9]", " ");
			logger.debug(searcherInput);

			}
		
		return searcherInput;
	}

	public List<String> stopWords(String searcherInput) throws IllegalArgumentException
	{
		
		// create an empty Annotation just with the given text
		logger.debug("this is "+searcherInput); 
		
		// creates a StanfordCoreNLP object
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(searcherInput);
		
		logger.debug("hi i ma inside run "+document);
		// run all annotators on this text
		pipeline.annotate(document);
		
		// these are all the sentences in this document
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		if(sentences.size() > 1)
		{
			System.out.println("Warning: There is more than one sentence.");
		}
		else if(sentences.isEmpty())
		{
			throw new IllegalArgumentException("There is no sentence.");
		}
		ArrayList<String> words = new ArrayList<String>();
		List<String> list = Arrays.asList(stopword);
		// Lemmatize
        for (CoreLabel token: sentences.get(0).get(TokensAnnotation.class)) {
        	logger.debug("hi after lemma "+token.get(LemmaAnnotation.class));
        	logger.debug("hi after pos "+token.get(PartOfSpeechAnnotation.class));
        	logger.debug("hi after ner "+token.get(NamedEntityTagAnnotation.class));
        String lemma = token.get(LemmaAnnotation.class);
        logger.debug("my final lemma is "+lemma);
        	token.setWord(token.get(LemmaAnnotation.class));
        	token.get(PartOfSpeechAnnotation.class);
        	token.get(NamedEntityTagAnnotation.class);
        	if(!list.contains(lemma))
        		
        		words.add(lemma);
        }
        return words;
	}

	@Override
	public List<NlpOutput> getOutput(List<String> list) {
		List<String> Intent = new ArrayList<String>();
		logger.debug("Get Nlp with Textword");
		List<NlpOutput> final_nlp=new ArrayList<>();
		System.out.println(list.toString());
		for(String s:list){
			Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "kavya@123" ) );
			Session session = driver.session();
			System.out.println("in sresult of neo4j "+s);
			StatementResult result = session.run( "match (s:SubIntent{name:{lemn}})-[r]->(b:BaseIntent) return b.name AS name",
			        parameters( "lemn", s ) );
			
			
		System.out.println(result.hasNext());

			while(result.hasNext())
			{
			    System.out.println("hi.....");
				Record record = result.next();
				
			    Intent.add(record.get("name").asString());
			
				System.out.println( "hi my record is"+ record.get( "name" ).asString() );
			}
			
			session.close();
			driver.close();
			}
		
			for(String s:Intent){
				System.out.println("in intent list -------"+s);
			}
			
			for(String s:list){
				System.out.println("hi i am in "+s);
				String a=s.substring(0, 1).toUpperCase() + s.substring(1);
			
				System.out.println("hi ia m in "+a);
			Driver driver1 = GraphDatabase.driver( "bolt://172.23.239.174:7687", AuthTokens.basic( "neo4j", "root123" ) );
			Session session1 = driver1.session();
//			
//			match (c:Concept{name:'Array'})-[r:Relates{intent:'basics'}]-() return c,r.confidenceScore AS cs order by r.confidenceScore DESC
//			StatementResult result1 = session1.run( "match (a:Term{name:{lemn}}) return a.name AS name",
//			        parameters( "lemn", "monitor" ) );
//			for(String intent:Intent)
//			{
//			
//			StatementResult result1 = session1.run( "match (c:Concept{name:{lemn}})-[r:Relates{intent:{intent}}]-(d:Document) return d.title AS title,d.url AS url,r.description As description,r.confidenceScore AS cs order by r.confidenceScore DESC",
//			        parameters( "lemn",  a ,"intent",Intent.get(0)) );
//			
//			StatementResult result2 = session1.run( "match (c:Concept{name:{lemn}})-[r:Relates{intent:{intent}}]-(d:Document) return d.title AS title,d.url AS url,r.description As description,r.confidenceScore AS cs order by r.confidenceScore DESC",
//			        parameters( "lemn",  a ,"intent",Intent.get(1)) );
//			
			
			List<Map<String,Object>> listMap1 = nlpRepository.getOutput(a, Intent.get(0));
			List<Map<String,Object>> listMap2 = nlpRepository.getOutput(a, Intent.get(1));
			
			System.out.println("here we are "+listMap1.toString());
			
			for(Map<String,Object> map : listMap1){
				System.out.println("her is map 2 value ==== "+map.toString());
				NlpOutput nlpOutput = new NlpOutput();
				nlpOutput.setUrl(""+map.get("url"));
				nlpOutput.setTitle(""+map.get("title"));
				nlpOutput.setDesc(""+map.get("description"));
				nlpOutput.setConfidencescore(Float.parseFloat(""+map.get("cs")));
				final_nlp.add(nlpOutput);
			}
			
			for(Map<String,Object> map : listMap2){
				System.out.println("her is map 2 value ==== "+map.toString());
				NlpOutput nlpOutput = new NlpOutput();
				nlpOutput.setUrl(""+map.get("url"));
				nlpOutput.setTitle(""+map.get("title"));
				nlpOutput.setDesc(""+map.get("description"));
				nlpOutput.setConfidencescore(Float.parseFloat(""+map.get("cs")));
				final_nlp.add(nlpOutput);
			}
			System.out.println(final_nlp.toString());
			
//			System.out.println("result1 is "+result1.keys());
//			
//			System.out.println("my result has next value is "+result1.hasNext());
//            System.out.println("result2 is "+result2.keys());
//			
//			System.out.println("my result has next value is "+result2.hasNext());
//			System.out.println("result1 is "+result1.list());
//			System.out.println("hi this is above while");
//			while(result1.hasNext())
//			{
//			    NlpOutput nlpOutput = new NlpOutput(); 
//				System.out.println("hi.....");
//				Record record = result1.next();
//			    System.out.println( "hi my record is"+ record.get( "title" ).asString() );
//			    System.out.println( "hi my record is"+ record.get( "url" ).asString() );
//			    System.out.println( "hi my record is"+ record.get( "description" ).asString() );
//			    nlpOutput.setUrl(record.get( "url" ).asString());
//			    nlpOutput.setTitle(record.get( "title" ).asString());
//			    nlpOutput.setDesc(record.get( "description" ).asString());
//			    nlpOutput.setConfidencescore(record.get( "cs" ).asFloat());
//			    final_nlp.add(nlpOutput);
//			}
//			while(result2.hasNext())
//			{
//			    NlpOutput nlpOutput = new NlpOutput(); 
//				System.out.println("hi.....");
//				Record record = result2.next();
//			    System.out.println( "hi my record is"+ record.get( "title" ).asString() );
//			    System.out.println( "hi my record is"+ record.get( "url" ).asString() );
//			    System.out.println( "hi my record is"+ record.get( "description" ).asString() );
//			    nlpOutput.setUrl(record.get( "url" ).asString());
//			    nlpOutput.setTitle(record.get( "title" ).asString());
//			    nlpOutput.setDesc(record.get( "description" ).asString());
//			    nlpOutput.setConfidencescore(record.get( "cs" ).asFloat());
//			    final_nlp.add(nlpOutput);
//			}
//			System.out.println("hi this is below while");
////			session1.close();
////			driver1.close();
//		}
			session1.close();
		driver1.close();
			
	}return final_nlp;
}
}