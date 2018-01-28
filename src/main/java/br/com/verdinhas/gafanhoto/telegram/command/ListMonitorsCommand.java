package br.com.verdinhas.gafanhoto.telegram.command;

import static java.lang.System.lineSeparator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.monitor.Monitor;
import br.com.verdinhas.gafanhoto.monitor.MonitorRepository;
import br.com.verdinhas.gafanhoto.monitor.MonitorValidator;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ListMonitorsCommand implements BotCommand {

	@Autowired
	private MonitorRepository monitorRepository;

	@Autowired
	private MonitorValidator monitorValidator;

	@Override
	public String command() {
		return "/listar";
	}

	@Override
	public void doIt(GafanhotoBot bot, ReceivedMessage message) {
		log.info("Executando comando listar");

		List<Monitor> userMonitors = monitorRepository.findByUserId(message.userId());

		if (monitorValidator.thereAreNoMonitors(bot, message, userMonitors)) {
			return;
		}

		bot.sendMessage(message.chatId(), buildMonitorList(userMonitors));
	}

	private String buildMonitorList(List<Monitor> userMonitors) {
		StringBuilder sb = new StringBuilder();

		userMonitors.forEach(m -> {
			sb.append("* " + m.toString());
			sb.append(lineSeparator());
		});

		return sb.toString();
	}

}
