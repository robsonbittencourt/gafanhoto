package br.com.verdinhas.gafanhoto.monitor;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Component
public class MonitorValidator {

	private static final int MAX_MONITORS_BY_USER = 10;

	@Autowired
	private MonitorRepository monitorRepository;

	public boolean limitOfMonitorsReached(GafanhotoBot bot, ReceivedMessage message) {
		List<Monitor> userMonitors = monitorRepository.findByUserId(message.userId());

		if (isNotEmpty(userMonitors) && userMonitors.size() == MAX_MONITORS_BY_USER) {
			bot.sendMessage(message.chatId(),
					"Você quer boletar muitas coisas hein. Já existem 10 monitores cadastrados. Apague algum para cadastrar um novo.");
			return true;
		}

		return false;
	}

	public boolean isInvalidArgumentsQuantity(ReceivedMessage message, GafanhotoBot bot) {
		List<String> arguments = message.arguments();

		if (arguments.size() == 0 || arguments.size() > 5) {
			bot.sendMessage(message.chatId(), "Quantidade de palavras-chave inválida. Insira de 1 a 5 palavras-chave.");
			return true;
		}

		return false;
	}

	public boolean thereAreNoMonitors(GafanhotoBot bot, ReceivedMessage message, List<Monitor> userMonitors) {
		if (isEmpty(userMonitors)) {
			bot.sendMessage(message.chatId(), "Você ainda não possui monitores.");
			return true;
		}

		return false;
	}

}
