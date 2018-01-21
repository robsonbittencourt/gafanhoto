package br.com.verdinhas.gafanhoto.telegram.command.callback;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;
import br.com.verdinhas.gafanhoto.telegram.command.Commands;

@Component
public class Callbacks {

	@Autowired
	private Commands commands;

	@Autowired
	private MonitorCallback monitorCallback;

	@Autowired
	private DeleteMonitorCallback deleteMonitorCallback;

	public boolean verifyCallbacks(ReceivedMessage message, GafanhotoBot bot, String callbackIdentifier) {
		if (message.hasCallbackQuery() && message.callbackText().startsWith("/")) {
			commands.verifyCommands(message, message.callbackText(), bot);
			return true;
		}

		if (message.hasCallbackQuery()) {
			verifyCallbacks(message, message.callbackText(), bot);
			return true;
		}

		if (callbackIdentifier != null) {
			verifyCallbacks(message, callbackIdentifier, bot);
			return true;
		}

		return false;
	}

	private void verifyCallbacks(ReceivedMessage message, String text, GafanhotoBot bot) {
		getCallbacks().stream()
			.filter(c -> text.startsWith(c.prefixIdentifier()))
			.findFirst()
			.ifPresent(c -> c.callback(bot, message));
	}

	private List<BotCallback> getCallbacks() {
		return asList(deleteMonitorCallback, monitorCallback);
	}
}
