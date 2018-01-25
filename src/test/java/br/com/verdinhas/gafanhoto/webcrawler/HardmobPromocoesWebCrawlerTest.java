package br.com.verdinhas.gafanhoto.webcrawler;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

public class HardmobPromocoesWebCrawlerTest {

	@InjectMocks
	private HardmobPromocoesWebCrawler hardmobCrawler;

	@Before
	public void setUp() {
		initMocks(this);
		
		ReflectionTestUtils.setField(hardmobCrawler, "forumUrl", "http://www.hardmob.com.br/promocoes/?pp=30&daysprune=1&sort=dateline&order=desc");
	}

	@Test
	public void shouldDecomposeUrlInWords() {
		List<String> words = hardmobCrawler.decompose("http://www.hardmob.com.br/promocoes/682015-magazine-luiza-xbox-live-gold-de-12-meses-r-99-00-a.html");

		assertEquals(words.size(), 10);
		assertEquals("magazine", words.get(0));
		assertEquals("luiza", words.get(1));
		assertEquals("xbox", words.get(2));
		assertEquals("live", words.get(3));
		assertEquals("gold", words.get(4));
		assertEquals("de", words.get(5));
		assertEquals("12", words.get(6));
		assertEquals("meses", words.get(7));
		assertEquals("99", words.get(8));
		assertEquals("00", words.get(9));
	}

	@Test
	public void shouldReturnUniqueIdentifierFromUrl() {
		String identifier = hardmobCrawler.getIdentifier("http://www.hardmob.com.br/promocoes/682015-magazine-luiza-xbox-live-gold-de-12-meses-r-99-00-a.html");

		assertEquals("682015", identifier);
	}

}
