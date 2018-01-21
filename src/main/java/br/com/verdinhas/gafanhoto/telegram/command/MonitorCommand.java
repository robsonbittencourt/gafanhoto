package br.com.verdinhas.gafanhoto.telegram.command;

import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Component
public class MonitorCommand implements BotCommand {

	@Override
	public String command() {
		return "/monitorar";
	}

	@Override
	public void doIt(GafanhotoBot bot, ReceivedMessage message) {
		bot.sendMessageWithCallback(message.chatId(), "Quais são as palavras-chave que você quer monitorar?", "monitor");
	}

}
