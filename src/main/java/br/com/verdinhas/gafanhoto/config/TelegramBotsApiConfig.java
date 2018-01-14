package br.com.verdinhas.gafanhoto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.verdinhas.gafanhoto.telegram.MonitorarCommand;

@Configuration
public class TelegramBotsApiConfig {

	@Autowired
	private MonitorarCommand gafanhotoMobBot;

	@Bean
	public TelegramBotsApi telegramBotsApi() {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		try {
			telegramBotsApi.registerBot(gafanhotoMobBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

		return telegramBotsApi;
	}

}
