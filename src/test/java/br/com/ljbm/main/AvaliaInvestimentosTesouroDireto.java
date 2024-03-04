package br.com.ljbm.main;

import java.math.BigDecimal;
import java.time.LocalDate;
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
//import br.com.ljbm.fp.servico.CotacaoTituloDAO;
import br.com.ljbm.fp.servico.FPDominioImpl;
import br.com.ljbm.ws.bc.Selic;

public class AvaliaInvestimentosTesouroDireto {
	
	private static Logger log;

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManagerFactory fabricaEMSeries;
	
	private static  EntityManager em;
	private static  EntityManager emSeries;
	
	private static Selic selicService;

	private static FPDominioImpl servicoFPDominio;

	public static void main(String[] args) throws Exception {
		log = LogManager.getFormatterLogger(AvaliaInvestimentosTesouroDireto.class);
		
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
//			, new CotacaoTituloDAO(log, servicoFPDominio)
			, log
			, servicoFPDominio);
		
		String dataRef = "01/03/2024";
		
//		put("Tesouro Prefixado 2025", 	"917,87");
//		put("Tesouro Prefixado 2026", 	"837,04");
//		put("Tesouro Prefixado 2029", 	"616,34");
//		put("Tesouro IPCA+ 2024", 		"4.065,94"); // valores para resgate
//		put("Tesouro IPCA+ 2029", 		"3.165,18");
//		put("Tesouro IPCA+ 2035", 		"2.266,04");
//		put("Tesouro RENDA+ AE 2035", 	"1.380,89");
//		put("Tesouro RENDA+ AE 2040", 	"1.040,17");
//
//		put("BB Ações Energia",			"16,798465000");
//		put("BB Ações Dividendos", 		"19,468146369");
//		put("BB Ações Exportação",		"12,707916996");
//		put("BB Ações Muilt Setor",		"2,745426937"); // Ações Seleção Fator
//		put("BB Acoes Aloc ETF FI", 	"6,174183359"); // Ações Alocação ETF
//		put("BB Ações Consumo", 		"1,738958557");
//		put("BB Ações Setor Financ",	"3,599815322");
//		put("BB Ações Siderurgia", 		"1,335112831");
//		put("BB Ações BB", 				"4,575848993");
//		put("BB Ações Const Civil", 	"1,225911474");
//		put("BB Ações Infra", 			"1,429992821");
//		put("BB Ações BDR Nivel I", 	"2,605431935"); // Ações Globais BDR I
//
//		put("BB Ações Vale",			"26,622579917");
//		put("BB Ações Petrobras",		"26,500672000");
//
//		gravar cotações
		
		
	
