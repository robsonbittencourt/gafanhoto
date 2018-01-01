package br.com.verdinhas.gafanhoto.config;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class WebDriverWrapper {

	private WebDriver driver;

	public void init() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("always-authorize-plugins");
		options.addArguments("dns-prefetch-disable");

		driver = new ChromeDriver(options);
	}

	public void get(String url) {
		if (driver == null) {
			init();
		}

		driver.get(url);
	}

	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	public void waitingPageLoad(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void quit() {
		driver.quit();
		driver = null;
	}

}
