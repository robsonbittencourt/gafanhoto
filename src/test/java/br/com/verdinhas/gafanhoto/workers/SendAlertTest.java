package br.com.verdinhas.gafanhoto.workers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.verdinhas.gafanhoto.alert.Alert;
import br.com.verdinhas.gafanhoto.alert.AlertRepository;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;

public class SendAlertTest {
	
	@InjectMocks
	private SendAlert sendAlert;
	
	@Mock
	private GafanhotoBot sendMessageBot;

	@Mock
	private AlertRepository alertRepository;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldSendMessageForEachAlertAndAfterDeleteAlert() {
		Alert alert = new Alert(0, 0L, "www.test.com/1234/car");

		when(sendMessageBot.sendMessage(eq(alert.getChatId()), anyString())).thenReturn(true);;
		
		sendAlert.send(alert);
		
		String message = "Nova oferta encontrada 🤑\n" + alert.getUrl();
		verify(sendMessageBot).sendMessage(0L, message);
		
		verify(alertRepository).delete(alert.id);
	}

}