		List<PosicaoTituloPorAgente> extrato = new ArrayList<PosicaoTituloPorAgente>();
		LocalDate dataRefAux = LocalDate.parse(dataRef, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		List<FundoInvestimento> fundos = servicoFPDominio.getAllFundoInvestimento();
		fundos.stream().forEach(fundo -> {
			PosicaoTituloPorAgente posicao = new PosicaoTituloPorAgente();
			posicao.setAgenteCustodia(fundo.getCorretora().getSigla());
			posicao.setTipoFundoInvestimento(fundo.getTipoFundoInvestimento());
			posicao.setTitulo(fundo.getNomeAbreviado());
			posicao.setCompras(fundo.getAplicacoes().stream().filter(a -> ! a.getDataCompra().isAfter(dataRefAux) && a.getSaldoCotas().compareTo(BigDecimal.ZERO) > 0)
				.collect(Collectors.toList()));
			if (posicao.getCompras().size() > 0) {
				extrato.add(posicao);
			}
		});
		
		List<ComparacaoInvestimentoVersusSELIC> comparativo = avaliador.comparaInvestimentosComSELIC(extrato, dataRef);
		
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


//25/05/2018");
//06/04/2018");
//21/06/2018");
//16/10/2018");
//                          fundo %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024       10,68    7,94        6.864,30      277.327,09      270.462,79
//Agora      Tesouro IPCA+ 2035       45,69   67,37      -11.219,30       75.405,41       86.624,71
//Agora      Tesouro Prefixado 2023   14,94    9,37        1.458,26       30.067,93       28.609,67
//Agora      Tesouro Prefixado 2025    5,28    0,21          240,80        4.996,11        4.755,30
//BB         Tesouro IPCA+ 2024      147,64  109,65        9.274,92       60.464,45       51.189,52
//BB         Tesouro IPCA+ 2035      106,58   91,52        4.686,03       64.288,80       59.602,77
//                                                      11.305,01      512.549,78      501.244,76		

//04/01/2019");
//                          fundo %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024       15,11    9,38       14.359,10      288.441,19      274.082,08
//Agora      Tesouro IPCA+ 2035       53,80   69,61       -8.179,09       79.604,82       87.783,91
//Agora      Tesouro Prefixado 2023   22,16   10,83        2.964,07       31.956,60       28.992,53
//Agora      Tesouro Prefixado 2025   14,52    1,55          615,27        5.434,21        4.818,94
//BB         Tesouro IPCA+ 2024      157,57  112,46       11.013,08       62.887,61       51.874,53
//BB         Tesouro IPCA+ 2035      118,09   94,09        7.468,75       67.869,12       60.400,37
//                                                      28.241,19      536.193,54      507.952,35

					
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

//"25/02/2019" 
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
//      														-216.409,50      577.051,55      793.461,05
//      														-171.497,63    1.209.816,33    1.381.313,96		
		
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

//"05/04/2019" 
//Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024         19,30   11,09       20.561,42      298.927,35      278.365,93
//Agora      Tesouro IPCA+ 2035         69,27   72,26       -1.543,56       87.612,37       89.155,93
//Agora      Tesouro Prefixado 2023     25,62   12,56        3.415,70       32.861,37       29.445,67
//Agora      Tesouro Prefixado 2025     18,23    3,14          716,23        5.610,48        4.894,26
//BB         Tesouro IPCA+ 2024        166,93  115,78       12.488,55       65.173,86       52.685,31
//BB         Tesouro IPCA+ 2035         66,59   46,06       13.572,66      110.145,71       96.573,05
//BB         Tesouro Prefixado 2025      0,92    0,71          230,90      111.008,77      110.777,87
//                                                          49.441,90      711.339,92      661.898,01
//BB         BB Acoes Aloc ETF FI       40,68   76,42      -28.506,34      112.203,53      140.709,87
//BB         BB Ações BB                23,11   19,17        2.923,99       91.412,72       88.488,72
//BB         BB Ações BDR Nivel I       16,43    1,46        2.620,08       20.376,10       17.756,03
//BB         BB Ações Const Civil      -17,38  147,03      -15.136,47        7.605,88       22.742,35
//BB         BB Ações Consumo           76,44  103,50       -2.570,00       16.762,64       19.332,63
//BB         BB Ações Dividendos        24,41   45,07       -9.879,14       59.472,73       69.351,88
//BB         BB Ações Energia           56,78   51,30        4.463,11      127.622,91      123.159,80
//BB         BB Ações Exportação        72,77  211,35      -16.612,16       20.710,39       37.322,55
//BB         BB Ações Infra             -0,97    0,85         -365,18       19.805,54       20.170,72
//BB         BB Ações Muilt Setor        1,44    0,85          118,50       20.289,22       20.170,72
//BB         BB Ações Petrobras         15,40  216,88      -57.004,00       32.650,66       89.654,66
//BB         BB Ações Setor Financ      -1,26    0,68         -487,75       24.684,20       25.171,95
//BB         BB Ações Siderurgia       -38,06  183,34      -53.746,53       15.035,79       68.782,31
//BB         BB Ações Vale              25,02   85,64      -40.184,82       82.867,52      123.052,33
//                                                        -214.366,71      651.499,81      865.866,52
//                                                        -164.924,81    1.362.839,73    1.527.764,53

// 11/06/2019
//Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024         25,81   12,33       33.780,00      315.246,67      281.466,67
//Agora      Tesouro IPCA+ 2035         88,16   74,18        7.237,56       97.386,61       90.149,05
//Agora      Tesouro Prefixado 2023     32,30   13,82        4.835,37       34.609,04       29.773,67
//Agora      Tesouro Prefixado 2025     27,34    4,29        1.094,07        6.042,85        4.948,77
//BB         Tesouro IPCA+ 2024        181,50  118,18       15.459,71       68.731,89       53.272,19
//BB         Tesouro IPCA+ 2035         85,17   47,68       24.785,03      122.433,83       97.648,80
//BB         Tesouro Prefixado 2025      8,69    1,83        7.551,58      119.563,43      112.011,85
//                                                          94.743,32      764.014,32      669.271,00
//BB         BB Acoes Aloc ETF FI       42,59   78,38      -28.545,81      113.731,47      142.277,28
//BB         BB Ações BB                33,10   20,50        9.357,78       98.832,20       89.474,42
//BB         BB Ações BDR Nivel I       13,79    2,59        1.960,74       19.914,55       17.953,81
//BB         BB Ações Const Civil       -7,10  149,79      -14.444,05        8.551,62       22.995,67
//BB         BB Ações Consumo           91,12  105,76       -1.390,86       18.157,12       19.547,98
//BB         BB Ações Dividendos        29,58   46,69       -8.180,11       61.944,29       70.124,40
//BB         BB Ações Energia           69,19   52,98       13.196,03      137.727,73      124.531,70
//BB         BB Ações Exportação        67,94  214,82      -17.606,94       20.131,35       37.738,29
//BB         BB Ações Infra             -0,64    1,97         -523,80       19.871,60       20.395,40
//BB         BB Ações Muilt Setor        5,49    1,97          704,17       21.099,56       20.395,40
//BB         BB Ações Petrobras          8,23  220,41      -60.029,86       30.623,48       90.653,34
//BB         BB Ações Setor Financ       2,98    1,80          294,68       25.747,02       25.452,34
//BB         BB Ações Siderurgia       -40,30  186,49      -55.056,30       14.492,19       69.548,49
//BB         BB Ações Vale              23,22   87,71      -42.747,22       81.675,82      124.423,05
//                                                        -203.011,58      672.499,99      875.511,56
//                                                        -108.268,25    1.436.514,31    1.544.782,56
// 21/06/2019
//Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024         27,40   12,52       37.270,80      319.222,82      281.952,01
//Agora      Tesouro IPCA+ 2035         91,00   74,48        8.550,38       98.854,92       90.304,54
//Agora      Tesouro Prefixado 2023     34,00   14,01        5.229,13       35.054,14       29.825,01
//Agora      Tesouro Prefixado 2025     29,72    4,47        1.198,32        6.155,63        4.957,31
//BB         Tesouro IPCA+ 2024        185,05  118,56       16.234,73       69.598,79       53.364,07
//BB         Tesouro IPCA+ 2035         87,96   47,94       26.462,57      124.279,77       97.817,20
//BB         Tesouro Prefixado 2025     10,72    2,00        9.589,87      121.794,93      112.205,06
//                                                         104.535,80      774.960,99      670.425,19
//BB         BB Acoes Aloc ETF FI       46,59   78,69      -25.602,08      116.920,59      142.522,67
//BB         BB Ações BB                33,53   20,71        9.517,64       99.146,39       89.628,74
//BB         BB Ações BDR Nivel I       14,85    2,77        2.114,15       20.098,94       17.984,79
//BB         BB Ações Const Civil       -0,16  150,22      -13.844,43        9.190,91       23.035,34
//BB         BB Ações Consumo           97,17  106,12         -850,10       18.731,59       19.581,70
//BB         BB Ações Dividendos        31,93   46,94       -7.177,04       63.068,31       70.245,36
//BB         BB Ações Energia           69,29   53,25       13.060,96      137.807,45      124.746,49
//BB         BB Ações Exportação        74,31  215,36      -16.908,41       20.894,97       37.803,38
//BB         BB Ações Infra              3,28    2,15          227,06       20.657,64       20.430,58
//BB         BB Ações Muilt Setor        7,62    2,15        1.094,70       21.525,28       20.430,58
//BB         BB Ações Petrobras         14,14  220,96      -58.515,20       32.294,50       90.809,69
//BB         BB Ações Setor Financ       5,89    1,98          977,26       26.473,50       25.496,24
//BB         BB Ações Siderurgia       -40,07  186,99      -55.120,57       14.547,87       69.668,44
//BB         BB Ações Vale              25,56   88,04      -41.408,09       83.229,55      124.637,64
//                                                        -192.434,15      684.587,48      877.021,63
//                                                         -87.898,35    1.459.548,47    1.547.446,82

// 05/12/2019
//Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024         35,30   15,48       49.659,86      339.015,00      289.355,14
//Agora      Tesouro IPCA+ 2035        109,58   79,06       15.794,78      108.470,40       92.675,61
//Agora      Tesouro Prefixado 2023     41,73   17,01        6.468,17       37.076,29       30.608,12
//Agora      Tesouro Prefixado 2025     39,47    7,21        1.530,67        6.618,14        5.087,47
//BB         Tesouro IPCA+ 2024        202,73  124,30       19.148,78       73.914,00       54.765,22
//BB         Tesouro IPCA+ 2035        106,25   51,82       35.982,74      136.368,29      100.385,55
//BB         Tesouro Prefixado 2025     19,04    4,68       15.795,08      130.946,22      115.151,14
//                                                         144.380,08      832.408,33      688.028,25
//BB         BB Acoes Aloc ETF FI       60,73   83,38      -18.067,94      128.196,85      146.264,79
//BB         BB Ações BB                24,75   23,88          651,41       92.633,46       91.982,05
//BB         BB Ações BDR Nivel I       31,53    5,46        4.561,60       23.018,58       18.456,99
//BB         BB Ações Const Civil       22,35  156,79      -12.375,86       11.264,30       23.640,17
//BB         BB Ações Consumo          132,74  111,53        2.014,80       22.110,64       20.095,84
//BB         BB Ações Dividendos        40,03   50,80       -5.148,23       66.941,50       72.089,73
//BB         BB Ações Energia           84,49   57,27       22.155,97      150.177,85      128.021,88
//BB         BB Ações Exportação        96,12  223,64      -15.286,14       23.509,83       38.795,96
//BB         BB Ações Infra             17,40    4,83        2.513,46       23.480,48       20.967,02
//BB         BB Ações Muilt Setor       18,66    4,83        2.766,64       23.733,66       20.967,02
//BB         BB Ações Petrobras         16,99  229,39      -60.094,76       33.099,28       93.194,04
//BB         BB Ações Setor Financ      13,01    4,66        2.089,00       28.254,68       26.165,68
//BB         BB Ações Siderurgia       -38,23  194,52      -56.504,25       14.993,46       71.497,70
//BB         BB Ações Vale              20,77   92,97      -47.857,28       80.052,89      127.910,17
//                                                        -178.581,58      721.467,45      900.049,03
//                                                         -34.201,50    1.553.875,78    1.588.077,28

// 27/12/2019
//Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024         35,86   15,78       50.289,97      340.415,33      290.125,36
//Agora      Tesouro IPCA+ 2035        108,72   79,53       15.105,60      108.027,88       92.922,28
//Agora      Tesouro Prefixado 2023     41,98   17,32        6.450,80       37.140,38       30.689,58
//Agora      Tesouro Prefixado 2025     39,27    7,49        1.508,02        6.609,03        5.101,01
//BB         Tesouro IPCA+ 2024        203,98  124,90       19.308,33       74.219,31       54.910,98
//BB         Tesouro IPCA+ 2035        105,40   52,23       35.159,23      135.811,96      100.652,73
//BB         Tesouro Prefixado 2025     18,88    4,96       15.308,34      130.765,97      115.457,63
//                                                         143.130,28      832.989,85      689.859,57
//BB         BB Acoes Aloc ETF FI       71,28   83,87      -10.042,67      136.611,42      146.654,09
//BB         BB Ações BB                35,99   24,21        8.747,54      100.974,42       92.226,87
//BB         BB Ações BDR Nivel I       32,80    5,74        4.734,30       23.240,41       18.506,11
//BB         BB Ações Const Civil       37,56  157,47      -11.039,30       12.663,79       23.703,08
//BB         BB Ações Consumo          151,57  112,09        3.750,74       23.900,07       20.149,33
//BB         BB Ações Dividendos        49,84   51,20         -649,19       71.632,42       72.281,61
//BB         BB Ações Energia          100,12   57,69       34.535,10      162.897,72      128.362,61
//BB         BB Ações Exportação       112,42  224,51      -13.435,93       25.463,29       38.899,22
//BB         BB Ações Infra             30,73    5,11        5.124,76       26.147,58       21.022,82
//BB         BB Ações Muilt Setor       27,91    5,11        4.561,14       25.583,96       21.022,82
//BB         BB Ações Petrobras         18,71  230,27      -59.855,06       33.587,02       93.442,08
//BB         BB Ações Setor Financ      15,98    4,94        2.761,83       28.997,15       26.235,32
//BB         BB Ações Siderurgia       -31,31  195,31      -55.014,84       16.673,15       71.687,99
//BB         BB Ações Vale              30,22   93,49      -41.931,28       86.319,33      128.250,61
//                                                        -127.752,85      774.691,71      902.444,56
//                                                          15.377,43    1.607.681,56    1.592.304,13

// 14/01/2020 
//Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024         36,81   16,00       52.139,48      342.810,57      290.671,09
//Agora      Tesouro IPCA+ 2035        111,00   79,87       16.109,70      109.206,80       93.097,10
//Agora      Tesouro Prefixado 2023     43,29   17,54        6.737,38       37.484,69       30.747,31
//Agora      Tesouro Prefixado 2025     40,77    7,70        1.569,48        6.680,09        5.110,61
//BB         Tesouro IPCA+ 2024        206,12  125,32       19.727,25       74.741,53       55.014,28
//BB         Tesouro IPCA+ 2035        107,65   52,51       36.451,99      137.294,09      100.842,10
//BB         Tesouro Prefixado 2025     20,16    5,16       16.497,06      132.171,92      115.674,85
//                                                         149.232,34      840.389,69      691.157,36
//BB         BB Acoes Aloc ETF FI       74,12   84,22       -8.055,75      138.874,25      146.930,00
//BB         BB Ações BB                28,72   24,44        3.181,12       95.581,51       92.400,38
//BB         BB Ações BDR Nivel I       35,05    5,94        5.093,35       23.634,28       18.540,94
//BB         BB Ações Const Civil       47,84  157,95      -10.137,48       13.610,19       23.747,68
//BB         BB Ações Consumo          163,52  112,49        4.847,61       25.034,85       20.187,23
//BB         BB Ações Dividendos        51,82   51,49          159,41       72.577,01       72.417,60
//BB         BB Ações Energia          106,92   57,99       39.831,38      168.435,49      128.604,11
//BB         BB Ações Exportação       126,79  225,12      -11.786,71       27.185,69       38.972,40
//BB         BB Ações Infra             36,03    5,31        6.145,17       27.207,53       21.062,36
//BB         BB Ações Muilt Setor       33,07    5,31        5.552,43       26.614,79       21.062,36
//BB         BB Ações Petrobras         17,16  230,89      -60.468,47       33.149,41       93.617,88
//BB         BB Ações Setor Financ      13,50    5,13        2.092,36       28.377,02       26.284,67
//BB         BB Ações Siderurgia       -27,12  195,86      -54.132,36       17.690,51       71.822,86
//BB         BB Ações Vale              34,93   93,85      -39.056,96       89.434,93      128.491,89
//                                                        -116.734,89      787.407,47      904.142,36
//                                                          32.497,45    1.627.797,16    1.595.299,71
// 06/02/2020
//Corretora  Título/Fundo             %Rentab %RSelic       Diferença     Valor Fundo  Valor Eq Selic
//Agora      Tesouro IPCA+ 2024         38,03   16,34       54.348,54      345.865,20      291.516,66
//Agora      Tesouro IPCA+ 2035        116,40   80,40       18.635,84      112.003,78       93.367,95
//Agora      Tesouro Prefixado 2023     44,31   17,88        6.913,13       37.749,89       30.836,76
//Agora      Tesouro Prefixado 2025     43,00    8,01        1.660,38        6.785,86        5.125,48
//BB         Tesouro IPCA+ 2024        208,84  125,97       20.233,19       75.407,52       55.174,33
//BB         Tesouro IPCA+ 2035        112,96   52,96       39.674,98      140.810,44      101.135,46
//BB         Tesouro Prefixado 2025     22,06    5,46       18.253,26      134.264,62      116.011,36
//                                                         159.719,32      852.887,31      693.167,99
//BB         BB Acoes Aloc ETF FI       70,29   84,75      -11.539,49      135.817,95      147.357,44
//BB         BB Ações BB                27,07   24,80        1.681,22       94.350,42       92.669,20
//BB         BB Ações BDR Nivel I       40,37    6,25        5.971,29       24.566,16       18.594,87
//BB         BB Ações Const Civil       43,18  158,70      -10.635,15       13.181,61       23.816,76
//BB         BB Ações Consumo          157,60  113,11        4.226,59       24.472,56       20.245,97
//BB         BB Ações Dividendos        50,15   51,93         -849,36       71.778,90       72.628,27
//BB         BB Ações Energia          108,46   58,44       40.715,75      169.693,99      128.978,24
//BB         BB Ações Exportação       116,91  226,06      -13.084,00       26.001,78       39.085,78
//BB         BB Ações Infra             34,51    5,61        5.778,85       26.902,49       21.123,64
//BB         BB Ações Muilt Setor       34,25    5,61        5.728,22       26.851,85       21.123,64
//BB         BB Ações Petrobras         14,73  231,85      -61.430,28       32.459,94       93.890,22
//BB         BB Ações Setor Financ      11,55    5,44        1.528,26       27.889,39       26.361,14
//BB         BB Ações Siderurgia       -28,17  196,72      -54.596,82       17.434,98       72.031,79
//BB         BB Ações Vale              29,13   94,41      -43.272,73       85.592,97      128.865,70
//                                                        -129.777,65      776.994,98      906.772,63
//                                                          29.941,67    1.629.882,29    1.599.940,62