package br.com.verdinhas.gafanhoto.workers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.alert.Alert;
import br.com.verdinhas.gafanhoto.alert.AlertRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SendAlertsScheduler {

	@Autowired
	private AlertRepository alertRepository;

	@Autowired
	private SendAlert sendAlert;

	@Scheduled(fixedDelay = 30000)
	public void sendAlerts() {
		List<Alert> alerts = alertRepository.findAll();

		log.info("Iniciando envio de alertas. Existem {} a serem enviados.", alerts.size());

		for (int i = 0; (i < alerts.size() && i < 100) ; i++) {
			sendAlert.send(alerts.get(i), i);
		}
	}

}
