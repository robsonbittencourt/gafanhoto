package br.com.verdinhas.gafanhoto.telegram;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.verdinhas.gafanhoto.telegram.command.Commands;
import br.com.verdinhas.gafanhoto.telegram.command.callback.Callbacks;

@Component
public class GafanhotoBot extends TelegramLongPollingBot {

	@Autowired
	private Callbacks callbacks;

	@Autowired
	private Commands commands;

	private Map<Long, String> usersWaitingCallback = new HashMap<>();

	@Override
	public void onUpdateReceived(Update update) {
		ReceivedMessage message = new ReceivedMessage(update);

		String callbackIdentifier = getCallbackIdentifier(message);

		boolean executedCallback = callbacks.verifyCallbacks(message, this, callbackIdentifier);

		if (!executedCallback) {
			commands.verifyCommands(message, message.text(), this);
		}
	}

	private String getCallbackIdentifier(ReceivedMessage message) {
		String callbackIdentifier = usersWaitingCallback.get(message.chatId());
		usersWaitingCallback.remove(message.chatId());
		return callbackIdentifier;
	}

	@Override
	public String getBotUsername() {
		return "GafanhotoMobBot";
	}

	@Override
	public String getBotToken() {
		return System.getenv("GAFANHOTO_TOKEN");
	}

	public void sendMessage(Long chatId, String message) {
		SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(message);

		try {
			execute(sendMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessageWithCallback(Long chatId, String message, String callbackIdentifier) {
		usersWaitingCallback.put(chatId, callbackIdentifier);

		sendMessage(chatId, message);
	}

	public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
		try {
			return super.execute(method);
		} catch (TelegramApiException e) {
			e.printStackTrace();
			return null;
		}
	}

}
