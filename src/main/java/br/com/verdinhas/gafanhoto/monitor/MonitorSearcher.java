package br.com.verdinhas.gafanhoto.monitor;

import static br.com.verdinhas.gafanhoto.util.Utils.distinctByKey;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.url.Url;

@Component
public class MonitorSearcher {

	@Autowired
	private MonitorRepository monitorRepository;

	public List<Monitor> searchByUrl(Url url) {
		List<Monitor> monitors = new ArrayList<>();

		for (String keyWord : url.getUrlWords()) {
			List<Monitor> monitorsWithAllKeyWords = monitorsWithAllKeyWords(url, keyWord);
			monitors.addAll(monitorsWithAllKeyWords);
		}

		return monitors.stream()
				.filter(distinctByKey(Monitor::getChatId))
				.collect(toList());
	}

	private List<Monitor> monitorsWithAllKeyWords(Url url, String keyWord) {
		List<Monitor> monitorsWithMainKeyWord = monitorRepository.findByMainKeyWord(keyWord);

		return filterMonitorsByKeyWords(monitorsWithMainKeyWord, url.getUrlWords());
	}

	private List<Monitor> filterMonitorsByKeyWords(List<Monitor> monitors, List<String> keyWords) {
		if (isEmpty(monitors)) {
			return new ArrayList<>();
		}

		return monitors.stream()
				.filter(m -> isEmpty(m.getOtherKeyWords()) || keyWords.containsAll(m.getOtherKeyWords()))
				.collect(toList());
	}

}
