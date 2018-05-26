package br.com.ljbm.fp.servico;

import java.io.File;
import java.io.IOException;
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
import org.junit.Test;

import br.com.ljbm.fp.LeitorExtratoTesouroDireto;
import br.com.ljbm.fp.PosicaoTituloPorAgente;
import br.com.ljbm.fp.modelo.ComparacaoInvestimentoVersusSELIC;
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
	public void test() throws IOException {
		
		LeitorExtratoTesouroDireto leitor = new LeitorExtratoTesouroDireto(resourcesDir.getPath() + File.separator + "cestasComprasTDTest.txt");
		leitor.le();
		
		AvaliadorInvestimentoImpl 
			avaliador = new AvaliadorInvestimentoImpl(
					leitor
				, selicService
				, new CotacaoTituloDAO()
				, log
				, servicoFPDominio);

		List<ComparacaoInvestimentoVersusSELIC> comparativo = avaliador
				.comparaInvestimentosComSELIC("25/05/2018");
		avaliador.imprimeComparacaoInvestComSELIC(comparativo);
	}

}
