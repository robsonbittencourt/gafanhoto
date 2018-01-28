package br.com.verdinhas.gafanhoto.telegram.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CommandsTest {
	
	@InjectMocks
	private Commands commands;
	
	@Mock
	private StartCommand startCommand;

	@Mock
	private MonitorCommand monitorCommand;

	@Mock
	private DeleteMonitorCommand deleteMonitorCommand;

	@Mock
	private ListMonitorsCommand listMonitorsCommand;

	@Mock
	private HelpCommand helpCommand;
	
	@Before
	public void setUp() {
		initMocks(this);
		when(startCommand.command()).thenReturn("/start");
		when(monitorCommand.command()).thenReturn("/monitorar");
		when(deleteMonitorCommand.command()).thenReturn("/apagar");
		when(listMonitorsCommand.command()).thenReturn("/listar");
		when(helpCommand.command()).thenReturn("/help");
	}
	
	@Test
	public void shouldReturnTrueWhenReceiveStartCommand() {
		assertTrue(commands.verifyCommands(null, "/start", null));
	}
	
	@Test
	public void shouldReturnTrueWhenReceiveMonitorCommand() {
		assertTrue(commands.verifyCommands(null, "/monitorar", null));
	}
	
	@Test
	public void shouldReturnTrueWhenReceiveDeleteMonitorCommand() {
		assertTrue(commands.verifyCommands(null, "/apagar", null));
	}
	
	@Test
	public void shouldReturnTrueWhenReceiveListMonitorsCommand() {
		assertTrue(commands.verifyCommands(null, "/listar", null));
	}
	
	@Test
	public void shouldReturnTrueWhenReceiveHelpCommand() {
		assertTrue(commands.verifyCommands(null, "/help", null));
	}
	
	@Test
	public void shouldReturnFalseWhenAnyoneCommandResponds() {
		assertFalse(commands.verifyCommands(null, "/test", null));
	}
	
}
