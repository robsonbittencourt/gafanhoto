package br.com.verdinhas.gafanhoto.telegram;

import br.com.verdinhas.gafanhoto.telegram.command.Commands;
import br.com.verdinhas.gafanhoto.telegram.command.callback.Callbacks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class GafanhotoBot extends TelegramLongPollingBot {

	public static final int FORBIDDEN = 403;
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

    public boolean sendMessageToUser(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);

        return sendMessageToUser(sendMessage);
    }

    public boolean sendMessageToUser(SendMessage sendMessage) {
		try {
			execute(sendMessage);
			return true;
		} catch (TelegramApiException e) {
			if (e instanceof TelegramApiRequestException &&
					((TelegramApiRequestException)e).getErrorCode() == FORBIDDEN) {
				return true;
			} else {
				log.error("Erro ao enviar mensagem", e);
			}
		}

		return false;
	}

	public void sendMessageWithCallback(Long chatId, String message, String callbackIdentifier) {
		usersWaitingCallback.put(chatId, callbackIdentifier);

		sendMessageToUser(chatId, message);
	}
	
	@Override
	public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
		return super.execute(method);
	}

}
