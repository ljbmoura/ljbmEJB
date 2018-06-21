package br.com.ljbm.fp.servico;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import br.com.ljbm.fp.LeitorExtratoTesouroDireto;
import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.fp.modelo.ComparacaoInvestimentoVersusSELIC;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.PosicaoTituloPorAgente;
import br.com.ljbm.utilitarios.Recurso;
import br.com.ljbm.ws.bc.Selic;

public class AvaliadorInvestimentoImplTest {
	
	private static Logger log;

	private static EntityManagerFactory entityManagerFactory;
	
	private static File resourcesDir;

	private static EntityManager em;
	
	private Selic selicService;

	private FPDominio servicoFPDominio;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("ljbmUPTeste", null);
		log = LogManager.getFormatterLogger(AvaliadorInvestimentoImplTest.class);
		resourcesDir = Recurso.getPastaRecursos(AvaliadorInvestimentoImplTest.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Before
	public void setUp() throws Exception {
		selicService = new Selic(log);
		em = entityManagerFactory.createEntityManager();
		servicoFPDominio = new FPDominioImpl(em, log);
//		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
//		em.getTransaction().commit();
	}

	@Test
	@Ignore
	public void test() throws IOException {
		
		AvaliadorInvestimentoImpl 
			avaliador = new AvaliadorInvestimentoImpl(
				selicService
				, new CotacaoTituloDAO()
				, log
				, servicoFPDominio);

		List<ComparacaoInvestimentoVersusSELIC> comparativo = avaliador
				.comparaInvestimentosComSELIC(
						new LeitorExtratoTesouroDireto()
							.caminhoArquivoExtratoTD(resourcesDir.getPath()+ File.separator + "cestasComprasTDTest.txt")
							.le()
						,"25/05/2018");
		avaliador.imprimeComparacaoInvestComSELIC(comparativo);
	}

	@Test
	public void test2() throws IOException {
		
		AvaliadorInvestimentoImpl 
			avaliador = new AvaliadorInvestimentoImpl(
				selicService
				, new CotacaoTituloDAO()
				, log
				, servicoFPDominio);
		
		List<PosicaoTituloPorAgente> extrato = new ArrayList<PosicaoTituloPorAgente>(); 
		List<FundoInvestimento> fundos = servicoFPDominio.getAllFundoInvestimento();
		fundos.stream().forEach(fundo -> {
//			fundo.getAplicacoes().stream().forEach(aplicacao -> {
//				log.debug(aplicacao);
//			});
			PosicaoTituloPorAgente posicao = new PosicaoTituloPorAgente();
			posicao.setAgenteCustodia(fundo.getCorretora().getRazaoSocial());
			posicao.setTitulo(fundo.getNome());
			posicao.setCompras(fundo.getAplicacoes());
			extrato.add(posicao);
		});
		List<ComparacaoInvestimentoVersusSELIC> comparativo = 
				avaliador.comparaInvestimentosComSELIC(
						extrato
						,"06/04/2018");
		avaliador.imprimeComparacaoInvestComSELIC(comparativo);
	}
}

