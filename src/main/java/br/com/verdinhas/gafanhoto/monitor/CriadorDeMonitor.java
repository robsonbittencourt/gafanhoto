package br.com.verdinhas.gafanhoto.monitor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriadorDeMonitor {

	@Autowired
	private MonitorRepository repository;

	public void criar(String userId, String mainKeyWord, List<String> keyWords) {
		repository.save(new Monitor(userId, mainKeyWord, keyWords));
	}

}
