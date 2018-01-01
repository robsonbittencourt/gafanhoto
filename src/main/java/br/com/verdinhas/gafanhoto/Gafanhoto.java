package br.com.verdinhas.gafanhoto;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.verdinhas.gafanhoto.config.WebDriverWrapper;

@Component
public class Gafanhoto {
	
	@Value("${forumUrl}")
	private String forumUrl;

	@Value("${elements.main}")
	private String mainClass;

	@Value("${elements.title}")
	private String titleClass;

	@Autowired
	private WebDriverWrapper driver;

	public List<String> getActualUrls() {
		driver.get(forumUrl);
		
		driver.waitingPageLoad(By.id(mainClass));

		List<WebElement> urlElements = driver.findElements(By.className(titleClass));
		
		List<String> urls = urlElements.stream().map(url -> url.getAttribute("href")).collect(Collectors.toList());

		driver.quit();

		return urls;
	}

}
