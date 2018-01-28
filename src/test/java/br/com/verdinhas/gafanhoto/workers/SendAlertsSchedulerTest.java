package br.com.verdinhas.gafanhoto.workers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.verdinhas.gafanhoto.alert.Alert;
import br.com.verdinhas.gafanhoto.alert.AlertRepository;

public class SendAlertsSchedulerTest {
	
	@InjectMocks
	private SendAlertsScheduler sendAlertsScheduler;
	
	@Mock
	private AlertRepository alertRepository;

	@Mock
	private SendAlert sendAlert;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldSendMessageForEachAlert() {
		List<Alert> alerts = new ArrayList<>();
		alerts.add(new Alert(0, 0L, "www.test.com/1234/car"));
		alerts.add(new Alert(1, 1L, "www.test.com/1234/car"));
		alerts.add(new Alert(2, 2L, "www.test.com/1234/car"));
		when(alertRepository.findAll()).thenReturn(alerts);
		
		sendAlertsScheduler.sendAlerts();
		
		verify(sendAlert).send(alerts.get(0));
		verify(sendAlert).send(alerts.get(1));
		verify(sendAlert).send(alerts.get(2));
	}
	

}
