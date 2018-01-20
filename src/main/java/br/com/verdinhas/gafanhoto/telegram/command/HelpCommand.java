package br.com.verdinhas.gafanhoto.telegram.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Component
public class HelpCommand implements BotCommand {

	public String command() {
		return "/help";
	}

	public void doIt(GafanhotoBot bot, ReceivedMessage message) {
		SendMessage sendMessage = new SendMessage().setChatId(message.chatId()).setText("Veja as opções disponíveis");

		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

		addOption(rowsInline, "Monitorar", "/monitorar");
		addOption(rowsInline, "Apagar monitores", "/apagar");
		addOption(rowsInline, "Listar monitores", "/listar");

		markupInline.setKeyboard(rowsInline);
		sendMessage.setReplyMarkup(markupInline);

		bot.execute(sendMessage);
	}

	private List<InlineKeyboardButton> addOption(List<List<InlineKeyboardButton>> rowsInline, String description,
			String callbackIdentifier) {
		List<InlineKeyboardButton> rowInline = new ArrayList<>();

		InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton().setText(description)
				.setCallbackData(callbackIdentifier);
		rowInline.add(inlineKeyboardButton);

		rowsInline.add(rowInline);

		return rowInline;
	}

}
