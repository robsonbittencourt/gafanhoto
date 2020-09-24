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
		List<String> words = hardmobCrawler.decompose("http://www.hardmob.com.br/threads/702432-Submarino-Game-Detroit-Become-Human-PS4-R99?s=519e805efac23ba7042f8c512d7b7365");

		assertEquals(7, words.size());
		assertEquals("Submarino", words.get(0));
		assertEquals("Game", words.get(1));
		assertEquals("Detroit", words.get(2));
		assertEquals("Become", words.get(3));
		assertEquals("Human", words.get(4));
		assertEquals("PS4", words.get(5));
		assertEquals("R99", words.get(6));
	}

	@Test
	public void shouldReturnUniqueIdentifierFromUrl() {
		String identifier = hardmobCrawler.getIdentifier("http://www.hardmob.com.br/threads/702432-Submarino-Game-Detroit-Become-Human-PS4-R99?s=519e805efac23ba7042f8c512d7b7365");
		assertEquals("702432", identifier);
	}

	@Test
	public void shouldDecomposeWhenUrlNotHaveIdentifier() {
		List<String> words = hardmobCrawler.decompose("https://www.hardmob.com.br:443/threads/449293-twitter-siga-a-hardmob-promocoes-status-ok");

		assertEquals(7, words.size());
		assertEquals("twitter", words.get(0));
		assertEquals("siga", words.get(1));
		assertEquals("a", words.get(2));
		assertEquals("hardmob", words.get(3));
		assertEquals("promocoes", words.get(4));
		assertEquals("status", words.get(5));
		assertEquals("ok", words.get(6));
	}



}
