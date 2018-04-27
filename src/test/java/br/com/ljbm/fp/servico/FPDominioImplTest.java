package br.com.ljbm.fp.servico;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
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

	private FPDominioImpl servico;

	@BeforeClass
	public static void setUpBeforeClass() {
		em = Persistence.createEntityManagerFactory("ljbmUPTeste").createEntityManager();
	}
	
	@AfterClass
	public static void tearDownClass () {
		em.close();
	}
	
	@Before
	public void setUp() throws Exception {
		servico = new FPDominioImpl(em,
				LogManager.getFormatterLogger(FPDominioImplTest.class));
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
//		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
//		}
	}

	@Test
	public void test01AddFundoInvestimento() throws FPException {

		Corretora bB = new Corretora();
		bB.setCnpj("00000000000191");
		bB.setRazaoSocial("Banco do Brasil");
		bB.setSigla("BB");
		em.persist(bB);
		
		Corretora agora = new Corretora();
		agora.setCnpj("74014747000135");
		agora.setRazaoSocial("AGORA CTVM S.A.");
		agora.setSigla("Agora");
		em.persist(agora);
		
		Stream<FundoInvestimento> listaFundos = Stream.of(
				new FundoInvestimento("74014747000135", "Agora Prefixado 2019", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, agora),
				new FundoInvestimento("74014747000135", "Agora Prefixado 2023", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, agora),
				new FundoInvestimento("74014747000135", "Agora NTNB-2024", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, agora),
				new FundoInvestimento("74014747000135", "Agora NTNB-2035", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, agora),
				new FundoInvestimento("00000000000191", "BB NTNB-2024", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, bB),
				new FundoInvestimento("00000000000191", "BB Prefixado 2023", new BigDecimal("0.15"),
						TipoFundoInvestimento.TesouroDireto, bB)		);

		listaFundos.forEach(fundo -> {
			try {
				servico.addFundoInvestimento(fundo);
			} catch (FPException e) {
//				em.getTransaction().rollback();
				throw new RuntimeException(e);
			}
		});
		List<FundoInvestimento> lista = servico.getAllFundoInvestimento();
		lista.forEach(fundo -> {
			System.out.println(fundo.getIde());
		});
	}
	
	@Test
	public void test02LeTabela() throws FPException {
//		FundoInvestimento listaOrdenada = servico.getFundoInvestimento(1l);
		System.out.println(em.find(FundoInvestimento.class, 1l).getIde());
	}
	
	@Test
	public void test03LeCache2aNivel() {
		EntityManager em2 = Persistence.createEntityManagerFactory("ljbmUPTeste").createEntityManager();
		System.out.println(em2.find(FundoInvestimento.class, 1l).getIde());
	}

}
