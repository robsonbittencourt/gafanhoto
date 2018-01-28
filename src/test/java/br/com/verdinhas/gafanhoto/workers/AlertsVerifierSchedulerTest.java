package br.com.verdinhas.gafanhoto.workers;

import static br.com.verdinhas.gafanhoto.util.RandomUtils.getRandomString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.verdinhas.gafanhoto.alert.AlertCreator;
import br.com.verdinhas.gafanhoto.fixture.UrlFixture;
import br.com.verdinhas.gafanhoto.url.NewUrlFilter;
import br.com.verdinhas.gafanhoto.url.Url;
import br.com.verdinhas.gafanhoto.webcrawler.UrlCrawlerAggregator;

public class AlertsVerifierSchedulerTest {
	
	@InjectMocks
	private AlertsVerifierScheduler alertsVerifierScheduler;
	
	@Mock
	private UrlCrawlerAggregator urlCrawlerAggregator;

	@Mock
	private NewUrlFilter newUrlFilter;

	@Mock
	private AlertCreator alertCreator;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldCreateAlertsForNewFoundedUrls() {
		List<Url> urls = new ArrayList<>();
		urls.add(UrlFixture.get().identifier(getRandomString()).build());
		urls.add(UrlFixture.get().identifier(getRandomString()).build());
		urls.add(UrlFixture.get().identifier(getRandomString()).build());
		urls.add(UrlFixture.get().identifier(getRandomString()).build());
		urls.add(UrlFixture.get().identifier(getRandomString()).build());
		when(urlCrawlerAggregator.retrieveUrlsFromSources()).thenReturn(urls);
		
		when(newUrlFilter.filter(urls)).thenReturn(new HashSet<Url>(urls));
		
		alertsVerifierScheduler.verifyAlerts();
		
		verify(alertCreator).createAlerts(urls.get(0));
		verify(alertCreator).createAlerts(urls.get(1));
		verify(alertCreator).createAlerts(urls.get(2));
		verify(alertCreator).createAlerts(urls.get(3));
		verify(alertCreator).createAlerts(urls.get(4));
	}
	

}
