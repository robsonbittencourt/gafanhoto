package br.com.verdinhas.gafanhoto.monitor;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

@Service
public class MonitorCreator {

	@Autowired
	private MonitorRepository monitorRepository;

	public void create(ReceivedMessage message) {
		List<String> words = new ArrayList<>(asList(message.text().split(" ")));

		String mainKeyWord = words.get(0);
		List<String> otherKeyWords = words.subList(1, words.size());

		monitorRepository.save(new Monitor(message.userId(), message.chatId(), mainKeyWord, otherKeyWords));
	}

}
