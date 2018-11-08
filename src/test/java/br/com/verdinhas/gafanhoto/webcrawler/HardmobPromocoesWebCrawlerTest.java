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
		ReflectionTestUtils.setField(hardmobCrawler, "forumUrl", "http://www.hardmob.com.br/forums/407-Promocoes?s=&pp=30&daysprune=1&sort=dateline&order=desc");
	}

	@Test
	public void shouldDecomposeUrlInWords() {
		List<String> words = hardmobCrawler.decompose("http://www.hardmob.com.br/threads/702252-Sexta-Feira-Negra-Capsulas-Dolce-Gusto-099-cada?s=74b87259e69c7169e759c19eb86a6a46");

		assertEquals(8, words.size());
		assertEquals("Sexta", words.get(0));
		assertEquals("Feira", words.get(1));
		assertEquals("Negra", words.get(2));
		assertEquals("Capsulas", words.get(3));
		assertEquals("Dolce", words.get(4));
		assertEquals("Gusto", words.get(5));
		assertEquals("099", words.get(6));
		assertEquals("cada", words.get(7));
	}

	@Test
	public void shouldReturnUniqueIdentifierFromUrl() {
		String identifier = hardmobCrawler.getIdentifier("http://www.hardmob.com.br/threads/702252-Sexta-Feira-Negra-Capsulas-Dolce-Gusto-099-cada?s=74b87259e69c7169e759c19eb86a6a46");
		assertEquals("702252", identifier);
	}

}
