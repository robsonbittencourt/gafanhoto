package br.com.verdinhas.gafanhoto.telegram.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Component
public class StartCommand implements BotCommand {

	@Override
	public String command() {
		return "/start";
	}

	@Override
	public void doIt(GafanhotoBot bot, ReceivedMessage message) {
		List<String> messages = buildStartMessages();

		bot.sendConversation(message.chatId(), messages);
	}

	private List<String> buildStartMessages() {
		List<String> messages = new ArrayList<>();

		messages.add("Olá jovem gafanhoto.");
		messages.add("Apartir de agora, vou poupar você de ficar frenéticamente atrás de promoções.");
		messages.add(
				"Posso te avisar quando novas promoções surgirem, para isso você pode me pedir para monitorar certas palavras-chave para você.");
		messages.add(
				"Para fazer isso digite o comando /monitorar e informe até 5 palavras-chave como por exemplo: TV Samsung LED");
		messages.add(
				"Escolha as palavras com cuidado, pois só vou te mostrar caso todas elas apareçam no link da promoção.");
		messages.add("Quando mais palavras, mais específica será a busca.");
		messages.add("Para listar todos os comandos disponíveis digite /commands que te mostro.");

		return messages;
	}

}
