package br.com.verdinhas.gafanhoto.telegram.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.monitor.Monitor;
import br.com.verdinhas.gafanhoto.monitor.MonitorRepository;
import br.com.verdinhas.gafanhoto.monitor.MonitorValidator;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.MessageWithButtons;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		log.info("Executando comando help");

		List<Monitor> userMonitors = monitorRepository.findByUserId(message.userId());

		if (monitorValidator.thereAreNoMonitors(bot, message, userMonitors)) {
			return;
		}

		MessageWithButtons messageWithButtons = new MessageWithButtons(bot, message.chatId(), "Qual monitor você quer apagar?");

		for (Monitor monitor : userMonitors) {
			String callbackData = "delete-" + monitor.id;
			messageWithButtons.addButton(monitor.toString(), callbackData);
		}

		messageWithButtons.send();
	}

}
