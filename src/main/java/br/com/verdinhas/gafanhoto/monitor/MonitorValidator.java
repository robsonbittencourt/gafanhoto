package br.com.verdinhas.gafanhoto.monitor;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vdurmont.emoji.EmojiManager;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.MessageWithButtons;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Component
public class MonitorValidator {

	private static final int MAX_MONITORS_BY_USER = 10;

	@Autowired
	private MonitorRepository monitorRepository;

	public boolean limitOfMonitorsReached(GafanhotoBot bot, ReceivedMessage message) {
		List<Monitor> userMonitors = monitorRepository.findByUserId(message.userId());

		if (isNotEmpty(userMonitors) && userMonitors.size() == MAX_MONITORS_BY_USER) {
			MessageWithButtons messageWithButtons = new MessageWithButtons(bot, message.chatId(), buildMessage());
			
			messageWithButtons.addButton("Apagar monitores", "/apagar");

			messageWithButtons.send();

			return true;
		}

		return false;
	}

	private String buildMessage() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Você quer boletar muitas coisas hein ");
		sb.append(EmojiManager.getForAlias("money_with_wings").getUnicode());
		sb.append(System.lineSeparator());
		sb.append("Já existem 10 monitores cadastrados. Apague algum para cadastrar um novo.");
		
		return sb.toString();
	}

	public boolean thereAreNoMonitors(GafanhotoBot bot, ReceivedMessage message, List<Monitor> userMonitors) {
		if (isEmpty(userMonitors)) {
			bot.sendMessageToUser(message.chatId(), "Você ainda não possui monitores.");
			return true;
		}

		return false;
	}

}
