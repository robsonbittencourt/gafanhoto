package br.com.verdinhas.gafanhoto.monitor;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Component
public class MonitorValidator {

	private static final int MAX_MONITORS_BY_USER = 10;

	@Autowired
	private MonitorRepository monitorRepository;

	public boolean limitOfMonitorsReached(GafanhotoBot bot, ReceivedMessage message) {
		List<Monitor> userMonitors = monitorRepository.findByUserId(message.userId());

		if (isNotEmpty(userMonitors) && userMonitors.size() == MAX_MONITORS_BY_USER) {
			SendMessage sendMessage = new SendMessage().setChatId(message.chatId()).setText(
					"Você quer boletar muitas coisas hein. Já existem 10 monitores cadastrados. Apague algum para cadastrar um novo.");

			InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
			List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
			List<InlineKeyboardButton> rowInline = new ArrayList<>();

			InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton().setText("Apagar monitores")
					.setCallbackData("/apagar");
			rowInline.add(inlineKeyboardButton);

			rowsInline.add(rowInline);

			markupInline.setKeyboard(rowsInline);
			sendMessage.setReplyMarkup(markupInline);

			bot.execute(sendMessage);

			return true;
		}

		return false;
	}

	public boolean thereAreNoMonitors(GafanhotoBot bot, ReceivedMessage message, List<Monitor> userMonitors) {
		if (isEmpty(userMonitors)) {
			bot.sendMessage(message.chatId(), "Você ainda não possui monitores.");
			return true;
		}

		return false;
	}

}
