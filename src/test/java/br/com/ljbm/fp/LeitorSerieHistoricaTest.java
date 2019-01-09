package br.com.ljbm.fp;

import static br.com.ljbm.utilitarios.Data.obterDataDDMMAAAA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import br.com.ljbm.fp.LeitorSerieHistorica;
import br.com.ljbm.fp.SerieHistorica;
import br.com.ljbm.utilitarios.Recurso;

public class LeitorSerieHistoricaTest {

	private static final File resourcesDir = Recurso
			.getPastaRecursos(LeitorSerieHistoricaTest.class);

	@Before
	public void setUp() throws Exception {
		// por enquanto sem necessidade;
	}

	@Test
	public void testLeCotacaoSELICBancoCentral() {
		String caminhoArquivoCotacaoSELICBC = resourcesDir.getPath()
				+ File.separator + "arquivoTaxaSelicDiariaBC.txt";

		LeitorSerieHistorica l = new LeitorSerieHistorica();
		Calendar chave = new GregorianCalendar();
//		try {
			SerieHistorica<BigDecimal> sh = l
					.leCotacoesSELICBancoCentral(caminhoArquivoCotacaoSELICBC);

			chave = obterDataDDMMAAAA(2, 1, 2006);
			// System.out.println(chave.getTime());
			assertEquals(new BigDecimal("1.0006563500000000"),
					sh.getElemento(chave));
			chave = obterDataDDMMAAAA(3, 1, 2006);
			assertEquals(new BigDecimal("1.0013131307953225"),
					sh.getElemento(chave));
			chave = obterDataDDMMAAAA(31, 12, 2008);
			assertEquals(new BigDecimal("1.0019700022222555"),
					sh.getElemento(chave));
			chave = obterDataDDMMAAAA(29, 2, 2012);
			assertEquals(new BigDecimal("1.9866502846083138"),
					sh.getElemento(chave));
			chave = obterDataDDMMAAAA(8, 3, 2012);
			assertEquals(new BigDecimal("1.9873766834183780"),
					sh.getElemento(chave));
			chave = obterDataDDMMAAAA(1, 1, 2010);
			assertEquals(null, sh.getElemento(chave));

//		} catch (IOException e) {
//			fail(e.getMessage());
//
//		}
	}

	@Test()
	public void testLeCotacaoSELICBancoCentral_ArquivoInvalido1() {
		String caminhoArquivoCotacaoSELICBC = resourcesDir.getPath()
				+ File.separator + "arquivoTaxaSelicDiariaBCInvalido1.txt";

		LeitorSerieHistorica l = new LeitorSerieHistorica();
		try {
			l.leCotacoesSELICBancoCentral(caminhoArquivoCotacaoSELICBC);
			fail(IllegalArgumentException.class.getName() + " esperada.");

		} catch (IllegalArgumentException iae) {
			if (!iae.getLocalizedMessage().equals(
					"Arquivo n�o est� no formato da Consulta � Taxa Selic Di�ria "
							+ "fornecido pelo BC: t�tulo n�o encontrado.")) {
				fail(IllegalArgumentException.class.getName()
						+ " esperada para t�tulo inv�lido.");
			}
//		} catch (IOException e) {
//			fail(e.getMessage());
//
		}
	}

	@Test()
	public void testLeCotacaoSELICBancoCentral_ArquivoInvalido2() {
		String caminhoArquivoCotacaoSELICBC = resourcesDir.getPath()
				+ File.separator + "arquivoTaxaSelicDiariaBCInvalido2.txt";

		LeitorSerieHistorica l = new LeitorSerieHistorica();
		try {
			l.leCotacoesSELICBancoCentral(caminhoArquivoCotacaoSELICBC);
			fail(IllegalArgumentException.class.getName() + " esperada.");

		} catch (IllegalArgumentException iae) {
			if (!iae.getLocalizedMessage().equals(
					LeitorSerieHistorica.ErroLinhaInvalida)) {
				fail(IllegalArgumentException.class.getName()
						+ " esperada para linha inv�lida.");
			}
//		} catch (IOException e) {
//			fail(e.getMessage());

		}
	}
}
