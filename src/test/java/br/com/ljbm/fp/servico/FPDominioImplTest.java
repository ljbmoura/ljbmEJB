package br.com.ljbm.fp.servico;

import static br.com.ljbm.fp.modelo.Corretora.cnpjAgora;
import static br.com.ljbm.fp.modelo.Corretora.cnpjBB;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
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

	private static EntityManager em;

	private static Logger log;

	private FPDominioImpl servico;

	@BeforeClass
	public static void setUpBeforeClass() {
		em = Persistence.createEntityManagerFactory("ljbmUPTeste").createEntityManager();
		log = LogManager.getFormatterLogger(FPDominioImplTest.class);
	}

	@AfterClass
	public static void tearDownClass() {
		em.close();
	}

	@Before
	public void setUp() throws Exception {
		servico = new FPDominioImpl(em, log);
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		// if (em.getTransaction().isActive()) {
		em.getTransaction().commit();
		// }
	}

	@Test
	public void test01AddFundoInvestimento() throws FPException {

		Corretora bB = new Corretora();
		bB.setCnpj("00000000000191");
		bB.setRazaoSocial("Banco do Brasil");
		bB.setSigla("BB");
		em.persist(bB);

		Corretora agora = new Corretora();
		agora.setCnpj(cnpjAgora);
		agora.setRazaoSocial("AGORA CTVM S.A.");
		agora.setSigla("Agora");
		em.persist(agora);

		Stream<FundoInvestimento> listaFundos = Stream.of(
				new FundoInvestimento(cnpjAgora, "Agora Prefixado 2019", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, agora),
				new FundoInvestimento(cnpjAgora, "Agora Prefixado 2023", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, agora),
				new FundoInvestimento(cnpjAgora, "Agora NTNB-2024", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, agora),
				new FundoInvestimento(cnpjAgora, "Agora NTNB-2035", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, agora),
				new FundoInvestimento(cnpjBB, "BB NTNB-2024", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, bB),
				new FundoInvestimento(cnpjBB, "BB Prefixado 2023", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, bB));

		listaFundos.forEach(fundo -> {
			try {
				servico.addFundoInvestimento(fundo);
			} catch (FPException e) {
				// em.getTransaction().rollback();
				throw new RuntimeException(e);
			}
		});
		List<FundoInvestimento> lista = servico.getAllFundoInvestimento();
		lista.forEach(fundo -> {
			log.info(fundo.getIde());
		});
	}

	@Test
	public void test02LeUmRegistro() throws FPException {
		log.info(em.find(FundoInvestimento.class, 1l).getIde());
	}

	@Test
	public void test03LeCache2aNivel() {
		// EntityManager em2 =
		// Persistence.createEntityManagerFactory("ljbmUPTeste").createEntityManager();
		// log.info(em2.find(FundoInvestimento.class, 1l).getIde());
	}

}
