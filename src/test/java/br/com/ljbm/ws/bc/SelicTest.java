package br.com.ljbm.ws.bc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.BeforeClass;
import org.junit.Test;

public class SelicTest {

	public static Selic selicWS;

	@BeforeClass
	public static void setup() {
		selicWS = new Selic();		
	}
	
	@Test
	public void testFatorAcumuladoSelic() {

		BigDecimal fator = selicWS.fatorAcumuladoSelic(LocalDate.of(2007, 4, 19), LocalDate.of(2018, 4, 6));
		assertThat(fator, equalTo(new BigDecimal("3.07544276955311")));
	}

}
