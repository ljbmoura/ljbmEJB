package br.com.ljbm.fp.servico;

import static br.com.ljbm.fp.modelo.Corretora.cnpjBB;
import static br.com.ljbm.fp.modelo.Corretora.cnpjAgora;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.ljbm.fp.modelo.Corretora;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.TipoFundoInvestimento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// TODO usar classes do ehcache para avaliar resultados esperados
public class FPDominioImplTest {
	private static Logger log;

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManagerFactory fabricaEMSeries;

	private static Corretora bB;
	private static Corretora agora;
	private static List<Long> idesFundosInseridos;

	private EntityManager em;
	private EntityManager emSeries;
	private FPDominioImpl servico;

	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ljbmFPTeste", null);
		fabricaEMSeries = Persistence.createEntityManagerFactory("ljbmSeries", null);
		log = LogManager.getFormatterLogger(FPDominioImplTest.class);
		idesFundosInseridos = new ArrayList<Long>();
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() throws Exception {
		em = entityManagerFactory.createEntityManager();
		emSeries = fabricaEMSeries.createEntityManager();
		servico = new FPDominioImpl(em, emSeries, log);
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
		em.close(); // ponto em que as estatísticas do ehcache são relatadas
	}

	@Test
	public void test00_PreparaBase() throws FPException {
		em.createQuery("delete from FundoInvestimento").executeUpdate();
		em.createQuery("delete from Corretora").executeUpdate();

		bB = new Corretora();
		bB.setCnpj("00000000000191");
		bB.setRazaoSocial("BB BANCO DE INVESTIMENTO S/A - 1102303");
		bB.setSigla("BB");
		servico.addCorretora(bB);

		agora = new Corretora();
		agora.setCnpj(cnpjAgora);
		agora.setRazaoSocial("AGORA CTVM S/A - 131836");
		agora.setSigla("Agora");
		servico.addCorretora(agora);
	}

	@Test
	public void test01_InsereFundosInvestimento() throws FPException {
		
		Corretora bBTransient = new Corretora();
		bBTransient.setIde(1l);
		
		Stream

				.of(
						// new FundoInvestimento(cnpjAgora, "Agora Prefixado 2019", new
						// BigDecimal("0.15"),
						// TipoFundoInvestimento.TesouroDireto, agora),
						// new FundoInvestimento(cnpjAgora, "Agora Prefixado 2023", new
						// BigDecimal("0.15"),
						// TipoFundoInvestimento.TesouroDireto, agora),
						// new FundoInvestimento(cnpjAgora, "Agora NTNB-2024", new BigDecimal("0.15"),
						// TipoFundoInvestimento.TesouroDireto, agora),
						// new FundoInvestimento(cnpjAgora, "Agora NTNB-2035", new BigDecimal("0.15"),
						// TipoFundoInvestimento.TesouroDireto, agora),
						new FundoInvestimento(cnpjBB, "BB NTNB-2024", new BigDecimal("0.15"),
								TipoFundoInvestimento.TesouroDireto, bB),
						new FundoInvestimento(cnpjBB, "Agora Prefixado 2023", new BigDecimal("0.15"),
								TipoFundoInvestimento.TesouroDireto, agora))

				.forEach(fundo -> {
					try {
						FundoInvestimento fundoPersistido = servico.addFundoInvestimento(fundo);
						em.flush();
						idesFundosInseridos.add(fundoPersistido.getIde());
					} catch (Exception e) {
						e.printStackTrace();
						em.getTransaction().rollback();
						log.info(e.getMessage());
						fail(e.getMessage());
					}
				});
	}

	@Test
	public void test02_LeFundos1aVezBD_GerandoCache() throws FPException {
		log.info("test02_LeFundos1aVezBD_GerandoCache");
		log.debug(servico.getFundoInvestimento(idesFundosInseridos.get(0)));
		log.debug(servico.getCorretora(bB.getIde()));
	}

	@Test
	public void test03_LeFundosNoCache2oNivel() throws FPException {
		log.info("test03_LeFundosNoCache2oNivel");
		log.debug(servico.getFundoInvestimento(idesFundosInseridos.get(0)));
		log.debug(servico.getCorretora(bB.getIde()));
	}

}
