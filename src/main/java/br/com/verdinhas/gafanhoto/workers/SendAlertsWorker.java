package br.com.verdinhas.gafanhoto.workers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.alertas.Alerta;
import br.com.verdinhas.gafanhoto.alertas.AlertaRepository;
import br.com.verdinhas.gafanhoto.telegram.MonitorarCommand;

@Component
public class SendAlertsWorker {

	@Autowired
	private AlertaRepository alertaRepository;

	@Autowired
	private MonitorarCommand sendMessageBot;

	@Scheduled(fixedDelay = 65000)
	public void sendAlerts() {
		List<Alerta> alertas = alertaRepository.findAll();

		for (Alerta alerta : alertas) {
			sendMessageBot.sendMessage("Nova oferta encontrada: " + alerta.getUrl(), alerta.getChatId());
			alertaRepository.delete(alerta.id);
		}
	}

}
