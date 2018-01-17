package br.com.verdinhas.gafanhoto.workers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.alert.Alert;
import br.com.verdinhas.gafanhoto.alert.AlertRepository;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;

@Component
public class SendAlertsScheduler {

	@Autowired
	private AlertRepository alertRepository;

	@Autowired
	private GafanhotoBot sendMessageBot;

	@Scheduled(fixedDelay = 65000)
	public void sendAlerts() {
		List<Alert> alerts = alertRepository.findAll();

		for (Alert alert : alerts) {
			sendMessageBot.sendMessage(alert.getChatId(), "Nova oferta encontrada: " + alert.getUrl());
			alertRepository.delete(alert.id);
		}
	}

}
