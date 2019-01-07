package br.com.ljbm.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.ljbm.fp.modelo.ComparacaoInvestimentoVersusSELIC;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.PosicaoTituloPorAgente;
import br.com.ljbm.fp.servico.AvaliadorInvestimentoImpl;
import br.com.ljbm.fp.servico.CotacaoTituloDAO;
import br.com.ljbm.fp.servico.FPDominio;
import br.com.ljbm.fp.servico.FPDominioImpl;
import br.com.ljbm.ws.bc.Selic;

public class AvaliaInvestimentosTesouroDireto {
	
	private static Logger log;

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManagerFactory fabricaEMSeries;
	
//	private  File resourcesDir;

	private static  EntityManager em;
	private static  EntityManager emSeries;
	
	private static Selic selicService;

	private static FPDominio servicoFPDominio;

	public static void main(String[] args) throws IOException {
		log = LogManager.getFormatterLogger(AvaliaInvestimentosTesouroDireto.class);
//		resourcesDir = Recurso.getPastaRecursos(AvaliaInvestimentosTesouroDireto.class);
		
		selicService = new Selic(log);

		entityManagerFactory = Persistence.createEntityManagerFactory("ljbmFPTeste", null);
		em = entityManagerFactory.createEntityManager();
		fabricaEMSeries = Persistence.createEntityManagerFactory("ljbmSeries", null);
		emSeries = fabricaEMSeries.createEntityManager();
		
		servicoFPDominio = new FPDominioImpl(em, emSeries, log);
		
		em.getTransaction().begin();
		emSeries.getTransaction().begin();
		
		AvaliadorInvestimentoImpl 
		avaliador = new AvaliadorInvestimentoImpl(
			selicService
			, new CotacaoTituloDAO()
			, log
			, servicoFPDominio);
	
		List<PosicaoTituloPorAgente> extrato = new ArrayList<PosicaoTituloPorAgente>(); 
		List<FundoInvestimento> fundos = servicoFPDominio.getAllFundoInvestimento();
		fundos.stream().forEach(fundo -> {
			PosicaoTituloPorAgente posicao = new PosicaoTituloPorAgente();
			posicao.setAgenteCustodia(fundo.getCorretora().getSigla());
			posicao.setTitulo(fundo.getNome());
			posicao.setCompras(fundo.getAplicacoes());
			extrato.add(posicao);
		});
		List<ComparacaoInvestimentoVersusSELIC> comparativo = 
				avaliador.comparaInvestimentosComSELIC(
						extrato
//						,"25/05/2018");
//						,"06/04/2018");
//						,"21/06/2018");
						,"04/01/2019");
		avaliador.imprimeComparacaoInvestComSELIC(comparativo);
			
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
		em.close(); // ponto em que as estatísticas do ehcache são relatadas
		if (emSeries.getTransaction().isActive()) {
			emSeries.getTransaction().commit();
		}		
		emSeries.close();
		System.exit(0);
	}
}


