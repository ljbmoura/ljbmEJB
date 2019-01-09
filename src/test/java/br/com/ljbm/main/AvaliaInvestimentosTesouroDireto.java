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
			, new CotacaoTituloDAO(log)
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
//						,"16/10/2018");
//                            fundo %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024       10,68    7,94        6.864,30      277.327,09      270.462,79
//Agora      Tesouro IPCA+ 2035       45,69   67,37      -11.219,30       75.405,41       86.624,71
//Agora      Tesouro Prefixado 2023   14,94    9,37        1.458,26       30.067,93       28.609,67
//Agora      Tesouro Prefixado 2025    5,28    0,21          240,80        4.996,11        4.755,30
//BB         Tesouro IPCA+ 2024      147,64  109,65        9.274,92       60.464,45       51.189,52
//BB         Tesouro IPCA+ 2035      106,58   91,52        4.686,03       64.288,80       59.602,77
//                                                        11.305,01      512.549,78      501.244,76		

						,"04/01/2019");
//                            fundo %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024       15,11    9,38       14.359,10      288.441,19      274.082,08
//Agora      Tesouro IPCA+ 2035       53,80   69,61       -8.179,09       79.604,82       87.783,91
//Agora      Tesouro Prefixado 2023   22,16   10,83        2.964,07       31.956,60       28.992,53
//Agora      Tesouro Prefixado 2025   14,52    1,55          615,27        5.434,21        4.818,94
//BB         Tesouro IPCA+ 2024      157,57  112,46       11.013,08       62.887,61       51.874,53
//BB         Tesouro IPCA+ 2035      118,09   94,09        7.468,75       67.869,12       60.400,37
//                                                        28.241,19      536.193,54      507.952,35
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


