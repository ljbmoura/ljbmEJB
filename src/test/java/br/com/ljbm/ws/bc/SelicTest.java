package br.com.ljbm.ws.bc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SelicTest {

	@Mock
	Logger log;
	
	@InjectMocks 
	Selic selicWS;

	@Test
	public void testFatorAcumuladoSelic() {

		BigDecimal fator = selicWS.fatorAcumuladoSelic(LocalDate.of(2007, 4, 19), LocalDate.of(2018, 4, 6));
		assertThat(fator, equalTo(new BigDecimal("3.07544276955311")));
		
	}

}
