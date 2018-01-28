package br.com.verdinhas.gafanhoto.monitor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MonitorTest {
	
	@Test
	public void shouldCreateMonitorNormalizeMainAndOtherKeyWords() {
		List<String> otherKeyWords = new ArrayList<>();
		otherKeyWords.add("LED");
		otherKeyWords.add("Samsung");
		
		Monitor monitor = new Monitor(1, 1L, "TV", otherKeyWords);
		
		assertEquals(1, monitor.getUserId());
		assertEquals(1L, monitor.getChatId());
		assertEquals("tv", monitor.getMainKeyWord());
		
		assertEquals(2, monitor.getOtherKeyWords().size());
		assertEquals("led", monitor.getOtherKeyWords().get(0));
		assertEquals("samsung", monitor.getOtherKeyWords().get(1));
	}
	
	
	@Test
	public void shouldReturnCorretToString() {
		List<String> otherKeyWords = new ArrayList<>();
		otherKeyWords.add("LED");
		otherKeyWords.add("Samsung");
		
		Monitor monitor = new Monitor(1, 1L, "TV", otherKeyWords);
		
		assertEquals("tv - led - samsung", monitor.toString());
	}
}
