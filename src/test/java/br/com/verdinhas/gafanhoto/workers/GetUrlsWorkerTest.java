package br.com.verdinhas.gafanhoto.workers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.verdinhas.gafanhoto.Gafanhoto;
import br.com.verdinhas.gafanhoto.alertas.CriadorDeAlertas;
import br.com.verdinhas.gafanhoto.redis.RedisSetService;
import br.com.verdinhas.gafanhoto.workers.GetUrlsWorker;

public class GetUrlsWorkerTest {

	@InjectMocks
	private GetUrlsWorker urlsOrganizer;

	@Mock
	private Gafanhoto gafanhoto;

	@Mock
	private RedisSetService redisSetService;

	@Mock
	private CriadorDeAlertas verificadorAlertas;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void shouldSaveActualUrlsAndReturnNewUrls() {
		List<String> actualUrlsStub = new ArrayList<>();
		actualUrlsStub.add("www.hardmob.com.br/promocoes/123");
		actualUrlsStub.add("www.hardmob.com.br/promocoes/456");
		actualUrlsStub.add("www.hardmob.com.br/promocoes/789");
		when(gafanhoto.getActualUrls()).thenReturn(actualUrlsStub);

		Set<String> newUrlsStub = new HashSet<>();
		newUrlsStub.add("www.hardmob.com.br/promocoes/912");
		when(redisSetService.getDifference("actualUrls", "urls")).thenReturn(newUrlsStub);

		urlsOrganizer.updateDataBaseWithNewUrls();

		verify(redisSetService).saveElements(actualUrlsStub, "actualUrls");
		verify(redisSetService).delete("actualUrls");
		verify(redisSetService).saveElements(actualUrlsStub, "urls");

		verify(verificadorAlertas).criarAlertas("www.hardmob.com.br/promocoes/912");
	}

}
