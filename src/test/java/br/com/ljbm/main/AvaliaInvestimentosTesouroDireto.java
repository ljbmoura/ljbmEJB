package br.com.ljbm.main;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	public static void main(String[] args) throws Exception {
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
		
		String dataRef = "29/03/2019";
	
		List<PosicaoTituloPorAgente> extrato = new ArrayList<PosicaoTituloPorAgente>();
		LocalDate dataRefAux = LocalDate.parse(dataRef, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		List<FundoInvestimento> fundos = servicoFPDominio.getAllFundoInvestimento();
		fundos.stream().forEach(fundo -> {
			PosicaoTituloPorAgente posicao = new PosicaoTituloPorAgente();
			posicao.setAgenteCustodia(fundo.getCorretora().getSigla());
			posicao.setTipoFundoInvestimento(fundo.getTipoFundoInvestimento());
			posicao.setTitulo(fundo.getNome());
			posicao.setCompras(fundo.getAplicacoes().stream().filter(a -> ! a.getDataCompra().isAfter(dataRefAux)).collect(Collectors.toList()));
			extrato.add(posicao);
		});
		
		List<ComparacaoInvestimentoVersusSELIC> comparativo = 
				avaliador.comparaInvestimentosComSELIC(
						extrato, dataRef);
		
//25/05/2018");
//06/04/2018");
//21/06/2018");
//16/10/2018");
//                            fundo %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024       10,68    7,94        6.864,30      277.327,09      270.462,79
//Agora      Tesouro IPCA+ 2035       45,69   67,37      -11.219,30       75.405,41       86.624,71
//Agora      Tesouro Prefixado 2023   14,94    9,37        1.458,26       30.067,93       28.609,67
//Agora      Tesouro Prefixado 2025    5,28    0,21          240,80        4.996,11        4.755,30
//BB         Tesouro IPCA+ 2024      147,64  109,65        9.274,92       60.464,45       51.189,52
//BB         Tesouro IPCA+ 2035      106,58   91,52        4.686,03       64.288,80       59.602,77
//                                                        11.305,01      512.549,78      501.244,76		

//04/01/2019");
//                            fundo %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024       15,11    9,38       14.359,10      288.441,19      274.082,08
//Agora      Tesouro IPCA+ 2035       53,80   69,61       -8.179,09       79.604,82       87.783,91
//Agora      Tesouro Prefixado 2023   22,16   10,83        2.964,07       31.956,60       28.992,53
//Agora      Tesouro Prefixado 2025   14,52    1,55          615,27        5.434,21        4.818,94
//BB         Tesouro IPCA+ 2024      157,57  112,46       11.013,08       62.887,61       51.874,53
//BB         Tesouro IPCA+ 2035      118,09   94,09        7.468,75       67.869,12       60.400,37
//                                                        28.241,19      536.193,54      507.952,35

					
//10/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
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
						
//14/01/2019"); 
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

//15/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
		
//17/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
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
//18/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
//		Agora      Tesouro IPCA+ 2024         15,89    9,65       15.634,86      290.392,56      274.757,70
//		Agora      Tesouro IPCA+ 2035         61,99   70,02       -4.158,31       83.841,96       88.000,27
//		Agora      Tesouro Prefixado 2023     22,06   11,10        2.867,42       31.931,41       29.063,98
//		Agora      Tesouro Prefixado 2025     14,57    1,80          606,12        5.436,94        4.830,82
//		BB         Tesouro IPCA+ 2024        159,31  112,98       11.310,66       63.313,06       52.002,39
//		BB         Tesouro IPCA+ 2035        129,70   94,57       10.932,36       71.481,60       60.549,24
//		                                                          37.193,12      546.397,52      509.204,40
//		BB         BB Acoes Aloc ETF FI       41,10   79,16      -28.457,10      105.482,74      133.939,83
//		BB         BB Ações BB                23,56   20,55        1.936,43       79.390,74       77.454,31
//		BB         BB Ações BDR Nivel I        3,70    0,14          621,93       18.147,80       17.525,87
//		BB         BB Ações Const Civil      -14,93  143,83      -14.616,20        7.831,35       22.447,55
//		BB         BB Ações Consumo           77,47  100,86       -2.222,17       16.859,86       19.082,03
//		BB         BB Ações Dividendos        23,40   43,19       -9.463,00       58.989,93       68.452,93
//		BB         BB Ações Energia           59,25   62,66       -2.196,26      102.559,99      104.756,25
//		BB         BB Ações Exportação        64,14  207,32      -17.162,66       19.676,11       36.838,77
//		BB         BB Ações Petrobras          4,58  212,77      -58.904,18       29.588,36       88.492,53
//		BB         BB Ações Siderurgia       -40,05  179,66      -53.337,85       14.552,89       67.890,74
//		BB         BB Ações Vale              39,17  107,92      -35.256,24       71.372,39      106.628,63
//		                                                        -219.057,29      524.452,16      743.509,45
//		                                                        -181.864,17    1.070.849,68    1.252.713,85
//24/01/2019"); // supondo resgate com valor cota dia atual para TD e valor cota do dia anterior para ações
//		Agora      Tesouro IPCA+ 2024         16,30    9,76       16.391,78      291.420,09      275.028,31
//		Agora      Tesouro IPCA+ 2035         63,74   70,19       -3.336,89       84.750,08       88.086,97
//		Agora      Tesouro Prefixado 2023     23,11   11,21        3.112,39       32.205,00       29.092,62
//		Agora      Tesouro Prefixado 2025     15,73    1,90          656,39        5.491,96        4.835,57
//		BB         Tesouro IPCA+ 2024        160,23  113,19       11.483,46       63.537,08       52.053,62
//		BB         Tesouro IPCA+ 2035        132,18   94,76       11.646,95       72.255,84       60.608,89
//		                                                          39.954,07      549.660,06      509.705,99
//		BB         BB Acoes Aloc ETF FI       43,64   79,34      -26.690,94      107.380,83      134.071,77
//		BB         BB Ações BB                24,54   20,67        2.487,73       80.018,36       77.530,63
//		BB         BB Ações BDR Nivel I        3,44    0,24          559,64       18.102,77       17.543,13
//		BB         BB Ações Const Civil      -12,14  144,07      -14.381,64        8.088,03       22.469,67
//		BB         BB Ações Consumo           78,86  101,06       -2.108,74       16.992,10       19.100,84
//		BB         BB Ações Dividendos        25,75   43,33       -8.404,54       60.115,83       68.520,37
//		BB         BB Ações Energia           66,39   62,82        2.300,74      107.160,20      104.859,46
//		BB         BB Ações Exportação        68,01  207,62      -16.735,27       20.139,79       36.875,06
//		BB         BB Ações Petrobras          5,50  213,08      -58.730,52       29.849,19       88.579,71
//		BB         BB Ações Siderurgia       -38,96  179,94      -53.142,31       14.815,31       67.957,62
//		BB         BB Ações Vale              42,65  108,12      -33.578,93       73.154,75      106.733,68
//		                                                        -208.424,76      535.817,17      744.241,93
//		                                                        -168.470,69    1.085.477,23    1.253.947,92
//14/02/2019"); 
//15/02/2019"); 
//21/02/2019"); 

// "25/02/2019" 
//		Agora      Tesouro IPCA+ 2024         17,60   10,36       18.162,58      294.684,42      276.521,84
//		Agora      Tesouro IPCA+ 2035         66,89   71,12       -2.184,80       86.380,53       88.565,33
//		Agora      Tesouro Prefixado 2023     24,65   11,82        3.356,18       32.606,78       29.250,61
//		Agora      Tesouro Prefixado 2025     17,44    2,45          711,03        5.572,86        4.861,84
//		BB         Tesouro IPCA+ 2024        163,14  114,35       11.912,49       64.248,79       52.336,30
//		BB         Tesouro IPCA+ 2035         92,69   64,86       12.837,31       88.881,42       76.044,11
//		BB         Tesouro Prefixado 2025      0,65    0,46          117,10       60.389,99       60.272,89
//		                                                          44.911,88      632.764,79      587.852,91
//		BB         BB Acoes Aloc ETF FI       43,54   80,31      -27.489,31      107.310,54      134.799,85
//		BB         BB Ações BB                34,16   21,32        8.251,12       86.202,76       77.951,64
//		BB         BB Ações BDR Nivel I        8,01    0,79        1.264,34       18.902,75       17.638,41
//		BB         BB Ações Const Civil      -14,44  145,40      -14.715,12        7.876,57       22.591,69
//		BB         BB Ações Consumo           78,25  102,15       -2.270,40       16.934,15       19.204,56
//		BB         BB Ações Dividendos        25,43   44,11       -8.930,25       59.962,20       68.892,45
//		BB         BB Ações Energia           68,46   63,70        3.059,84      108.488,73      105.428,89
//		BB         BB Ações Exportação        65,80  209,29      -17.200,10       19.875,20       37.075,30
//		BB         BB Ações Infra             -0,18    0,39          -86,31       14.972,88       15.059,19
//		BB         BB Ações Muilt Setor       -2,40    0,39         -420,06       14.639,13       15.059,19
//		BB         BB Ações Petrobras          9,29  214,78      -58.138,73       30.922,01       89.060,74
//		BB         BB Ações Setor Financ      -0,16    0,39          -84,35       14.974,84       15.059,19
//		BB         BB Ações Siderurgia       -39,77  181,46      -53.707,98       14.618,69       68.326,67
//		BB         BB Ações Vale              19,67  109,25      -45.942,19       61.371,09      107.313,29
//        														-216.409,50      577.051,55      793.461,05
//        														-171.497,63    1.209.816,33    1.381.313,96		
		
//"29/03/2019" 
//		Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//		Agora      Tesouro IPCA+ 2024         19,00   10,95       20.164,08      298.187,58      278.023,50
//		Agora      Tesouro IPCA+ 2035         68,84   72,05       -1.658,55       87.387,73       89.046,29
//		Agora      Tesouro Prefixado 2023     25,42   12,42        3.401,10       32.810,54       29.409,44
//		Agora      Tesouro Prefixado 2025     18,31    3,01          725,80        5.614,04        4.888,24
//		BB         Tesouro IPCA+ 2024        166,27  115,51       12.392,07       65.012,57       52.620,50
//		BB         Tesouro IPCA+ 2035         66,16   45,88       13.409,05      109.863,30       96.454,25
//		BB         Tesouro Prefixado 2025      0,98    0,58          437,44      111.079,06      110.641,62
//		                                                          48.870,99      709.954,82      661.083,84
//		BB         BB Acoes Aloc ETF FI       38,19   76,20      -30.320,40      110.216,37      140.536,77
//		BB         BB Ações BB                22,15   19,03        2.321,78       90.701,62       88.379,84
//		BB         BB Ações BDR Nivel I       14,36    1,33        2.278,88       20.013,07       17.734,19
//		BB         BB Ações Const Civil      -18,16  146,73      -15.181,09        7.533,27       22.714,37
//		BB         BB Ações Consumo           72,85  103,25       -2.887,60       16.421,25       19.308,85
//		BB         BB Ações Dividendos        21,58   44,89      -11.147,04       58.119,51       69.266,55
//		BB         BB Ações Energia           53,59   51,11        2.014,72      125.022,99      123.008,28
//		BB         BB Ações Exportação        69,26  210,97      -16.987,12       20.289,52       37.276,64
//		BB         BB Ações Infra             -1,88    0,72         -522,29       19.623,61       20.145,90
//		BB         BB Ações Muilt Setor       -1,48    0,72         -443,24       19.702,65       20.145,90
//		BB         BB Ações Petrobras         12,33  216,49      -57.762,87       31.781,50       89.544,37
//		BB         BB Ações Setor Financ      -2,90    0,56         -866,34       24.274,63       25.140,97
//		BB         BB Ações Siderurgia       -38,93  182,99      -53.873,53       14.824,17       68.697,71
//		BB         BB Ações Vale              22,55   85,42      -41.669,26       81.231,69      122.900,95
//		                                                        -225.045,40      639.755,86      864.801,26
//		                                                        -176.174,41    1.349.710,69    1.525.885,10
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


