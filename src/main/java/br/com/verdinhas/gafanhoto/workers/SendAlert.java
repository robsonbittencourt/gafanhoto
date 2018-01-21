package br.com.verdinhas.gafanhoto.workers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.vdurmont.emoji.EmojiManager;

import br.com.verdinhas.gafanhoto.alert.Alert;
import br.com.verdinhas.gafanhoto.alert.AlertRepository;
import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SendAlert {
	
	@Autowired
	private GafanhotoBot sendMessageBot;

	@Autowired
	private AlertRepository alertRepository;

	@Async("async-task-executor")
	public void send(Alert alert, int i) {
		log.info("Enviando alerta " + i);
		sendMessageBot.sendMessage(alert.getChatId(), buildMessage(alert));
		alertRepository.delete(alert.id);
	}

	private String buildMessage(Alert alert) {
		StringBuilder sb = new StringBuilder();

		sb.append("Nova oferta encontrada ");
		sb.append(EmojiManager.getForAlias("money_face").getUnicode());
		sb.append(System.lineSeparator());
		sb.append(alert.getUrl());

		return sb.toString();
	}

}
