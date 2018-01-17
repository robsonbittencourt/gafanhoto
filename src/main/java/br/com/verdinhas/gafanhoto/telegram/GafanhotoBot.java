package br.com.verdinhas.gafanhoto.telegram;

import static java.util.Arrays.asList;

import java.io.Serializable;
import java.util.List;

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

	@Override
	public void onUpdateReceived(Update update) {
		ReceivedMessage message = new ReceivedMessage(update);

		if (message.hasCallbackQuery()) {
			getCallbacks().stream().filter(c -> message.callbackText().startsWith(c.prefixIdentifier())).findFirst()
					.ifPresent(c -> c.callback(this, message));
		}

		if (message.hasText()) {
			getCommands().stream().filter(c -> message.text().startsWith(c.command())).findFirst()
					.ifPresent(c -> c.doIt(this, message));
		}
	}

	private List<BotCommand> getCommands() {
		return asList(startCommand, monitorCommand, deleteMonitorCommand, listMonitorsCommand);
	}

	private List<BotCallback> getCallbacks() {
		return asList(deleteMonitorCallback);
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

	public void sendConversation(Long chatId, List<String> messages) {
		Runnable task = () -> {
			for (String message : messages) {
				sendMessage(chatId, message);

				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		new Thread(task).start();
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
