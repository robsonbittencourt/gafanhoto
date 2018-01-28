package br.com.verdinhas.gafanhoto.telegram.command.callback;

import static br.com.verdinhas.gafanhoto.util.Utils.addSeparators;
import static java.lang.System.lineSeparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.monitor.MonitorCreator;
import br.com.verdinhas.gafanhoto.monitor.MonitorValidator;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MonitorCallback implements BotCallback {

	@Autowired
	private MonitorCreator monitorCreator;

	@Autowired
	private MonitorValidator monitorValidator;

	@Override
	public String prefixIdentifier() {
		return "monitor";
	}

	@Override
	public void callback(GafanhotoBot bot, ReceivedMessage message) {
		if (isInvalidKeyWordsQuantity(message, bot)) {
			return;
		}

		if (monitorValidator.limitOfMonitorsReached(bot, message)) {
			return;
		}

		monitorCreator.create(message);

		log.info("Monitor criado. Palavras chave: {}", message.text());

		bot.sendMessage(message.chatId(), buildMonitorarFeedbackMessage(message));
	}

	private boolean isInvalidKeyWordsQuantity(ReceivedMessage message, GafanhotoBot bot) {
		if (message.splitMessage().size() > 5) {
			bot.sendMessageWithCallback(message.chatId(), "Quantidade de palavras-chave inválida. Insira de 1 a 5 palavras-chave.", "monitor");
			return true;
		}

		return false;
	}

	private String buildMonitorarFeedbackMessage(ReceivedMessage message) {
		StringBuilder sb = new StringBuilder();

		sb.append("Monitor criado com sucesso. ");

		if (message.splitMessage().size() == 1) {
			sb.append("Vou te avisar quando aparecer uma oferta que atenda a palavra-chave:");
		} else {
			sb.append("Vou te avisar quando aparecer uma oferta que atenda as palavras-chave:");
		}

		sb.append(lineSeparator());
		sb.append(addSeparators(message.splitMessage()));

		return sb.toString();
	}

}
