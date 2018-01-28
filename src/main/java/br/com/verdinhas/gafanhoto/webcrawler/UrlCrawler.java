package br.com.verdinhas.gafanhoto.webcrawler;

import java.util.List;

public interface UrlCrawler {

	List<String> retrieveUrlsFromSource();

	List<String> decompose(String url);
	
	String getIdentifier(String url);

}
