package br.com.verdinhas.gafanhoto.telegram;

import static java.util.Arrays.asList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.verdinhas.gafanhoto.telegram.command.BotCommand;
import br.com.verdinhas.gafanhoto.telegram.command.DeleteMonitorCommand;
import br.com.verdinhas.gafanhoto.telegram.command.ListMonitorsCommand;
import br.com.verdinhas.gafanhoto.telegram.command.MonitorCommand;
import br.com.verdinhas.gafanhoto.telegram.command.StartCommand;
import br.com.verdinhas.gafanhoto.telegram.command.callback.BotCallback;
import br.com.verdinhas.gafanhoto.telegram.command.callback.DeleteMonitorCallback;
import br.com.verdinhas.gafanhoto.telegram.command.callback.MonitorCallback;

@Component
public class GafanhotoBot extends TelegramLongPollingBot {

	@Autowired
	private StartCommand startCommand;

	@Autowired
	private MonitorCommand monitorCommand;

	@Autowired
	private DeleteMonitorCommand deleteMonitorCommand;

	@Autowired
	private ListMonitorsCommand listMonitorsCommand;

	@Autowired
	private DeleteMonitorCallback deleteMonitorCallback;

	@Autowired
	private MonitorCallback monitorCallback;

	private Map<Long, String> usersWaitingCallback = new HashMap<>();

	@Override
	public void onUpdateReceived(Update update) {
		ReceivedMessage message = new ReceivedMessage(update);

		verifyCallbacks(message);

		if (message.hasText()) {
			verifyCommands(message, message.text());
		}
	}

	private void verifyCommands(ReceivedMessage message, String text) {
		getCommands().stream().filter(c -> text.startsWith(c.command())).findFirst()
				.ifPresent(c -> c.doIt(this, message));
	}

	private void verifyCallbacks(ReceivedMessage message) {
		if (message.hasCallbackQuery() && message.callbackText().startsWith("/")) {
			verifyCommands(message, message.callbackText());
			return;
		}

		if (message.hasCallbackQuery()) {
			verifyCallbacks(message, message.callbackText());
			return;
		}

		String callbackIdentifier = usersWaitingCallback.get(message.chatId());
		if (callbackIdentifier != null) {
			usersWaitingCallback.remove(message.chatId());

			verifyCallbacks(message, callbackIdentifier);
		}
	}

	private void verifyCallbacks(ReceivedMessage message, String text) {
		getCallbacks().stream().filter(c -> text.startsWith(c.prefixIdentifier())).findFirst()
				.ifPresent(c -> c.callback(this, message));
	}

	private List<BotCallback> getCallbacks() {
		return asList(deleteMonitorCallback, monitorCallback);
	}

	private List<BotCommand> getCommands() {
		return asList(startCommand, monitorCommand, deleteMonitorCommand, listMonitorsCommand);
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
