package br.com.ljbm.fp.servico;

import static br.com.ljbm.fp.modelo.Corretora.cnpjBB;
import static br.com.ljbm.fp.modelo.Corretora.cnpjAgora;

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
public class FPDominioImplTest {
	private static Logger log;

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager em;
	
	private static Corretora bB;
	private static Corretora agora;
	private static List<Long> idesfundosInseridos;


	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ljbmUPTeste", null);
		log = LogManager.getFormatterLogger(FPDominioImplTest.class);
		idesfundosInseridos = new ArrayList<Long>();
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() throws Exception {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void test00LimpaBase() throws FPException {
		em.createQuery("delete from FundoInvestimento").executeUpdate();
		em.createQuery("delete from Corretora").executeUpdate();
		
		FPDominioImpl servico = new FPDominioImpl(em, log);

		bB = new Corretora();
		bB.setCnpj("00000000000191");
		bB.setRazaoSocial("Banco do Brasil");
		bB.setSigla("BB");
		servico.addCorretora(bB);
		
		agora = new Corretora();
		agora.setCnpj(cnpjAgora);
		agora.setRazaoSocial("AGORA CTVM S.A.");
		agora.setSigla("Agora");
		servico.addCorretora(agora);
	}
	
	@Test
	public void test01AddFundoInvestimento() throws FPException {
		FPDominioImpl servico = new FPDominioImpl(em, log);

		Stream<FundoInvestimento> listaFundos = Stream.of(
//				new FundoInvestimento(cnpjAgora, "Agora Prefixado 2019", new BigDecimal("0.15"),
//						TipoFundoInvestimento.TesouroDireto, agora),
//				new FundoInvestimento(cnpjAgora, "Agora Prefixado 2023", new BigDecimal("0.15"),
//						TipoFundoInvestimento.TesouroDireto, agora),
//				new FundoInvestimento(cnpjAgora, "Agora NTNB-2024", new BigDecimal("0.15"),
//						TipoFundoInvestimento.TesouroDireto, agora),
//				new FundoInvestimento(cnpjAgora, "Agora NTNB-2035", new BigDecimal("0.15"),
//						TipoFundoInvestimento.TesouroDireto, agora),
				new FundoInvestimento(cnpjBB, "BB NTNB-2024", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, bB),
				new FundoInvestimento(cnpjBB, "Agora Prefixado 2023", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, agora));

		listaFundos.forEach(fundo -> {
			try {
				idesfundosInseridos.add(servico.addFundoInvestimento(fundo).getIde());
			} catch (FPException e) {
				em.getTransaction().rollback();
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	public void test02LeBDSemCache() throws FPException {
		log.info("test02LeCache1oNivel");
		FPDominioImpl servico = new FPDominioImpl(em, log);
		log.info(servico.getFundoInvestimento(idesfundosInseridos.get(0)));
		log.info(servico.getCorretora(bB.getIde()));
	}

	@Test
	public void test03LeCache2oNivel() throws FPException {
		log.info("test03LeCache2oNivel");
		FPDominioImpl servico = new FPDominioImpl(em, log);
		log.info(servico.getFundoInvestimento(idesfundosInseridos.get(0)));
		log.info(servico.getCorretora(bB.getIde()));
	}
	
}
