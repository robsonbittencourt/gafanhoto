package br.com.verdinhas.gafanhoto.webcrawler;

import static java.util.Arrays.asList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.url.Url;

@Component
public class UrlCrawlerAggregator {

	@Autowired
	private HardmobPromocoesWebCrawler hardmobPromocoesCrawler;

	public List<Url> retrieveUrlsFromSources() {
		List<Url> urlsFromSources = new ArrayList<>();
		
		getUrlsCrawlers().forEach(c -> 
			c.retrieveUrlsFromSource().forEach(u -> {
				String address = u.toLowerCase();
				List<String> words = c.decompose(u);
				String identifier = c.getIdentifier(u);
				urlsFromSources.add(new Url(address, words, identifier, LocalDateTime.now()));
			})
		);
		
		return urlsFromSources;
	}

	private List<UrlCrawler> getUrlsCrawlers() {
		return asList(hardmobPromocoesCrawler);
	}

}
