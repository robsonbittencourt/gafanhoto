package br.com.verdinhas.gafanhoto.telegram.command;

import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.MessageWithButtons;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelpCommand implements BotCommand {

	public String command() {
		return "/help";
	}

	public void doIt(GafanhotoBot bot, ReceivedMessage message) {
		log.info("Executando comando help");

		MessageWithButtons messageWithButtons = new MessageWithButtons(bot, message.chatId(),
				"Veja as opções disponíveis");

		messageWithButtons.addButton("Monitorar promoções", "/monitorar");
		messageWithButtons.addButton("Apagar monitores", "/apagar");
		messageWithButtons.addButton("Listar monitores", "/listar");

		messageWithButtons.send();
	}

}
