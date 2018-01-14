package br.com.verdinhas.gafanhoto.monitor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriadorDeMonitor {

	@Autowired
	private MonitorRepository repository;

	public void criar(int userId, List<String> keyWords) {
		String mainKeyWord = keyWords.get(0);
		keyWords.remove(0);

		repository.save(new Monitor(userId, mainKeyWord, keyWords));
	}

}
