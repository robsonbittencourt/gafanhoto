package br.com.verdinhas.gafanhoto.alert;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.verdinhas.gafanhoto.monitor.Monitor;
import br.com.verdinhas.gafanhoto.monitor.MonitorSearcher;
import br.com.verdinhas.gafanhoto.url.Url;

public class AlertCreatorTest {
	
	@InjectMocks
	private AlertCreator alertCreator;
	
	@Mock
	private MonitorSearcher monitorSearcher;

	@Mock
	private AlertRepository alertRepository;
	
	@Captor
	private ArgumentCaptor<Alert> alertCaptor;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldCreateAnAlertForEachFoundedMonitor() {
		List<String> urlWords = new ArrayList<>();
		urlWords.add("test");
		Url url = new Url("www.test.com", urlWords, new Date());
		
		List<Monitor> monitors = new ArrayList<>();
		monitors.add(new Monitor(0, 0L, "test", new ArrayList<>()));
		monitors.add(new Monitor(1, 1L, "test", new ArrayList<>()));
		monitors.add(new Monitor(2, 2L, "test", new ArrayList<>()));

		when(monitorSearcher.searchByUrl(url)).thenReturn(monitors);

		alertCreator.createAlerts(url);
		
		verify(alertRepository, times(3)).save(alertCaptor.capture());
		List<Alert> alerts = alertCaptor.getAllValues();
		
		assertEquals(0, alerts.get(0).getUserId());
		assertEquals(0L, alerts.get(0).getChatId());
		assertEquals("www.test.com", alerts.get(0).getUrl());
		
		assertEquals(1, alerts.get(1).getUserId());
		assertEquals(1L, alerts.get(1).getChatId());
		assertEquals("www.test.com", alerts.get(1).getUrl());
		
		assertEquals(2, alerts.get(2).getUserId());
		assertEquals(2L, alerts.get(2).getChatId());
		assertEquals("www.test.com", alerts.get(2).getUrl());
	}
}
