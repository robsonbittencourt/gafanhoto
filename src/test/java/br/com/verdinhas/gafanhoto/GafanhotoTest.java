package br.com.verdinhas.gafanhoto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GafanhotoTest {

	@Autowired
	private Gafanhoto gafanhoto;

	@Test
	public void shouldReturnAll33TopicUrlsFromForum() {
		List<String> actualUrls = gafanhoto.getActualUrls();

		assertEquals(33, actualUrls.size());
	}

	@Test
	public void urlsShouldFollowTopicUrlPattern() {
		String regex = "http:\\/\\/www\\.hardmob\\.com\\.br\\/promocoes\\/[0-9].*\\.html";
		Pattern pattern = Pattern.compile(regex);

		List<String> actualUrls = gafanhoto.getActualUrls();

		for (String url : actualUrls) {
			Matcher matcher = pattern.matcher(url);
			assertTrue(matcher.matches());
		}
	}

}
