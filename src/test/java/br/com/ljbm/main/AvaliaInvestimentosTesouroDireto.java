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
			posicao.setTipoFundoInvestimento(fundo.getTipoFundoInvestimento());
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

//						,"09/01/2019"); // supondo resgate com valor cota dia 10 para TD e valor cota dia 9 para ações
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
						
//					,"10/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
//		Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//		Agora      Tesouro IPCA+ 2024         15,58    9,49       15.251,77      289.603,86      274.352,09
//		Agora      Tesouro IPCA+ 2035         58,80   69,77       -5.681,39       82.188,99       87.870,38
//		Agora      Tesouro Prefixado 2023     22,05   10,94        2.906,78       31.927,87       29.021,09
//		Agora      Tesouro Prefixado 2025     14,61    1,65          614,98        5.438,67        4.823,69
//		BB         Tesouro IPCA+ 2024        158,60  112,67       11.215,45       63.141,10       51.925,65
//		BB         Tesouro IPCA+ 2035        125,17   94,28        9.612,45       70.072,32       60.459,87
//		BB         BB Acoes Aloc ETF FI       37,58   78,90      -30.887,70      102.854,43      133.742,13
//		BB         BB Ações BB                23,47   20,37        1.995,78       79.335,74       77.339,97
//		BB         BB Ações BDR Nivel I        0,00    0,00           -0,00       17.500,00       17.500,00
//		BB         BB Ações Const Civil      -17,08  143,47      -14.781,34        7.633,08       22.414,42
//		BB         BB Ações Consumo           70,93  100,56       -2.814,75       16.239,12       19.053,87
//		BB         BB Ações Dividendos        20,64   42,98      -10.681,58       57.670,30       68.351,87
//		BB         BB Ações Energia           55,86   62,42       -4.224,99      100.376,64      104.601,62
//		BB         BB Ações Exportação        60,52  206,86      -17.542,35       19.242,04       36.784,39
//		BB         BB Ações Petrobras          3,26  212,31      -59.145,36       29.216,56       88.361,92
//		BB         BB Ações Siderurgia       -39,07  179,25      -53.001,84       14.788,70       67.790,54
//		BB         BB Ações Vale              35,03  107,61      -37.223,34       69.247,90      106.471,24
//		                                                        -194.387,42    1.056.477,32    1.250.864,74
						
//					,"14/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
//		Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//		Agora      Tesouro IPCA+ 2024         15,63    9,54       15.243,64      289.730,84      274.487,20
//		Agora      Tesouro IPCA+ 2035         60,44   69,86       -4.871,72       83.041,94       87.913,66
//		Agora      Tesouro Prefixado 2023     22,31   10,99        2.960,12       31.995,50       29.035,37
//		Agora      Tesouro Prefixado 2025     14,81    1,70          622,27        5.448,33        4.826,06
//		BB         Tesouro IPCA+ 2024        158,72  112,77       11.217,57       63.168,78       51.951,22
//		BB         Tesouro IPCA+ 2035        127,50   94,37       10.309,87       70.799,52       60.489,65
//		BB         BB Acoes Aloc ETF FI       38,87   78,99      -29.991,55      103.816,46      133.808,01
//		BB         BB Ações BB                26,83   20,43        4.110,23       81.488,31       77.378,08
//		BB         BB Ações BDR Nivel I       -0,20    0,04          -44,77       17.463,85       17.508,62
//		BB         BB Ações Const Civil      -16,60  143,59      -14.748,08        7.677,38       22.425,46
//		BB         BB Ações Consumo           73,33  100,66       -2.596,50       16.466,76       19.063,26
//		BB         BB Ações Dividendos        22,15   43,05       -9.989,88       58.395,68       68.385,55
//		BB         BB Ações Energia           58,43   62,50       -2.618,33      102.034,82      104.653,15
//		BB         BB Ações Exportação        60,42  207,01      -17.572,47       19.230,03       36.802,50
//		BB         BB Ações Petrobras          2,06  212,47      -59.529,22       28.876,22       88.405,44
//		BB         BB Ações Siderurgia       -40,64  179,39      -53.415,36       14.408,56       67.823,92
//		BB         BB Ações Vale              33,75  107,72      -37.933,05       68.590,64      106.523,68
//		                                                        -188.847,22    1.062.633,61    1.251.480,83		

					,"15/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
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


