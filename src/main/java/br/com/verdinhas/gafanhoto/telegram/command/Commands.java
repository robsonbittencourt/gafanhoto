package br.com.verdinhas.gafanhoto.telegram.command;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Component
public class Commands {

	@Autowired
	private StartCommand startCommand;

	@Autowired
	private MonitorCommand monitorCommand;

	@Autowired
	private DeleteMonitorCommand deleteMonitorCommand;

	@Autowired
	private ListMonitorsCommand listMonitorsCommand;

	@Autowired
	private HelpCommand helpCommand;

	public void verifyCommands(ReceivedMessage message, String text, GafanhotoBot bot) {
		getCommands().stream().filter(c -> text.startsWith(c.command())).findFirst()
				.ifPresent(c -> c.doIt(bot, message));
	}

	private List<BotCommand> getCommands() {
		return asList(startCommand, monitorCommand, deleteMonitorCommand, listMonitorsCommand, helpCommand);
	}
}
