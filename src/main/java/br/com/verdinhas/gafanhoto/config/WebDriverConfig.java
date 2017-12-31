package br.com.verdinhas.gafanhoto.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class WebDriverConfig {

	@Bean
	@Lazy
	public WebDriver webDriver() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");

		return new ChromeDriver(options);
	}

}
