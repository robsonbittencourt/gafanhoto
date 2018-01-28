package br.com.verdinhas.gafanhoto.monitor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

public class MonitorCreatorTest {
	
	@InjectMocks
	private MonitorCreator monitorCreator;
	
	@Mock
	private ReceivedMessage receivedMessage;
	
	@Mock
	private MonitorRepository monitorRepository;
	
	@Captor
	private ArgumentCaptor<Monitor> monitorCaptor;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldCreateMonitorWithReceivedMessage() {
		when(receivedMessage.text()).thenReturn("ps4 slim 1TB");
		when(receivedMessage.userId()).thenReturn(1);
		when(receivedMessage.chatId()).thenReturn(1L);
		
		monitorCreator.create(receivedMessage);
		
		verify(monitorRepository).save(monitorCaptor.capture());
		assertEquals(1, monitorCaptor.getValue().getUserId());
		assertEquals(1L, monitorCaptor.getValue().getChatId());
		assertEquals("ps4", monitorCaptor.getValue().getMainKeyWord());
		assertEquals(2, monitorCaptor.getValue().getOtherKeyWords().size());
		assertEquals("slim", monitorCaptor.getValue().getOtherKeyWords().get(0));
		assertEquals("1tb", monitorCaptor.getValue().getOtherKeyWords().get(1));
	}
	

}
