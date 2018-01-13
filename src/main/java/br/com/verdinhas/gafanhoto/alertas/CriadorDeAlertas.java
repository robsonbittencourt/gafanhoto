package br.com.verdinhas.gafanhoto.alertas;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verdinhas.gafanhoto.monitor.Monitor;
import br.com.verdinhas.gafanhoto.monitor.MonitorRepository;
import br.com.verdinhas.gafanhoto.monitor.UrlDecomposer;

@Service
public class CriadorDeAlertas {

	@Autowired
	private UrlDecomposer urlDecomposer;

	@Autowired
	private MonitorRepository monitorRepository;

	@Autowired
	private AlertaRepository alertaRepository;

	public void criarAlertas(String url) {
		List<Monitor> monitors = getMonitorsToUrl(url);

		createAlertasToMonitors(url, monitors);
	}

	private void createAlertasToMonitors(String url, List<Monitor> monitors) {
		for (Monitor monitor : monitors) {
			alertaRepository.save(new Alerta(monitor.getUserId(), url));
		}
	}

	private List<Monitor> getMonitorsToUrl(String url) {
		List<String> keyWords = urlDecomposer.decompose(url);
		List<Monitor> monitors = new ArrayList<>();

		for (String keyWord : keyWords) {
			monitors.addAll(filterMonitorsByKeyWords(keyWords, keyWord));
		}

		return monitors;
	}

	private List<Monitor> filterMonitorsByKeyWords(List<String> keyWords, String keyWord) {
		List<Monitor> filteredMonitors = new ArrayList<>();
		List<Monitor> monitorsWithMainKey = monitorRepository.findByMainKeyWord(keyWord);

		if (isEmpty(monitorsWithMainKey)) {
			return filteredMonitors;
		}

		for (Monitor monitor : monitorsWithMainKey) {
			if (isEmpty(monitor.getOtherKeyWords()) || keyWords.containsAll(monitor.getOtherKeyWords())) {
				filteredMonitors.add(monitor);
			}
		}

		return filteredMonitors;
	}

}
