package br.com.verdinhas.gafanhoto.telegram.command.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.monitor.MonitorRepository;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeleteMonitorCallback implements BotCallback {

	@Autowired
	private MonitorRepository monitorRepository;

	@Override
	public String prefixIdentifier() {
		return "delete";
	}

	@Override
	public void callback(GafanhotoBot bot, ReceivedMessage message) {
		String callbackData = message.callbackText();

		if (callbackData.startsWith("delete")) {
			String monitorId = callbackData.substring(callbackData.indexOf('-') + 1, callbackData.length());

			if (monitorRepository.exists(monitorId)) {
				monitorRepository.delete(monitorId);
				
				bot.sendMessageToUser(message.chatId(), "Monitor apagado. Você não vai mais receber avisos destas palavras-chave.");

				log.info("Monitor apagado");
			} else {
				bot.sendMessageToUser(message.chatId(), "Monitor não encontrado.");
			}
		}
	}

}
