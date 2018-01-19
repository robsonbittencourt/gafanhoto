package br.com.verdinhas.gafanhoto.telegram;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.Update;

public class ReceivedMessage {

	private Update update;
	private List<String> words;

	public ReceivedMessage(Update update) {
		this.update = update;
	}

	public Update update() {
		return update;
	}

	public boolean hasText() {
		return update.hasMessage() && update.getMessage().hasText();
	}

	public String text() {
		return update.getMessage().getText();
	}

	public boolean hasCallbackQuery() {
		return update.hasCallbackQuery();
	}

	public String callbackText() {
		return update.getCallbackQuery().getData();
	}

	public Integer userId() {
		return update.getMessage().getFrom().getId();
	}

	public Long chatId() {
		if (update.getCallbackQuery() != null && update.getCallbackQuery().getMessage() != null) {
			return update.getCallbackQuery().getMessage().getChatId();
		}

		return update.getMessage().getChatId();
	}

	public List<String> splitMessage() {
		if (this.words == null) {
			List<String> arguments = new ArrayList<>(asList(text().split(" ")));
			this.words = arguments;
		}

		return words;
	}

}
