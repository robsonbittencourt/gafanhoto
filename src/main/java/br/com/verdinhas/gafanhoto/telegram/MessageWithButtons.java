package br.com.verdinhas.gafanhoto.telegram;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class MessageWithButtons {

	private GafanhotoBot bot;
	private Long chatId;
	private String messageText;

	private InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
	private List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

	public MessageWithButtons(GafanhotoBot bot, Long chatId, String messageText) {
		this.bot = bot;
		this.chatId = chatId;
		this.messageText = messageText;

		markupInline.setKeyboard(rowsInline);
	}

	public void addButton(String description, String callbackIdentifier) {
		List<InlineKeyboardButton> rowInline = new ArrayList<>();

		InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton().setText(description)
				.setCallbackData(callbackIdentifier);

		rowInline.add(inlineKeyboardButton);

		rowsInline.add(rowInline);
	}

	public void send() {
		SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(messageText);
		sendMessage.setReplyMarkup(markupInline);
		
		bot.execute(sendMessage);
	}

}
