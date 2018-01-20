package br.com.verdinhas.gafanhoto.alert;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verdinhas.gafanhoto.monitor.Monitor;
import br.com.verdinhas.gafanhoto.monitor.MonitorSearcher;
import br.com.verdinhas.gafanhoto.url.Url;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlertCreator {

	@Autowired
	private MonitorSearcher monitorSearcher;

	@Autowired
	private AlertRepository alertRepository;

	public void createAlerts(Url url) {
		List<Monitor> monitors = monitorSearcher.searchByUrl(url);

		createAlertsToMonitors(url, monitors);
	}

	private void createAlertsToMonitors(Url url, List<Monitor> monitors) {
		log.info("Criando {} novos alertas", monitors.size());

		for (Monitor monitor : monitors) {
			alertRepository.save(new Alert(monitor.getUserId(), monitor.getChatId(), url.getUrl()));
		}
	}

}
