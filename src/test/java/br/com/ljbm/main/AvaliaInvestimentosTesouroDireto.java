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

					
//					,"10/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
//		Agora      Tesouro IPCA+ 2024         15,58    9,49       15.251,77      289.603,86      274.352,09
//		Agora      Tesouro IPCA+ 2035         58,80   69,77       -5.681,39       82.188,99       87.870,38
//		Agora      Tesouro Prefixado 2023     22,05   10,94        2.906,78       31.927,87       29.021,09
//		Agora      Tesouro Prefixado 2025     14,61    1,65          614,98        5.438,67        4.823,69
//		BB         Tesouro IPCA+ 2024        158,60  112,67       11.215,45       63.141,10       51.925,65
//		BB         Tesouro IPCA+ 2035        125,17   94,28        9.612,45       70.072,32       60.459,87
//		                                                          33.920,05      542.372,81      508.452,75
//		BB         BB Acoes Aloc ETF FI       37,58   78,90      -30.887,69      102.854,43      133.742,12
//		BB         BB Ações BB                23,47   20,37        1.995,78       79.335,74       77.339,97
//		BB         BB Ações BDR Nivel I        0,00    0,00            0,00       17.500,00       17.500,00
//		BB         BB Ações Const Civil      -17,08  143,47      -14.781,34        7.633,08       22.414,42
//		BB         BB Ações Consumo           70,93  100,56       -2.814,75       16.239,12       19.053,87
//		BB         BB Ações Dividendos        20,64   42,98      -10.681,58       57.670,30       68.351,87
//		BB         BB Ações Energia           55,86   62,42       -4.224,99      100.376,64      104.601,62
//		BB         BB Ações Exportação        60,52  206,86      -17.542,35       19.242,04       36.784,39
//		BB         BB Ações Petrobras          3,26  212,31      -59.145,36       29.216,56       88.361,92
//		BB         BB Ações Siderurgia       -39,07  179,25      -53.001,84       14.788,70       67.790,54
//		BB         BB Ações Vale              35,03  107,61      -37.223,34       69.247,90      106.471,24
//		                                                        -228.307,46      514.104,51      742.411,97
//		                                                        -194.387,41    1.056.477,32    1.250.864,73
						
//					,"14/01/2019"); 
//		Agora      Tesouro IPCA+ 2024         15,63    9,54       15.243,64      289.730,84      274.487,20
//		Agora      Tesouro IPCA+ 2035         60,44   69,86       -4.871,72       83.041,94       87.913,66
//		Agora      Tesouro Prefixado 2023     22,31   10,99        2.960,12       31.995,50       29.035,37
//		Agora      Tesouro Prefixado 2025     14,81    1,70          622,27        5.448,33        4.826,06
//		BB         Tesouro IPCA+ 2024        158,72  112,77       11.217,57       63.168,78       51.951,22
//		BB         Tesouro IPCA+ 2035        127,50   94,37       10.309,87       70.799,52       60.489,65
//		                                                          35.481,75      544.184,90      508.703,16
//		BB         BB Acoes Aloc ETF FI       38,87   78,99      -29.991,55      103.816,46      133.808,01
//		BB         BB Ações BB                26,83   20,43        4.110,23       81.488,31       77.378,08
//		BB         BB Ações BDR Nivel I       -0,20    0,04          -44,76       17.463,85       17.508,61
//		BB         BB Ações Const Civil      -16,60  143,59      -14.748,08        7.677,38       22.425,46
//		BB         BB Ações Consumo           73,33  100,66       -2.596,50       16.466,76       19.063,26
//		BB         BB Ações Dividendos        22,15   43,05       -9.989,88       58.395,68       68.385,55
//		BB         BB Ações Energia           58,43   62,50       -2.618,33      102.034,82      104.653,15
//		BB         BB Ações Exportação        60,42  207,01      -17.572,47       19.230,03       36.802,50
//		BB         BB Ações Petrobras          2,06  212,47      -59.529,22       28.876,22       88.405,44
//		BB         BB Ações Siderurgia       -40,64  179,39      -53.415,36       14.408,56       67.823,92
//		BB         BB Ações Vale              33,75  107,72      -37.933,05       68.590,64      106.523,68
//		                                                        -224.328,95      518.448,71      742.777,66
//		                                                        -188.847,21    1.062.633,61    1.251.480,82

//					,"15/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
		
					,"17/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
//		Agora      Tesouro IPCA+ 2024         15,92    9,62       15.764,34      290.454,31      274.689,96
//		Agora      Tesouro IPCA+ 2035         61,85   69,98       -4.210,96       83.767,64       87.978,61
//		Agora      Tesouro Prefixado 2023     21,85   11,07        2.818,89       31.875,71       29.056,83
//		Agora      Tesouro Prefixado 2025     14,25    1,78          591,73        5.421,36        4.829,63
//		BB         Tesouro IPCA+ 2024        159,36  112,93       11.336,92       63.326,52       51.989,59
//		BB         Tesouro IPCA+ 2035        129,49   94,52       10.883,90       71.418,24       60.534,34
//		                                                          37.184,82      546.263,78      509.078,96
//		BB         BB Acoes Aloc ETF FI       39,96   79,12      -29.273,90      104.632,94      133.906,84
//		BB         BB Ações BB                24,61   20,52        2.626,90       80.062,12       77.435,22
//		BB         BB Ações BDR Nivel I        2,49    0,12          415,76       17.937,32       17.521,56
//		BB         BB Ações Const Civil      -15,19  143,77      -14.635,14        7.806,89       22.442,03
//		BB         BB Ações Consumo           76,60  100,81       -2.299,70       16.777,64       19.077,34
//		BB         BB Ações Dividendos        22,85   43,16       -9.709,70       58.726,36       68.436,06
//		BB         BB Ações Energia           58,39   62,62       -2.726,42      102.004,03      104.730,45
//		BB         BB Ações Exportação        63,10  207,24      -17.278,66       19.551,03       36.829,69
//		BB         BB Ações Petrobras          3,37  212,70      -59.224,55       29.246,20       88.470,75
//		BB         BB Ações Siderurgia       -40,58  179,60      -53.450,47       14.423,56       67.874,03
//		BB         BB Ações Vale              37,84  107,87      -35.912,55       70.689,82      106.602,37
//		                                                        -221.468,44      521.857,91      743.326,35
//		                                                        -184.283,62    1.068.121,69    1.252.405,31
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


