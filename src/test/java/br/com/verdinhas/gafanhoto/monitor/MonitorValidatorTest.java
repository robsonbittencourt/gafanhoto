package br.com.verdinhas.gafanhoto.monitor;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MonitorValidatorTest {
	
	@InjectMocks
	private MonitorValidator monitorValidator;
	
	@Mock
	private MonitorRepository monitorRepository;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldValidateWhenLimitOfMonitorsReached() {
		
	}
}
