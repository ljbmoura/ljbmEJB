/**
 * 
 */
package br.com.ljbm.fp;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.TipoFundoInvestimento;
import br.com.ljbm.utilitarios.Data;
import br.com.ljbm.utilitarios.Recurso;

/**
 * @author guest
 * 
 */
public class LeitorExtratoInvestimentosBBTest {

	private ExtratoInvestimento _1oExtratoInvestimento;
	private ExtratoInvestimento _2oExtratoInvestimento;
	private Aplicacao umaAplicacao;
	private static final File resourcesDir = Recurso
			.getPastaRecursos(LeitorExtratoInvestimentosBBTest.class);

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testAnalisador() {
		String caminho = resourcesDir.getPath() + File.separator
				+ "extratoInvestimentos.txt";

		LeitorExtratoInvestimentosBB l = new LeitorExtratoInvestimentosBB();

		List<ExtratoInvestimento> extratoInvestimento = l.analisador(caminho);

		List<Aplicacao> aplicacoes = new ArrayList<Aplicacao>();
		umaAplicacao = new Aplicacao(LocalDate.of(2007, 6, 5),
				new Long("7111426"), new BigDecimal("800.00"), new BigDecimal(
						"207.702167"), new BigDecimal("207.702167"));
		aplicacoes.add(umaAplicacao);
		umaAplicacao = new Aplicacao(LocalDate.of(2007, 6, 27),
				new Long("7103428"), new BigDecimal("500.00"), new BigDecimal(
						"123.763662"), new BigDecimal("123.763662"));
		aplicacoes.add(umaAplicacao);
		_1oExtratoInvestimento = new ExtratoInvestimento(
				Data.obterDataDDMMAAAA(16, 3, 2012), new BigDecimal(
						"7.707668000"), aplicacoes, new FundoInvestimento(
						"02020528000158", "BB Ações Energia"
						, new BigDecimal("0.15")
						,TipoFundoInvestimento.Acoes));

		ExtratoInvestimento investimento = extratoInvestimento.get(0);
		// assertEquals(_1oExtratoInvestimento.getFundoInvestimento(),
		// investimento.getFundoInvestimento());
		// assertEquals(_1oExtratoInvestimento.getAplicacoes().get(0),
		// investimento.getAplicacoes().get(0));
		// assertEquals(_1oExtratoInvestimento.getData(),
		// investimento.getData());
		// assertEquals(_1oExtratoInvestimento.getValorCotaData(),
		// investimento.getValorCotaData());
		assertEquals(_1oExtratoInvestimento, investimento);

		aplicacoes = new ArrayList<Aplicacao>();
		umaAplicacao = new Aplicacao(LocalDate.of(2009, 12, 23),
				new Long("114151738"), new BigDecimal("1500.00"),
				new BigDecimal("1004.856762"), new BigDecimal("557.075135"));
		aplicacoes.add(umaAplicacao);

		umaAplicacao = new Aplicacao(LocalDate.of(2011, 10, 11),
				new Long("114084543"), new BigDecimal("5500.00"),
				new BigDecimal("3014.280610"), new BigDecimal("3004.285432"));
		aplicacoes.add(umaAplicacao);
		_2oExtratoInvestimento = new ExtratoInvestimento(
				Data.obterDataDDMMAAAA(16, 3, 2012), new BigDecimal(
						"1.920457204"), aplicacoes, new FundoInvestimento(
						"08080680000102", "BB RF Pré LP Estilo"
						,new BigDecimal("0.15")
						, TipoFundoInvestimento.RendaFixa));

		investimento = extratoInvestimento.get(1);
		// assertEquals(_2oExtratoInvestimento.getFundoInvestimento(),
		// investimento.getFundoInvestimento());
		// assertEquals(_2oExtratoInvestimento.getAplicacoes().get(0),
		// investimento.getAplicacoes().get(0));
		// assertEquals(_2oExtratoInvestimento.getData(),
		// investimento.getData());
		// assertEquals(_2oExtratoInvestimento.getValorCotaData(),
		// investimento.getValorCotaData());
		assertEquals(_2oExtratoInvestimento, investimento);
	}

}
