package br.com.verdinhas.gafanhoto.telegram.command;

import static br.com.verdinhas.gafanhoto.util.Utils.sleep;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.vdurmont.emoji.EmojiManager;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.MessageWithButtons;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StartCommand implements BotCommand {

	@Override
	public String command() {
		return "/start";
	}

	@Override
	public void doIt(GafanhotoBot bot, ReceivedMessage message) {
		log.info("Executando comando start");

		sendConversation(message.chatId(), buildStartMessages(), bot);
	}

	private void sendConversation(Long chatId, List<String> messages, GafanhotoBot bot) {
		Runnable task = () -> {
			messages.forEach(m -> {
				bot.sendMessage(chatId, m);
				sleep(3000);
			});

			sendMonitorButton(bot, chatId);
		};

		new Thread(task).start();
	}

	private List<String> buildStartMessages() {
		List<String> messages = new ArrayList<>();
		
		String monkeyEmoji = EmojiManager.getForAlias("see_no_evil").getUnicode();

		messages.add("Olá jovem gafanhoto.");
		messages.add("Apartir de agora, vou poupar você de ficar frenéticamente atrás de promoções " + monkeyEmoji);
		messages.add("Posso te avisar quando novas promoções surgirem, para isso você pode me pedir para monitorar certas palavras-chave para você.");
		messages.add("Para fazer isso digite o comando /monitorar e informe até 5 palavras-chave como por exemplo: TV Samsung LED");
		messages.add("Escolha as palavras com cuidado, pois só vou te mostrar caso todas elas apareçam no link da promoção.");
		messages.add("Quando mais palavras, mais específica será a busca.");
		messages.add("Para listar todos os comandos disponíveis digite /help que te mostro.");

		return messages;
	}

	private void sendMonitorButton(GafanhotoBot bot, Long chatId) {
		MessageWithButtons messageWithButtons = new MessageWithButtons(bot, chatId, "O que acha de já adicionar seu primeiro monitor? Clique no botão abaixo.");

		messageWithButtons.addButton("Monitorar promoções", "/monitorar");

		messageWithButtons.send();
	}

}
