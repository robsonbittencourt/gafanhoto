package br.com.verdinhas.gafanhoto.telegram.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import br.com.verdinhas.gafanhoto.monitor.Monitor;
import br.com.verdinhas.gafanhoto.monitor.MonitorRepository;
import br.com.verdinhas.gafanhoto.monitor.MonitorValidator;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Component
public class DeleteMonitorCommand implements BotCommand {

	@Autowired
	private MonitorRepository monitorRepository;

	@Autowired
	private MonitorValidator monitorValidator;

	@Override
	public String command() {
		return "/apagar";
	}

	@Override
	public void doIt(GafanhotoBot bot, ReceivedMessage message) {
		List<Monitor> userMonitors = monitorRepository.findByUserId(message.userId());

		if (monitorValidator.thereAreNoMonitors(bot, message, userMonitors)) {
			return;
		}

		SendMessage sendMessage = new SendMessage().setChatId(message.chatId())
				.setText("Qual monitor você quer apagar?");

		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

		for (Monitor monitor : userMonitors) {
			List<InlineKeyboardButton> rowInline = new ArrayList<>();

			String callbackData = "delete-" + monitor.id;
			InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton().setText(monitor.toString()).setCallbackData(callbackData);
			rowInline.add(inlineKeyboardButton);

			rowsInline.add(rowInline);
		}

		markupInline.setKeyboard(rowsInline);
		sendMessage.setReplyMarkup(markupInline);

		bot.execute(sendMessage);
	}

}
