package br.com.verdinhas.gafanhoto.monitor;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.verdinhas.gafanhoto.fixture.UrlFixture;
import br.com.verdinhas.gafanhoto.url.Url;

public class MonitorSearcherTest {
	
	@InjectMocks
	private MonitorSearcher monitorSearcher;
	
	@Mock
	private MonitorRepository monitorRepository;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldSearchMonitorByUrl() {
		List<String> urlWords = new ArrayList<>();
		urlWords.add("test");
		urlWords.add("car");
		Url url = UrlFixture.get().complete().urlWords(urlWords).build();
		
		List<Monitor> monitorsTest = new ArrayList<>();
		monitorsTest.add(new Monitor(0, 0L, "test", new ArrayList<>()));
		when(monitorRepository.findByMainKeyWord("test")).thenReturn(monitorsTest);
		
		List<Monitor> monitorsCar = new ArrayList<>();
		monitorsCar.add(new Monitor(1, 1L, "car", new ArrayList<>()));
		when(monitorRepository.findByMainKeyWord("car")).thenReturn(monitorsCar);
		
		List<Monitor> returnedMonitors = monitorSearcher.searchByUrl(url);
		
		assertEquals(2, returnedMonitors.size());
	}
	
	@Test
	public void shouldReturnMonitorIfUrlContainsAllWords() {
		List<String> urlWords = new ArrayList<>();
		urlWords.add("test");
		urlWords.add("boat");
		Url url = UrlFixture.get().complete().urlWords(urlWords).build();

		List<Monitor> monitorsTest = new ArrayList<>();
		monitorsTest.add(new Monitor(0, 0L, "test", asList("boat")));
		when(monitorRepository.findByMainKeyWord("test")).thenReturn(monitorsTest);
		
		List<Monitor> returnedMonitors = monitorSearcher.searchByUrl(url);
		
		assertEquals(1, returnedMonitors.size());
	}
	
	@Test
	public void shouldNotReturnMonitorIfUrlDoNotContainsAllWords() {
		List<String> urlWords = new ArrayList<>();
		urlWords.add("test");
		urlWords.add("car");
		Url url = UrlFixture.get().complete().urlWords(urlWords).build();
		
		List<Monitor> monitorsTest = new ArrayList<>();
		monitorsTest.add(new Monitor(0, 0L, "test", asList("boat")));
		when(monitorRepository.findByMainKeyWord("test")).thenReturn(monitorsTest);
		
		List<Monitor> returnedMonitors = monitorSearcher.searchByUrl(url);
		
		assertEquals(0, returnedMonitors.size());
	}
	
	@Test
	public void shouldNotReturnSameUrlForMonitorsWithSameChatId() {
		List<String> urlWords = new ArrayList<>();
		urlWords.add("test");
		urlWords.add("boat");
		Url url = UrlFixture.get().complete().urlWords(urlWords).build();
		
		List<Monitor> monitorsTest = new ArrayList<>();
		monitorsTest.add(new Monitor(0, 0L, "test", asList("boat")));
		when(monitorRepository.findByMainKeyWord("test")).thenReturn(monitorsTest);
		
		List<Monitor> monitorsBoat = new ArrayList<>();
		monitorsBoat.add(new Monitor(0, 0L, "boat", new ArrayList<>()));
		when(monitorRepository.findByMainKeyWord("boat")).thenReturn(monitorsBoat);
		
		List<Monitor> returnedMonitors = monitorSearcher.searchByUrl(url);
		
		assertEquals(1, returnedMonitors.size());
	}

}
