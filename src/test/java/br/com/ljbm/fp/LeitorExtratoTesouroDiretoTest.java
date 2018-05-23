package br.com.ljbm.fp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	}

//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}

	@Test
	public void extratoComUmFundoEDuasCompras() throws IOException {
		String caminhoArquivoExtrato = pastaExtratos.getPath() + File.separator + "extratoComUmFundoEDuasCompras.txt";
		log.info("testando extrato : " + caminhoArquivoExtrato);
		leitor = new LeitorExtratoTesouroDireto(caminhoArquivoExtrato);

		leitor.le();

		List<PosicaoTituloPorAgente> extrato = leitor.extratoLido();
		assertThat(extrato.size(), equalTo(1));

		PosicaoTituloPorAgente posicao = extrato.get(0);
		assertThat(posicao.getTitulo(), equalTo("Tesouro IPCA+ 2024"));
		assertThat(posicao.getAgenteCustodia(), equalTo("BB BANCO DE INVESTIMENTO S/A"));
		
		List<Aplicacao> compras = posicao.getCompras();
		assertThat(compras.size(), equalTo(2));
		Aplicacao primeiraCompra = compras.get(0);
		assertThat(primeiraCompra.getData(), equalTo(new GregorianCalendar(2007, Calendar.APRIL, 9)));
		assertThat(primeiraCompra.getQuantidadeCotas(), equalTo(new BigDecimal("5.20")));
		assertThat(primeiraCompra.getSaldoCotas(), equalTo(new BigDecimal("5.20")));
		assertThat(primeiraCompra.getValorAplicado(), equalTo(new BigDecimal("2890.94")));
		Aplicacao segundaCompra = compras.get(1);
		assertThat(segundaCompra.getData(), equalTo(new GregorianCalendar(2011, Calendar.NOVEMBER, 26)));
		assertThat(segundaCompra.getQuantidadeCotas(), equalTo(new BigDecimal("8.00")));
		assertThat(segundaCompra.getSaldoCotas(), equalTo(new BigDecimal("8.00")));
		assertThat(segundaCompra.getValorAplicado(), equalTo(new BigDecimal("8456.56")));
	}

}
