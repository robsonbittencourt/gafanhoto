package br.com.verdinhas.gafanhoto.telegram.command;

import static br.com.verdinhas.gafanhoto.util.Utils.addSeparators;
import static java.lang.System.lineSeparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.monitor.MonitorCreator;
import br.com.verdinhas.gafanhoto.monitor.MonitorValidator;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Component
public class MonitorCommand implements BotCommand {

	@Autowired
	private MonitorCreator monitorCreator;

	@Autowired
	private MonitorValidator monitorValidator;

	@Override
	public String command() {
		return "/monitorar";
	}

	@Override
	public void doIt(GafanhotoBot bot, ReceivedMessage message) {
		if (monitorValidator.isInvalidArgumentsQuantity(message, bot)) {
			return;
		}

		if (monitorValidator.limitOfMonitorsReached(bot, message)) {
			return;
		}

		monitorCreator.create(message);

		bot.sendMessage(message.chatId(), buildMonitorarFeedbackMessage(message));
	}

	private String buildMonitorarFeedbackMessage(ReceivedMessage message) {
		StringBuilder sb = new StringBuilder();

		sb.append("Monitor criado com sucesso. ");
		sb.append("Vou te avisar quando aparecer uma oferta que atenda as palavras-chave:").append(lineSeparator());
		sb.append(addSeparators(message.arguments()));

		return sb.toString();
	}

}
