package br.com.verdinhas.gafanhoto.webcrawler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.verdinhas.gafanhoto.url.Url;

public class UrlCrawlerAggregatorTest {
	
	@InjectMocks
	private UrlCrawlerAggregator urlCrawlerAggregator;
	
	@Mock
	private HardmobPromocoesWebCrawler hardmobPromocoesCrawler;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldAggregateUrlsFromAllCrawlers() {
		List<String> hardmobUrls = new ArrayList<>();
		hardmobUrls.add("http://www.hardmob.com.br/promocoes/682287-saraiva-box-a-historia-da-primeira-guerra-mundial-1914-1918-4-volumes-r-28-73-a.html");
		hardmobUrls.add("http://www.hardmob.com.br/promocoes/682256-Starbucks-Cafe-Gratis-Na-Compra-De-Croissant-No-Dia-Do-Croissant-Sp-Rj-Dia-30-a.html");
		hardmobUrls.add("http://www.hardmob.com.br/promocoes/679910-mosalinga-aprenda-ingles-premium-gratis-pra-ios-android.html");
		when(hardmobPromocoesCrawler.retrieveUrlsFromSource()).thenReturn(hardmobUrls);
		
		List<Url> retrieveUrlsFromSources = urlCrawlerAggregator.retrieveUrlsFromSources();
		
		assertEquals("http://www.hardmob.com.br/promocoes/682287-saraiva-box-a-historia-da-primeira-guerra-mundial-1914-1918-4-volumes-r-28-73-a.html", retrieveUrlsFromSources.get(0).getUrlAddress());
		assertEquals("http://www.hardmob.com.br/promocoes/682256-starbucks-cafe-gratis-na-compra-de-croissant-no-dia-do-croissant-sp-rj-dia-30-a.html", retrieveUrlsFromSources.get(1).getUrlAddress());
		assertEquals("http://www.hardmob.com.br/promocoes/679910-mosalinga-aprenda-ingles-premium-gratis-pra-ios-android.html", retrieveUrlsFromSources.get(2).getUrlAddress());
	}

}
