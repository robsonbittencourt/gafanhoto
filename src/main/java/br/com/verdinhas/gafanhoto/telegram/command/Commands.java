package br.com.verdinhas.gafanhoto.telegram.command;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Optional;

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

	public boolean verifyCommands(ReceivedMessage message, String text, GafanhotoBot bot) {
		Optional<BotCommand> findFirst = getCommands().stream().filter(c -> text.startsWith(c.command())).findFirst();

		if (findFirst.isPresent()) {
			findFirst.get().doIt(bot, message);
			return true;
		}

		return false;
	}

	private List<BotCommand> getCommands() {
		return asList(startCommand, monitorCommand, deleteMonitorCommand, listMonitorsCommand, helpCommand);
	}
}
