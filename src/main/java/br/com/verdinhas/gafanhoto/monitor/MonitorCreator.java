package br.com.verdinhas.gafanhoto.monitor;

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
		List<String> arguments = new ArrayList<>(message.arguments());
		String mainKeyWord = arguments.get(0);
		arguments.remove(0);

		monitorRepository.save(new Monitor(message.userId(), message.chatId(), mainKeyWord, arguments));
	}

}
