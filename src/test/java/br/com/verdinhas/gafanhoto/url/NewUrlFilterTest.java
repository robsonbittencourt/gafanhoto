package br.com.verdinhas.gafanhoto.url;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.verdinhas.gafanhoto.fixture.UrlFixture;

public class NewUrlFilterTest {
	
	@InjectMocks
	private NewUrlFilter newUrlFilter;
	
	@Mock
	private UrlRepository urlRepository;
	
	@Captor
	private ArgumentCaptor<Url> urlCaptor;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldSaveOnDataBaseOnlyNewUrls() {
		List<Url> urls = new ArrayList<>();
		urls.add(UrlFixture.get().complete().identifier("5566").build());
		urls.add(UrlFixture.get().complete().identifier("5678").build());
		urls.add(UrlFixture.get().complete().identifier("7788").build());
		
		List<Url> databaseUrls = new ArrayList<>();
		databaseUrls.add(UrlFixture.get().complete().identifier("1234").build());
		databaseUrls.add(UrlFixture.get().complete().identifier("5678").build());
		databaseUrls.add(UrlFixture.get().complete().identifier("3344").build());
		when(urlRepository.findAll()).thenReturn(databaseUrls);
		
		Set<Url> newUrls = newUrlFilter.filter(urls);
		
		verify(urlRepository, times(2)).save(urlCaptor.capture());
		assertEquals(urlCaptor.getAllValues().get(0).getIdentifier(), "5566");
		assertEquals(urlCaptor.getAllValues().get(1).getIdentifier(), "7788");
		
		assertEquals(2, newUrls.size());
	}

}
