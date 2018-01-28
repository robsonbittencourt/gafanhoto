package br.com.verdinhas.gafanhoto;

import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class GafanhotoApplication {

	@Autowired
	private GafanhotoBot mobBot;
	
	static {
		ApiContextInitializer.init();
	}

	public static void main(String[] args) {
		SpringApplication.run(GafanhotoApplication.class, args);
	}
	
	@Bean(name = "async-task-executor")
    public Executor asyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
    	executor.setMaxPoolSize(20);
        executor.setThreadNamePrefix("async-task-executor");
        executor.initialize();
        return executor;
    }

	@PostConstruct
	public void startBot() {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		try {
			telegramBotsApi.registerBot(mobBot);
		} catch (TelegramApiException e) {
			log.error("Error on register bot");
		}
	}

}
