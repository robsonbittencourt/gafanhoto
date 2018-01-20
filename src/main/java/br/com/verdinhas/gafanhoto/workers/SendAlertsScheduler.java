package br.com.verdinhas.gafanhoto.workers;

import static br.com.verdinhas.gafanhoto.util.Utils.sleep;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.alert.Alert;
import br.com.verdinhas.gafanhoto.alert.AlertRepository;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SendAlertsScheduler {

	@Autowired
	private AlertRepository alertRepository;

	@Autowired
	private GafanhotoBot sendMessageBot;

	@Scheduled(fixedDelay = 15000)
	public void sendAlerts() {
		Runnable task = () -> {
			List<Alert> alerts = alertRepository.findAll();

			log.info("Enviando {} alertas", alerts.size());

			// Telegram api have a limit of 30 messages per second
			int apiLimitHelper = 25;

			for (int i = 0; i < alerts.size(); i++) {
				if (i != 0 && i % apiLimitHelper == 0) {
					sleep(1000);
				}

				Alert alert = alerts.get(i);
				sendMessageBot.sendMessage(alert.getChatId(), "Nova oferta encontrada: " + alert.getUrl());
				alertRepository.delete(alert.id);
			}
		};

		new Thread(task).start();
	}

}
