package br.com.verdinhas.gafanhoto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;

@SpringBootApplication
@EnableScheduling
public class GafanhotoApplication {

	@Autowired
	private GafanhotoBot mobBot;

	static {
		ApiContextInitializer.init();
	}

	public static void main(String[] args) {
		SpringApplication.run(GafanhotoApplication.class, args);
	}

	@PostConstruct
	public void startBot() {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		try {
			telegramBotsApi.registerBot(mobBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
