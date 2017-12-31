package br.com.verdinhas.gafanhoto;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Gafanhoto {
	
	@Value("${forumUrl}")
	private String forumUrl;

	@Value("${elements.main}")
	private String mainClass;

	@Value("${elements.title}")
	private String titleClass;

	@Autowired
	private WebDriver driver;

	public List<String> getActualUrls() {
		driver.get(forumUrl);
		
		waitingPageLoad();

		List<WebElement> urlElements = driver.findElements(By.className(titleClass));
		
		List<String> urls = urlElements.stream().map(url -> url.getAttribute("href")).collect(Collectors.toList());

		driver.quit();

		return urls;
	}

	private void waitingPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(mainClass)));
	}

}
