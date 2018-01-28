package br.com.verdinhas.gafanhoto.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import br.com.verdinhas.gafanhoto.monitor.Monitor;

public class UtilsTest {
	
	@Test
	public void shouldRemoveAccentsAndConvertToLowerCase() {
		String normalizedString = Utils.normalizeString("Promoção");
		
		assertEquals("promocao", normalizedString);
	}
	
	@Test
	public void filterDistinctKeys() {
		List<Monitor> monitors = new ArrayList<>();
		monitors.add(new Monitor(0, 0L, "test", new ArrayList<>()));
		monitors.add(new Monitor(0, 0L, "car", new ArrayList<>()));
		monitors.add(new Monitor(1, 1L, "car", new ArrayList<>()));
		
		List<Monitor> filteredMonitors = monitors.stream()
			.filter(Utils.distinctByKey(Monitor::getChatId))
			.collect(Collectors.toList());
		
		assertEquals(2, filteredMonitors.size());
		assertEquals(0, filteredMonitors.get(0).getChatId());
		assertEquals(1, filteredMonitors.get(1).getChatId());
	}

}
