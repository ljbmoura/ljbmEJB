package br.com.ljbm.fp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.utilitarios.Recurso;

public class LeitorExtratoTesouroDiretoTest {
	private static Logger log;
	private LeitorExtratoTesouroDireto leitor;
	private static File pastaExtratos;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log = LogManager.getFormatterLogger(LeitorExtratoTesouroDiretoTest.class);
		pastaExtratos = Recurso.getPastaRecursos(LeitorExtratoTesouroDiretoTest.class);
		// log.info(pastaExtratos.getPath().toString());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		File[] x = pastaExtratos.listFiles();
		log.trace(Recurso.getFileContents(x[0].getPath()));
		log.info("testando extrato : " + x[0].getPath());
		leitor = new LeitorExtratoTesouroDireto(x[0].getPath());

		leitor.parse();
		List<Aplicacao> aplLidas = leitor.getAplicacoes();
		assertThat(aplLidas.size(), equalTo(1));
		Aplicacao apl = aplLidas.get(0);
		assertThat(apl.getFundoInvestimento().getNome(), equalTo("Tesouro IPCA+ 2024"));
		assertThat(apl.getFundoInvestimento().getCorretora().getRazaoSocial(), equalTo("BB BANCO DE INVESTIMENTO S/A"));
//		log.info(apl);
	}

}
