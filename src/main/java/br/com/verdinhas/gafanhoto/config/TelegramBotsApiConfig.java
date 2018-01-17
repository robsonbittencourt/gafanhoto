package br.com.verdinhas.gafanhoto.config;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;

public class TelegramBotsApiConfig {

	private GafanhotoBot mobBot;

	public TelegramBotsApi telegramBotsApi() {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		try {
			telegramBotsApi.registerBot(mobBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

		return telegramBotsApi;
	}

}
