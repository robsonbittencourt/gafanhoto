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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		if (executedCallback) {
			return;
		}

		boolean executedCommand = commands.verifyCommands(message, message.text(), this);
		if (executedCommand) {
			return;
		}

		log.info("Recebeu mensagem não tratada. Usuário: {} - Mensagem: {}", message.userId(), message.text());
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
			log.error("Erro ao enviar mensagem", e);
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
			log.error("Erro ao enviar mensagem", e);
			return null;
		}
	}

}
