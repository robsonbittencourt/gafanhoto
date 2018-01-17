package br.com.verdinhas.gafanhoto.telegram.command.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.monitor.MonitorRepository;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Component
public class DeleteMonitorCallback implements BotCallback {

	@Autowired
	private MonitorRepository monitorRepository;

	@Override
	public void callback(GafanhotoBot bot, ReceivedMessage message) {
		String callbackData = message.callbackText();

		if (callbackData.startsWith("delete")) {
			String monitorId = callbackData.substring(callbackData.indexOf("-") + 1, callbackData.length());

			if (monitorRepository.exists(monitorId)) {
				monitorRepository.delete(monitorId);
				bot.sendMessage(message.chatId(),
						"Monitor apagado. Você não vai mais receber avisos destas palavras-chave.");
			} else {
				bot.sendMessage(message.chatId(), "Monitor não encontrado.");
			}
		}
	}

	@Override
	public String prefixIdentifier() {
		return "delete";
	}

}
