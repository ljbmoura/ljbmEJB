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

//						,"04/01/2019");
//                            fundo %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024       15,11    9,38       14.359,10      288.441,19      274.082,08
//Agora      Tesouro IPCA+ 2035       53,80   69,61       -8.179,09       79.604,82       87.783,91
//Agora      Tesouro Prefixado 2023   22,16   10,83        2.964,07       31.956,60       28.992,53
//Agora      Tesouro Prefixado 2025   14,52    1,55          615,27        5.434,21        4.818,94
//BB         Tesouro IPCA+ 2024      157,57  112,46       11.013,08       62.887,61       51.874,53
//BB         Tesouro IPCA+ 2035      118,09   94,09        7.468,75       67.869,12       60.400,37
//                                                        28.241,19      536.193,54      507.952,35

						,"09/01/2019"); // supondo resgate com valor cota dia 10 para TD e valor cota dia 9 para ações
//		corretora  fundo                    %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//		Agora      Tesouro IPCA+ 2024         15,45    9,46       15.010,56      289.295,13      274.284,57
//		Agora      Tesouro IPCA+ 2035         58,80   69,73       -5.659,77       82.188,99       87.848,76
//		Agora      Tesouro Prefixado 2023     22,19   10,91        2.949,29       31.963,23       29.013,94
//		Agora      Tesouro Prefixado 2025     14,80    1,63          625,10        5.447,60        4.822,50
//		BB         Tesouro IPCA+ 2024        158,33  112,62       11.160,92       63.073,79       51.912,86
//		BB         Tesouro IPCA+ 2035        125,17   94,23        9.627,33       70.072,32       60.444,99
//		BB         BB Acoes Aloc ETF FI       37,26   78,87      -31.104,50      102.617,01      133.721,52
//		BB         BB Ações BB                21,71   20,36          869,60       78.204,07       77.334,47
//		BB         BB Ações Const Civil      -17,02  143,41      -14.770,66        7.638,24       22.408,90
//		BB         BB Ações Consumo           68,98  100,51       -2.995,27       16.053,91       19.049,18
//		BB         BB Ações Dividendos        20,28   42,97      -10.846,16       57.498,12       68.344,29
//		BB         BB Ações Energia           54,24   62,39       -5.252,31       99.332,80      104.585,11
//		BB         BB Ações Exportação        60,83  206,79      -17.495,78       19.279,56       36.775,34
//		BB         BB Ações Petrobras          3,88  212,24      -58.948,27       29.391,91       88.340,18
//		BB         BB Ações Siderurgia       -39,05  179,18      -52.979,72       14.794,12       67.773,85
//		BB         BB Ações Vale              36,53  107,57      -36.433,03       70.018,16      106.451,19
//		                                                        -196.242,67    1.036.868,96    1.233.111,63		
		
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


