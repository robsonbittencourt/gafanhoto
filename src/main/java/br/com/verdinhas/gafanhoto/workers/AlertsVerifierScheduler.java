package br.com.verdinhas.gafanhoto.workers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.alert.AlertCreator;
import br.com.verdinhas.gafanhoto.url.NewUrlFilter;
import br.com.verdinhas.gafanhoto.url.Url;
import br.com.verdinhas.gafanhoto.webcrawler.UrlCrawlerAggregator;

@Component
public class AlertsVerifierScheduler {

	@Autowired
	private UrlCrawlerAggregator urlCrawlerAggregator;

	@Autowired
	private NewUrlFilter newUrlFilter;

	@Autowired
	private AlertCreator alertCreator;

	@Scheduled(fixedDelay = 60000)
	public void verifyAlerts() {
		List<Url> urlsFromSources = urlCrawlerAggregator.retrieveUrlsFromSources();

		Set<Url> newUrls = newUrlFilter.filter(urlsFromSources);

		newUrls.stream()
				.forEach(u -> alertCreator.createAlerts(u));
	}

}
