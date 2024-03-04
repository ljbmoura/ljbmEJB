package br.com.ljbm.fp.servico;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

//import javax.inject.Inject;

//import org.apache.logging.log4j.Logger;

public class CotacaoTituloDAO {
//	@Inject	
//	private Logger log;

//	private FPDominioImpl servicoFPDominio;
	
	// TODO criar tabela bd para cotações com cache
	@SuppressWarnings("serial")
	static Map<LocalDate, Map<String, String>> cotacoes = 
			new HashMap<LocalDate, Map<String, String>>() 
	{
		{
//			put(LocalDate.of(2018, 6, 21), 
//					new HashMap<String, String>() 
//					{
//						{
//							put("Tesouro IPCA+ 2019", "3018.96");
//							put("Tesouro Prefixado 2019", "964.82");
//							put("Tesouro Prefixado 2023", "622.17");							
//							put("Tesouro Prefixado 2025", "483.39");
//							put("Tesouro IPCA+ 2024", "2214.30");
//							put("Tesouro IPCA+ 2035", "1173.29");
//						}
//					});
//			
//			put(LocalDate.of(2018, 05, 25), 
//					new HashMap<String, String>() 
//					{
//						{
//							put("Tesouro IPCA+ 2019", "2999.48");
//							put("Tesouro IPCA+ 2024", "2229.48");
//							put("Tesouro IPCA+ 2035", "1204.79");
//							put("Tesouro Prefixado 2019", "962.13");
//							put("Tesouro Prefixado 2023", "646.64");
//							put("Tesouro Prefixado 2025", "517.62"); 
//						}
//					});
//			
//			put(LocalDate.of(2018, 4, 6), 
//					new HashMap<String, String>() 
//					{
//						{
//							put("Tesouro IPCA+ 2019", "2995.76");
//							put("Tesouro Prefixado 2019", "956.43");
//							put("Tesouro Prefixado 2023", "661.08");							
//							put("Tesouro IPCA+ 2024", "2296.890000000");
//							put("Tesouro IPCA+ 2035", "1247.350000000");
//						}
//					});
//			
//			put(LocalDate.of(2018, 10, 16), 
//					new HashMap<String, String>() 
//					{
//						{
//							put("Tesouro IPCA+ 2024", "2380.49");
//							put("Tesouro IPCA+ 2035", "1339.35");
//							put("Tesouro Prefixado 2023", "680.27"); 
//							put("Tesouro Prefixado 2025", "548.42"); 
//
//						}
//					});
//			put(LocalDate.of(2019, 01, 04), 
//					new HashMap<String, String>() 
//					{
//						{
//							put("Tesouro IPCA+ 2024", "2475.89");
//							put("Tesouro IPCA+ 2035", "1413.94");
//							put("Tesouro Prefixado 2023", "723.00");
//							put("Tesouro Prefixado 2025", "596.51");
//							put("BB Ações Energia","11.656781000");
//							put("BB Ações Petrobras","7.499930000");
//							put("BB Ações Dividendos", "16.179030645");
//							put("BB Ações Exportação","7.908582269");
//							put("BB Ações Vale","15.985051758");
//							put("BB Ações PIBB", "4.971429687");
//							put("BB Ações Consumo", "2.256197807");
//							put("BB Ações Siderurgia", "0.823749004");
//							put("BB Ações BB", "2.849427795");
//							put("BB Ações Const Civil", "1.187927375");
//
//						}
//					});
//			
//			put(LocalDate.of(2019, 01, 9), 
//					new HashMap<String, String>() 
//					{
//						{
//							put("Tesouro IPCA+ 2024", "2483.22");
//							put("Tesouro IPCA+ 2035", "1459.84");
//							put("Tesouro Prefixado 2023", "723.15");
//							put("Tesouro Prefixado 2025", "597.98");
//
//							put("BB Ações Energia","11.693457000");												
//							put("BB Ações Petrobras","7.814492000");
//							put("BB Ações Dividendos", "16.225454745");
//							put("BB Ações Exportação","8.066576333");
//							put("BB Ações Vale","16.439668866");
//							put("BB Acoes Aloc ETF FI", "5.052408403");
//							put("BB Ações Consumo", "2.287631391");
//							put("BB Ações Siderurgia", "0.839018384");
//							put("BB Ações BB", "2.790912649");
//							put("BB Ações Const Civil", "1.197746596");
//							
//						}
//					});			
			put(LocalDate.of(2019, 01, 10), 
					new HashMap<String, String>() 
					{
						{
							put("Tesouro IPCA+ 2024", "2485,87");
							put("Tesouro IPCA+ 2035", "1459,84");
							put("Tesouro Prefixado 2023", "722,35");
							put("Tesouro Prefixado 2025", "597,00");

							put("BB Ações Energia",		"11,816338000");												
							put("BB Ações Petrobras",	"7,767873000");
							put("BB Ações Dividendos", 	"16,274040793");
							put("BB Ações Exportação",	"8,050878283");
							put("BB Ações Vale",		"16,258818616");
							put("BB Acoes Aloc ETF FI", "5,064097736");
							put("BB Ações Consumo", 	"2,314023258");
							put("BB Ações Siderurgia", 	"0,838710723");
							put("BB Ações BB", 			"2,831299483");
							put("BB Ações Const Civil", "1,196936788");
							put("BB Ações BDR Nivel I", "1,096759343");
							
//							put("BB Ações Muilt Setor",	"0,000000000");
//							put("BB Ações Setor Financ","0,000000000");
//							put("BB Ações Infra", 		"0,000000000");
						}
					});	
			
			
//			put(LocalDate.of(2019, 01, 14), 
//					new HashMap<String, String>() 
//			{
//				{
//					put("Tesouro IPCA+ 2024", "2486.96");
//					put("Tesouro IPCA+ 2035", "1474.99");
//					put("Tesouro Prefixado 2023", "723.88");
//					put("Tesouro Prefixado 2025", "598.06");
//					
//					put("BB Ações Energia",		"12.011539000");												
//					put("BB Ações Petrobras",	"7.677386000");
//					put("BB Ações Dividendos", 	"16.478736745");
//					put("BB Ações Exportação",	"8.045854577");
//					put("BB Ações Vale",		"16.104498855");
//					put("BB Acoes Aloc ETF FI", "5.111463956");
//					put("BB Ações Consumo", 	"2.346460816");
//					put("BB Ações Siderurgia", 	"0.817151996");
//					put("BB Ações BB", 			"2.908119414");
//					put("BB Ações Const Civil", "1.203882824");
//					put("BB Ações BDR Nivel I", "1.094493752");
//					
//				}
//			});	
//			put(LocalDate.of(2019, 01, 15), 
//					new HashMap<String, String>() 
//			{
//				{
//					put("Tesouro IPCA+ 2024", 		"2494,34");
//					put("Tesouro IPCA+ 2035", 		"1489,27");
//					put("Tesouro Prefixado 2023", 	"722,80");
//					put("Tesouro Prefixado 2025", 	"596,64");
//					
//					put("BB Ações Energia",		"11,922618000");												
//					put("BB Ações Dividendos", 	"16,392190777");
//					put("BB Ações Exportação",	"8,057518823");
//					put("BB Ações Vale",		"16,026916199");
//					put("BB Acoes Aloc ETF FI", "5,083552506");
//					put("BB Ações Consumo", 	"2,344786657");
//					put("BB Ações BB", 			"2,874356251");
//					put("BB Ações Const Civil", "1,202852182");
//					put("BB Ações BDR Nivel I", "1,104813675");
//
//					put("BB Ações Petrobras",	"7,657617000");
//					put("BB Ações Siderurgia", 	"0,804840884");
//					
//				}
//			});			
//			put(LocalDate.of(2019, 01, 17), 
//					new HashMap<String, String>() 
//			{
//				{
//					put("Tesouro IPCA+ 2024", 		"2493,17");
//					put("Tesouro IPCA+ 2035", 		"1487,88");
//					put("Tesouro Prefixado 2023", 	"721,17");
//					put("Tesouro Prefixado 2025", 	"595,10");
//					
//					put("BB Ações Energia",		"12,007915000");												
//					put("BB Ações Dividendos", 	"16,572053456");
//					put("BB Ações Exportação",	"8,180158828");
//					put("BB Ações Vale",		"16,597368459");
//					put("BB Acoes Aloc ETF FI", "5,151663844");
//					put("BB Ações Consumo", 	"2,390760513");
//					put("BB Ações BB", 			"2,857222066");
//					put("BB Ações Const Civil", "1,224191788");
//					put("BB Ações BDR Nivel I", "1,124166638");
//					
//					put("BB Ações Petrobras",	"7,775753000");
//					put("BB Ações Siderurgia", 	"0,818002434");
//					
//				}
//			});			
//			put(LocalDate.of(2019, 01, 18), 
//					new HashMap<String, String>() 
//			{
//				{
//					put("Tesouro IPCA+ 2024", 		"2492,64");
//					put("Tesouro IPCA+ 2035", 		"1489,20");
//					put("Tesouro Prefixado 2023", 	"722,43");
//					put("Tesouro Prefixado 2025", 	"596,81");
//					
//					put("BB Ações Energia",		"12,073362000");												
//					put("BB Ações Dividendos", 	"16,646429983");
//					put("BB Ações Exportação",	"8,232493050");
//					put("BB Ações Vale",		"16,757629688");
//					put("BB Acoes Aloc ETF FI", "5,193503962");
//					put("BB Ações Consumo", 	"2,402477112");
//					put("BB Ações BB", 			"2,833262284");
//					put("BB Ações Const Civil", "1,228028045");
//					put("BB Ações BDR Nivel I", "1,137357954");
//					
//					put("BB Ações Petrobras",	"7,866723000");
//					put("BB Ações Siderurgia", 	"0,825337475");
//					
//				}
//			});			
//			put(LocalDate.of(2019, 01, 24), 
//					new HashMap<String, String>() 
//			{
//				{
//					put("Tesouro IPCA+ 2024", 		"2501,46");
//					put("Tesouro IPCA+ 2035", 		"1505,33");
//					put("Tesouro Prefixado 2023", 	"728,62");
//					put("Tesouro Prefixado 2025", 	"602,85");
//					
//					put("BB Ações Energia",		"12,614899000");												
//					put("BB Ações Dividendos", 	"16,964148853");
//					put("BB Ações Exportação",	"8,426497062");
//					put("BB Ações Vale",		"17,176114027");
//					put("BB Acoes Aloc ETF FI", "5,286957661");
//					put("BB Ações Consumo", 	"2,421320412");
//					put("BB Ações BB", 			"2,855660214");
//					put("BB Ações Const Civil", "1,268277476");
//					put("BB Ações BDR Nivel I", "1,134536317");
//					
//					put("BB Ações Petrobras",	"7,936072000");
//					put("BB Ações Siderurgia", 	"0,840219881");
//				}
//			});			
//			put(LocalDate.of(2019, 01, 29), 
//					new HashMap<String, String>() 
//			{
//				{
//					put("Tesouro IPCA+ 2024", 		"2505,59");
//					put("Tesouro IPCA+ 2035", 		"1501,59");
//					put("Tesouro Prefixado 2023", 	"730,41");
//					put("Tesouro Prefixado 2025", 	"604,24");
//					
//					put("BB Ações Energia",		"12,779440000");												
//					put("BB Ações Dividendos", 	"16,762701287");
//					put("BB Ações Exportação",	"7,955774999");
//					put("BB Ações Vale",		"13,108843522");
//					put("BB Acoes Aloc ETF FI", "5,207768772");
//					put("BB Ações Consumo", 	"2,456263715");
//					put("BB Ações BB", 			"2,911921214");
//					put("BB Ações Const Civil", "1,276295512");
//					put("BB Ações BDR Nivel I", "1,120670279");
//					
//					put("BB Ações Petrobras",	"7,838432000");
//					put("BB Ações Siderurgia", 	"0,803736009");
//				}
//			});			
//	
//			put(LocalDate.of(2019, 02, 14),new HashMap<String, String>() {
//				{	put("Tesouro IPCA+ 2024", 		"2520,54");
//					put("Tesouro IPCA+ 2035", 		"1518,55");
//					put("Tesouro Prefixado 2023", 	"736,35");
//					put("Tesouro Prefixado 2025", 	"610,63");
//					
//					put("BB Ações Energia",		"12,825853000");												
//					put("BB Ações Dividendos", 	"17,074029313");
//					put("BB Ações Exportação",	"8,035628192");
//					put("BB Ações Muilt Setor",	"1,837466718");
//					put("BB Ações Vale",		"13,985741073");
//					put("BB Acoes Aloc ETF FI", "5,299305060");
//					put("BB Ações Consumo", 	"2,448487962");
//					put("BB Ações Setor Financ","3,396284247");
//					put("BB Ações BB", 			"3,189405108");
//					put("BB Ações Const Civil", "1,254566993");
//					put("BB Ações Infra", 		"0,855591644");
//					put("BB Ações BDR Nivel I", "1,151647800");
//					put("BB Ações Petrobras",	"8,397181000");
//					put("BB Ações Siderurgia", 	"0,808592157");
//				}
//			});			
//			put(LocalDate.of(2019, 02, 21),new HashMap<String, String>() {
//				{	put("Tesouro IPCA+ 2024", 		"2522,14");
//					put("Tesouro IPCA+ 2035", 		"1528,27");
//					put("Tesouro Prefixado 2023", 	"735,68");
//					put("Tesouro Prefixado 2025", 	"609,68");
//					
//					put("BB Ações Energia",		"12,679823000");												
//					put("BB Ações Dividendos", 	"16,780854882");
//					put("BB Ações Exportação",	"8,073768127");
//					put("BB Ações Muilt Setor",	"1,814989534");
//					put("BB Ações Vale",		"14,017596556");
//					put("BB Acoes Aloc ETF FI", "5,250734768");
//					put("BB Ações Consumo", 	"2,391496384");
//					put("BB Ações Setor Financ","3,327257210");
//					put("BB Ações BB", 			"3,085513700");
//					put("BB Ações Const Civil", "1,224895095");
//					put("BB Ações Infra", 		"0,849951682");
//					put("BB Ações BDR Nivel I", "1,174400271");
//					put("BB Ações Petrobras",	"8,395884000");
//					put("BB Ações Siderurgia", 	"0,802411725");
//				}
//			});			
//			put(LocalDate.of(2019, 02, 25),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 	"2.529,48");
//					put("Tesouro IPCA+ 2035", 		"1.534,29");
//					put("Tesouro Prefixado 2023", 	"737,71");
//					put("Tesouro Prefixado 2025", 	"611,73");
//					
//					put("BB Ações Energia",		"12,771293000");												
//					put("BB Ações Dividendos", 	"16,920796188");
//					put("BB Ações Exportação",	"8,315794710");
//					put("BB Ações Muilt Setor",	"1,814890193");
//					put("BB Ações Vale",		"14,409411384");
//					put("BB Acoes Aloc ETF FI", "5,283496935");
//					put("BB Ações Consumo", 	"2,413063577");
//					put("BB Ações Setor Financ","3,356040248");
//					put("BB Ações BB", 			"3,076366570");
//					put("BB Ações Const Civil", "1,235118513");
//					put("BB Ações Infra", 		"0,859305997");
//					put("BB Ações BDR Nivel I", "1,184672342");
//					put("BB Ações Petrobras",	"8,221303000");
//					put("BB Ações Siderurgia", 	"0,829068752");
//				}
//			});			
//			
//			put(LocalDate.of(2019, 03, 22),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.558,50");
//					put("Tesouro IPCA+ 2035", 		"1.567,41");
//					put("Tesouro Prefixado 2023", 	"739,09");
//					put("Tesouro Prefixado 2025", 	"611,00");
//					
//					put("BB Ações Energia",		"12,552139000");												
//					put("BB Ações Dividendos", 	"16,169489728");
//					put("BB Ações Exportação",	"8,328428229");
//					put("BB Ações Muilt Setor",	"1,787077813");
//					put("BB Ações Vale",		"15,257756054");
//					put("BB Acoes Aloc ETF FI", "5,091457837");
//					put("BB Ações Consumo", 	"2,297350400");
//					put("BB Ações Setor Financ","3,143015615");
//					put("BB Ações BB", 			"2,816051109");
//					put("BB Ações Const Civil", "1,174285847");
//					put("BB Ações Infra", 		"0,831584805");
//					put("BB Ações BDR Nivel I", "1,240512934");
//					put("BB Ações Petrobras",	"8,285750000");
//					put("BB Ações Siderurgia", 	"0,825521310");
//				}
//			});			
//			put(LocalDate.of(2019, 03, 26),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.552,20"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.558,81");
//					put("Tesouro Prefixado 2023", 	"740,59");
//					put("Tesouro Prefixado 2025", 	"612,71");
//					
//					put("BB Ações Energia",		"12,842902000");												
//					put("BB Ações Dividendos", 	"16,426372685");
//					put("BB Ações Exportação",	"8,462818288");
//					put("BB Ações Muilt Setor",	"1,787077813");
//					put("BB Ações Vale",		"15,257756054");
//					put("BB Acoes Aloc ETF FI", "5,091457837");
//					put("BB Ações Consumo", 	"2,297350400");
//					put("BB Ações Setor Financ","3,143015615");
//					put("BB Ações BB", 			"2,816051109");
//					put("BB Ações Const Civil", "1,174285847");
//					put("BB Ações Infra", 		"0,831584805");
//					put("BB Ações BDR Nivel I", "1,240512934");
//					put("BB Ações Petrobras",	"8,285750000");
//					put("BB Ações Siderurgia", 	"0,825521310");
//				}
//			});			
//			put(LocalDate.of(2019, 03, 29),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.559,55"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.552,18");
//					put("Tesouro Prefixado 2023", 	"742,32");
//					put("Tesouro Prefixado 2025", 	"616,25");
//					put("BB Ações Energia",			"12,712726000");												
//					put("BB Ações Dividendos", 		"16,400805629");
//					put("BB Ações Exportação",		"8,489144198");
//					put("BB Ações Muilt Setor",		"1,814416386");
//					put("BB Ações Vale",			"15,518495354");
//					put("BB Acoes Aloc ETF FI", 	"5,176375529");
//					put("BB Ações Consumo", 		"2,339975619");
//					put("BB Ações Setor Financ",	"3,199114712");
//					put("BB Ações BB", 				"2,877408207");
//					put("BB Ações Const Civil", 	"1,181286357");
//					put("BB Ações Infra", 			"0,837066981");
//					put("BB Ações BDR Nivel I", 	"1,254257975");
//					put("BB Ações Petrobras",		"8,449819000");
//					put("BB Ações Siderurgia", 		"0,840722645");
//				}
//			});			
//			put(LocalDate.of(2019, 04, 05),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.565,90"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.556,17");
//					put("Tesouro Prefixado 2023", 	"743,47");
//					put("Tesouro Prefixado 2025", 	"615,86");
//					put("BB Ações Energia",			"12,977094000");												
//					put("BB Ações Dividendos", 		"16,782672751");
//					put("BB Ações Exportação",		"8,665234698");
//					put("BB Ações Muilt Setor",		"1,868433172");
//					put("BB Ações Vale",			"15,831002757");
//					put("BB Acoes Aloc ETF FI", 	"5,269703554");
//					put("BB Ações Consumo", 		"2,388622569");
//					put("BB Ações Setor Financ",	"3,253090439");
//					put("BB Ações BB", 				"2,899966867");
//					put("BB Ações Const Civil", 	"1,192671326");
//					put("BB Ações Infra", 			"0,844827435");
//					put("BB Ações BDR Nivel I", 	"1,277010187");
//					put("BB Ações Petrobras",		"8,680904000");
//					put("BB Ações Siderurgia", 		"0,852723845");
//				}
//			});			
//			put(LocalDate.of(2019, 06, 11),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.705,98"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.729,78");
//					put("Tesouro Prefixado 2023", 	"783,01");
//					put("Tesouro Prefixado 2025", 	"663,32");
//					put("BB Ações Energia",			"14,004583000");												
//					put("BB Ações Dividendos", 		"17,480123239");
//					put("BB Ações Exportação",		"8,422964567");
//					put("BB Ações Muilt Setor",		"1,943057559");
//					put("BB Ações Vale",			"15,603341543");
//					put("BB Acoes Aloc ETF FI", 	"5,341464147");
//					put("BB Ações Consumo", 		"2,587332563");
//					put("BB Ações Setor Financ",	"3,393157841");
//					put("BB Ações BB", 				"3,135341617");
//					put("BB Ações Const Civil", 	"1,340972773");
//					put("BB Ações Infra", 			"0,847645342");
//					put("BB Ações BDR Nivel I", 	"1,248083602");
//					put("BB Ações Petrobras",		"8,141933000");
//					put("BB Ações Siderurgia", 		"0,821894921");
//				}
//			});			
//			put(LocalDate.of(2019, 06, 21),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.740,11"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.755,86");
//					put("Tesouro Prefixado 2023", 	"793,08");
//					put("Tesouro Prefixado 2025", 	"675,70");
//					put("BB Ações Energia",			"14,012690000");												
//					put("BB Ações Dividendos", 		"17,797312645");
//					put("BB Ações Exportação",		"8,742464273");
//					put("BB Ações Muilt Setor",		"1,982262462");
//					put("BB Ações Vale",			"15,900165827");
//					put("BB Acoes Aloc ETF FI", 	"5,491243024");
//					put("BB Ações Consumo", 		"2,669192626");
//					put("BB Ações Setor Financ",	"3,488899453");
//					put("BB Ações BB", 				"3,145308951");
//					put("BB Ações Const Civil", 	"1,441218213");
//					put("BB Ações Infra", 			"0,881174690");
//					put("BB Ações BDR Nivel I", 	"1,259639677");
//					put("BB Ações Petrobras",		"8,586210000");
//					put("BB Ações Siderurgia", 		"0,825052592");
//				}
//			});			
//			put(LocalDate.of(2019, 07, 06),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.760,28"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.814,99");
//					put("Tesouro Prefixado 2023", 	"800,69");
//					put("Tesouro Prefixado 2025", 	"686,83");
//					put("BB Ações Energia",			"14,378986000");												
//					put("BB Ações Dividendos", 		"18,086986272");
//					put("BB Ações Exportação",		"8,940447462");
//					put("BB Ações Muilt Setor",		"2,023653185");
//					put("BB Ações Vale",			"15,655421578");
//					put("BB Acoes Aloc ETF FI", 	"5,594807676");
//					put("BB Ações Consumo", 		"2,768045625");
//					put("BB Ações Setor Financ",	"3,595813557");
//					put("BB Ações BB", 				"3,277920427");
//					put("BB Ações Const Civil", 	"1,495952860");
//					put("BB Ações Infra", 			"0,908681925");
//					put("BB Ações BDR Nivel I", 	"1,275202307");
//					put("BB Ações Petrobras",		"8,192987000");
//					put("BB Ações Siderurgia", 		"0,847105789");
//				}
//			});			
//			put(LocalDate.of(2019, 12, 05),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.910,00"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.926,65");
//					put("Tesouro Prefixado 2023", 	"838,83");
//					put("Tesouro Prefixado 2025", 	"726,47");
//					put("BB Ações Energia",			"15,270550000");												
//					put("BB Ações Dividendos", 		"18,890292533");
//					put("BB Ações Exportação",		"9,836521969");
//					put("BB Ações Muilt Setor",		"2,185631283"); // Ações Set Quantitat
//					put("BB Ações Vale",			"15,293296232");
//					put("BB Acoes Aloc ETF FI", 	"6,020839156");
//					put("BB Ações Consumo", 		"3,150696570");
//					put("BB Ações Setor Financ",	"3,723638642");
//					put("BB Ações BB", 				"2,938693490");
//					put("BB Ações Const Civil", 	"1,766345838");
//					put("BB Ações Infra", 			"1,001586196");
//					put("BB Ações BDR Nivel I", 	"1,442619668");
//					put("BB Ações Petrobras",		"8,800179000");
//					put("BB Ações Siderurgia", 		"0,850323077");
//				}
//			});			
//			put(LocalDate.of(2019, 12, 27),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.922,02"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.918,79");
//					put("Tesouro Prefixado 2023", 	"840,28");
//					put("Tesouro Prefixado 2025", 	"725,47");
//					put("BB Ações Energia",			"16,563946000");												
//					put("BB Ações Dividendos", 		"20,214026566");
//					put("BB Ações Exportação",		"10,653850155");
//					put("BB Ações Muilt Setor",		"2,356025974"); // Ações Set Quantitat
//					put("BB Ações Vale",			"16,490436564");
//					put("BB Acoes Aloc ETF FI", 	"6,416034355");
//					put("BB Ações Consumo", 		"3,405683857");
//					put("BB Ações Setor Financ",	"3,821487806");
//					put("BB Ações Siderurgia", 		"0,945583603");
//					put("BB Ações BB", 				"3,203301080");
//					put("BB Ações Const Civil", 	"1,985797796");
//					put("BB Ações Infra", 			"1,115354214");
//					put("BB Ações BDR Nivel I", 	"1,456522283");
//					put("BB Ações Petrobras",		"8,929855000");
//				}
//			});			
//			put(LocalDate.of(2020, 01, 14),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.942,58"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.939,73");
//					put("Tesouro Prefixado 2023", 	"848,07");
//					put("Tesouro Prefixado 2025", 	"733,27");
//					put("BB Ações Energia",			"17,127044000");												
//					put("BB Ações Dividendos", 		"20,480582627");
//					put("BB Ações Exportação",		"11,374506313");
//					put("BB Ações Muilt Setor",		"2,450954863"); // Ações Set Quantitat
//					put("BB Ações Vale",			"17,085641381");
//					put("BB Acoes Aloc ETF FI", 	"6,522309522");
//					put("BB Ações Consumo", 		"3,567386845");
//					put("BB Ações Setor Financ",	"3,739762017");
//					put("BB Ações Siderurgia", 		"1,003280704");
//					put("BB Ações BB", 				"3,032217016");
//					put("BB Ações Const Civil", 	"2,134202737");
//					put("BB Ações Infra", 			"1,160567735");
//					put("BB Ações BDR Nivel I", 	"1,481206585");
//					put("BB Ações Petrobras",		"8,813508000");
//				}
//			});			
//			put(LocalDate.of(2020, 02, 06),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"2.968,80"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.989,41");
//					put("Tesouro Prefixado 2023", 	"854,07");
//					put("Tesouro Prefixado 2025", 	"744,88");
//					put("BB Ações Energia",			"17,255012000");												
//					put("BB Ações Dividendos", 		"20,255363604");
//					put("BB Ações Exportação",		"10,879155066");
//					put("BB Ações Muilt Setor",		"2,472786088"); // Ações Set Quantitat
//					put("BB Ações Vale",			"16,351673777");
//					put("BB Acoes Aloc ETF FI", 	"6,378768252");
//					put("BB Ações Consumo", 		"3,487261967");
//					put("BB Ações Setor Financ",	"3,675497948");
//					put("BB Ações Siderurgia", 		"0,988788858");
//					put("BB Ações BB", 				"2,993162048");
//					put("BB Ações Const Civil", 	"2,066997743");
//					put("BB Ações Infra", 			"1,147555750");
//					put("BB Ações BDR Nivel I", 	"1,539609206");
//					put("BB Ações Petrobras",		"8,630197000");
//				}
//			});			
//			put(LocalDate.of(2020, 07, 03),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"3.084,56"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.828,15");
//					put("Tesouro Prefixado 2023", 	"904,42");
//					put("Tesouro Prefixado 2025", 	"778,25");
//					put("BB Ações Energia",			"14,906965000");												
//					put("BB Ações Dividendos", 		"15,966907815");
//					put("BB Ações Exportação",		"8,845640614");
//					put("BB Ações Muilt Setor",		"2,077625765"); // Ações Set Quantitat
//					put("BB Ações Vale",			"16,551994973");
//					put("BB Acoes Aloc ETF FI", 	"5,301610736");
//					put("BB Ações Consumo", 		"3,074272041");
//					put("BB Ações Setor Financ",	"2,926188054");
//					put("BB Ações Siderurgia", 		"0,751329176");
//					put("BB Ações BB", 				"2,048462137");
//					put("BB Ações Const Civil", 	"1,484961987");
//					put("BB Ações Infra", 			"0,941265427");
//					put("BB Ações BDR Nivel I", 	"1,858503820");
//					put("BB Ações Petrobras",		"6,252215000");
//				}
//			});	
//			put(LocalDate.of(2020, 11, 13),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"3.107,93"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.851,61");
//					put("Tesouro Prefixado 2023", 	"897,24");
//					put("Tesouro Prefixado 2025", 	"754,44");
//					put("BB Ações Energia",			"15,521065000");												
//					put("BB Ações Dividendos", 		"16,767737272");
//					put("BB Ações Exportação",		"10,376166599");
//					put("BB Ações Muilt Setor",		"2,300465615"); // Ações Set Quantitat
//					put("BB Ações Vale",			"19,521112590");
//					put("BB Acoes Aloc ETF FI", 	"5,743493222");
//					put("BB Ações Consumo", 		"3,299497418");
//					put("BB Ações Setor Financ",	"3,043131530");
//					put("BB Ações Siderurgia", 		"1,030595703");
//					put("BB Ações BB", 				"2,041111012");
//					put("BB Ações Const Civil", 	"1,410793371");
//					put("BB Ações Infra", 			"1,009053556");
//					put("BB Ações BDR Nivel I", 	"2,124636416"); // Ações ESG Globais
//					put("BB Ações Petrobras",		"7,958232000");
//				}
//			});		
//			put(LocalDate.of(2021, 1, 8),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"3.208,72"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"2.051,32");
//					put("Tesouro Prefixado 2023", 	"910,48");
//					put("Tesouro Prefixado 2025", 	"777,13");
//					put("Tesouro Prefixado 2026", 	"714,99");
//					put("BB Ações Energia",			"16,822844000");												
//					put("BB Ações Dividendos", 		"19,031595286");
//					put("BB Ações Exportação",		"12,889535686");
//					put("BB Ações Muilt Setor",		"2,812541261"); // Ações Set Quantitat
//					put("BB Ações Vale",			"31,273219962");
//					put("BB Acoes Aloc ETF FI", 	"6,757077151");
//					put("BB Ações Consumo", 		"3,433138468");
//					put("BB Ações Setor Financ",	"3,566890078");
//					put("BB Ações Siderurgia", 		"1,552963685");
//					put("BB Ações BB", 				"2,469958382");
//					put("BB Ações Const Civil", 	"1,432438903");
//					put("BB Ações Infra", 			"1,247921328");
//					put("BB Ações BDR Nivel I", 	"2,248837191"); // Ações ESG Globais
//					put("BB Ações Petrobras",		"8,754711000");
//				}
//			});					
//			put(LocalDate.of(2021, 6, 4),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"3.240,47"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"2.014,13");
//					put("Tesouro Prefixado 2023", 	"899,95");
//					put("Tesouro Prefixado 2025", 	"758,17");
//					put("Tesouro Prefixado 2026", 	"693,44");
//					put("BB Ações Energia",			"17,088200000");												
//					put("BB Ações Dividendos", 		"20,052767431");
//					put("BB Ações Exportação",		"14,588292057");
//					put("BB Ações Muilt Setor",		"2,928089215"); // Ações Set Quantitat
//					put("BB Ações Vale",			"35,882262277");
//					put("BB Acoes Aloc ETF FI", 	"7,009386021");
//					put("BB Ações Consumo", 		"3,587134241");
//					put("BB Ações Setor Financ",	"3,594243463");
//					put("BB Ações Siderurgia", 		"1,715404442");
//					put("BB Ações BB", 				"2,277657899");
//					put("BB Ações Const Civil", 	"1,512421974");
//					put("BB Ações Infra", 			"1,372892587");
//					put("BB Ações BDR Nivel I", 	"2,270083929"); // Ações ESG Globais
//					put("BB Ações Petrobras",		"8,224178000");
//				}
//			});					
//			put(LocalDate.of(2022, 3, 4),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"3.337,07"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.805,60");
////					put("Tesouro Prefixado 2023", 	"899,95");
//					put("Tesouro Prefixado 2025", 	"723,59");
//					put("Tesouro Prefixado 2026", 	"649,99");
//					put("BB Ações Energia",			"15,261185000");												
//					put("BB Ações Dividendos", 		"16,837092500");
//					put("BB Ações Exportação",		"13,262722207");
//					put("BB Ações Muilt Setor",		"2,713905524"); // Ações Set Quantitat
//					put("BB Ações Vale",			"35,993035036");
//					put("BB Acoes Aloc ETF FI", 	"5,806845709");
//					put("BB Ações Consumo", 		"2,402478559");
//					put("BB Ações Setor Financ",	"2,889487245");
//					put("BB Ações Siderurgia", 		"1,526817281");
//					put("BB Ações BB", 				"2,276003851");
//					put("BB Ações Const Civil", 	"0,998531826");
//					put("BB Ações Infra", 			"1,203137705");
//					put("BB Ações BDR Nivel I", 	"2,255126854"); // Ações ESG Globais
//					put("BB Ações Petrobras",		"11,918106000");
//				}
//			});					
//			put(LocalDate.of(2022, 3, 27),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"3.440,53"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.917,69");
////					put("Tesouro Prefixado 2023", 	"899,95");
//					put("Tesouro Prefixado 2025", 	"737,59");
//					put("Tesouro Prefixado 2026", 	"662,12");
//					put("Tesouro Prefixado 2029", 	"471,69");
//					put("BB Ações Energia",			"16,655670000");												
//					put("BB Ações Dividendos", 		"18,154791878");
//					put("BB Ações Exportação",		"13,189951300");
//					put("BB Ações Muilt Setor",		"2,769178112"); // Ações Set Quantitat
//					put("BB Ações Vale",			"34,821634911");
//					put("BB Acoes Aloc ETF FI", 	"6,174988861");
//					put("BB Ações Consumo", 		"2,563093667");
//					put("BB Ações Setor Financ",	"3,160724322");
//					put("BB Ações Siderurgia", 		"1,489801806");
//					put("BB Ações BB", 				"2,382359657");
//					put("BB Ações Const Civil", 	"1,115672350");
//					put("BB Ações Infra", 			"1,259819737");
//					put("BB Ações BDR Nivel I", 	"2,240565690"); // Ações ESG Globais
//					put("BB Ações Petrobras",		"11,260842000");
//				}
//			});					
//			put(LocalDate.of(2022, 10, 14),new HashMap<String, String>() {
//				{	
//					put("Tesouro IPCA+ 2024", 		"3.524,01"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.917,13");
////					put("Tesouro Prefixado 2023", 	"899,95");
//					put("Tesouro Prefixado 2025", 	"780,30");
//					put("Tesouro Prefixado 2026", 	"697,81");
//					put("Tesouro Prefixado 2029", 	"495,76");
//					
//					put("BB Ações Energia",			"15,321843000");												
//					put("BB Ações Dividendos", 		"17,161754348");
//					put("BB Ações Exportação",		"11,162758726");
//					put("BB Ações Muilt Setor",		"2,586482061"); // Ações Set Quantitat
//					put("BB Ações Vale",			"26,684963488");
//					put("BB Acoes Aloc ETF FI", 	"5,726949230");
//					put("BB Ações Consumo", 		"2,100700325");
//					put("BB Ações Setor Financ",	"3,096828503");
//					put("BB Ações Siderurgia", 		"1,130250434");
//					put("BB Ações BB", 				"2,756956632");
//					put("BB Ações Const Civil", 	"1,114190961");
//					put("BB Ações Infra", 			"1,182568994");
//					put("BB Ações BDR Nivel I", 	"1,951415263"); // Ações ESG Globais
//					put("BB Ações Petrobras",		"16,947589000");
//				}
//			});					
//			put(LocalDate.of(2022, 10, 18),new HashMap<String, String>() {
//				{	
//					put("Tesouro Prefixado 2025", 	"782,57");
//					put("Tesouro Prefixado 2026", 	"700,34");
//					put("Tesouro Prefixado 2029", 	"498,90");
//					put("Tesouro IPCA+ 2024", 		"3.536,16"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.935,00");
////					put("Tesouro Prefixado 2023", 	"899,95");
//					
//					put("BB Ações Energia",			"15,758886000");												
//					put("BB Ações Dividendos", 		"17,686727644");
//					put("BB Ações Exportação",		"11,572369554");
//					put("BB Ações Muilt Setor",		"2,668999124"); // Ações Set Quantitat
//					put("BB Acoes Aloc ETF FI", 	"5,899739728"); // Ações Alocação ETF
//					put("BB Ações Consumo", 		"2,159680879");
//					put("BB Ações Setor Financ",	"3,185763604");
//					put("BB Ações Siderurgia", 		"1,164682442");
//					put("BB Ações BB", 				"2,921164314");
//					put("BB Ações Const Civil", 	"1,120268664");
//					put("BB Ações Infra", 			"1,220327644");
//					put("BB Ações BDR Nivel I", 	"2,000421097"); // Ações ESG Globais
//
//					put("BB Ações Vale",			"27,495005991");
//					put("BB Ações Petrobras",		"17,251796000");
//				}
//			});					
//			put(LocalDate.of(2022, 10, 21),new HashMap<String, String>() {
//				{	
//					put("Tesouro Prefixado 2025", 	"782,35");
//					put("Tesouro Prefixado 2026", 	"699,96");
//					put("Tesouro Prefixado 2029", 	"497,97");
//					put("Tesouro IPCA+ 2024", 		"3.542,97"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.932,13");
////					put("Tesouro Prefixado 2023", 	"899,95");
//					
//					put("BB Ações Energia",			"15,955672000");												
//					put("BB Ações Dividendos", 		"18,192988644");
//					put("BB Ações Exportação",		"11,898103427");
//					put("BB Ações Muilt Setor",		"2,772032891"); // Ações Set Quantitat
//					put("BB Acoes Aloc ETF FI", 	"6,123014445"); // Ações Alocação ETF
//					put("BB Ações Consumo", 		"2,165851544");
//					put("BB Ações Setor Financ",	"3,328790408");
//					put("BB Ações Siderurgia", 		"1,201731827");
//					put("BB Ações BB", 				"3,141031284");
//					put("BB Ações Const Civil", 	"1,114670536");
//					put("BB Ações Infra", 			"1,259278365");
//					put("BB Ações BDR Nivel I", 	"1,979899392"); // Ações ESG Globais
//					
//					put("BB Ações Vale",			"28,316409132");
//					put("BB Ações Petrobras",		"18,992487000");
//				}
//			});					
//			put(LocalDate.of(2023, 1, 26),new HashMap<String, String>() {
//				{	
//					put("Tesouro Prefixado 2025", 	"791,96");
//					put("Tesouro Prefixado 2026", 	"700,34");
//					put("Tesouro Prefixado 2029", 	"478,61");
//					put("Tesouro IPCA+ 2024", 		"3.649,23"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"1.864,08");
//					put("Tesouro IPCA+ 2029", 		"2.746,31");
//					put("BB Ações Energia",			"15,383052000");												
//					put("BB Ações Dividendos", 		"17,516512960");
//					put("BB Ações Exportação",		"12,368460417");
//					put("BB Ações Muilt Setor",		"2,617994973"); // Ações Seleção Fator
//					put("BB Acoes Aloc ETF FI", 	"5,696306378"); // Ações Alocação ETF
//					put("BB Ações Consumo", 		"1,848870191");
//					put("BB Ações Setor Financ",	"2,873950776");
//					put("BB Ações Siderurgia", 		"1,516416540");
//					put("BB Ações BB", 				"2,944536639");
//					put("BB Ações Const Civil", 	"0,969261705");
//					put("BB Ações Infra", 			"1,271502031");
//					put("BB Ações BDR Nivel I", 	"2,102768148"); // Ações ESG Globais
//					
//					put("BB Ações Vale",			"37,333710792");
//					put("BB Ações Petrobras",		"15,035879000");
//				}
//			});	
//			put(LocalDate.of(2023, 6, 2),new HashMap<String, String>() {
//				{	
//					put("Tesouro Prefixado 2025", 	"840,97");
//					put("Tesouro Prefixado 2026", 	"763,68");
//					put("Tesouro Prefixado 2029", 	"550,35");
//					put("Tesouro IPCA+ 2024", 		"3.778,57"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"2.167,96");
//					put("Tesouro IPCA+ 2029", 		"3.017,17");
//					put("BB Ações Energia",			"15,806258000");												
//					put("BB Ações Dividendos", 		"17,133702659");
//					put("BB Ações Exportação",		"11,484361998");
//					put("BB Ações Muilt Setor",		"2,492350543"); // Ações Seleção Fator
//					put("BB Acoes Aloc ETF FI", 	"5,659879941"); // Ações Alocação ETF
//					put("BB Ações Consumo", 		"1,835849149");
//					put("BB Ações Setor Financ",	"3,146542907");
//					put("BB Ações Siderurgia", 		"1,279114641");
//					put("BB Ações BB", 				"3,389866149");
//					put("BB Ações Const Civil", 	"1,168929163");
//					put("BB Ações Infra", 			"1,237815122");
//					put("BB Ações BDR Nivel I", 	"2,187665224"); // Ações ESG Globais
//					
//					put("BB Ações Vale",			"26,357371398");
//					put("BB Ações Petrobras",		"16,963653000");
//				}
//			});
//			put(LocalDate.of(2023, 6, 23),new HashMap<String, String>() {
//				{	
//					put("Tesouro Prefixado 2025", 	"851,92");
//					put("Tesouro Prefixado 2026", 	"776,23");
//					put("Tesouro Prefixado 2029", 	"566,40");
//					put("Tesouro IPCA+ 2024", 		"3.803,98"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"2.211,30");
//					put("Tesouro IPCA+ 2029", 		"3.044,45");
//					put("BB Ações Energia",			"17,074638000");												
//					put("BB Ações Dividendos", 		"18,026018363");
//					put("BB Ações Exportação",		"11,937839264");
//					put("BB Ações Muilt Setor",		"2,639409704"); // Ações Seleção Fator
//					put("BB Acoes Aloc ETF FI", 	"5,973474088"); // Ações Alocação ETF
//					put("BB Ações Consumo", 		"1,964668624");
//					put("BB Ações Setor Financ",	"3,323401937");
//					put("BB Ações Siderurgia", 		"1,277411673");
//					put("BB Ações BB", 				"3,863849757");
//					put("BB Ações Const Civil", 	"1,250016591");
//					put("BB Ações Infra", 			"1,286024266");
//					put("BB Ações BDR Nivel I", 	"2,168992121"); // Ações ESG Globais
//					
//					put("BB Ações Vale",			"25,539411676");
//					put("BB Ações Petrobras",		"19,806794000");
//				}
//			});	
//			put(LocalDate.of(2023, 7, 31),new HashMap<String, String>() {
//				{	
//					put("Tesouro Prefixado 2025", 	"865,86");
//					put("Tesouro Prefixado 2026", 	"789,65");
//					put("Tesouro Prefixado 2029", 	"575,83");
//					put("Tesouro IPCA+ 2024", 		"3849,17"); // valores para resgate
//					put("Tesouro IPCA+ 2035", 		"2239,79");
//					put("Tesouro IPCA+ 2029", 		"3088,09");
//					
//					put("Tesouro RENDA+ AE 2035", 		"1368,97");
//					put("Tesouro RENDA+ AE 2040", 		"1036,65");
//					
//					put("BB Ações Energia",			"16,991547000");												
//					put("BB Ações Dividendos", 		"18,428509923");
//					put("BB Ações Exportação",		"12,466741208");
//					put("BB Ações Muilt Setor",		"2,701357251"); // Ações Seleção Fator
//					put("BB Acoes Aloc ETF FI", 	"6,115234241"); // Ações Alocação ETF
//					put("BB Ações Consumo", 		"1,995074169");
//					put("BB Ações Setor Financ",	"3,368331164");
//					put("BB Ações Siderurgia", 		"1,374989736");
//					put("BB Ações BB", 				"3,649832608");
//					put("BB Ações Const Civil", 	"1,317988302");
//					put("BB Ações Infra", 			"1,340670317");
//					put("BB Ações BDR Nivel I", 	"2,267450400"); // Ações ESG Globais
//					
//					put("BB Ações Vale",			"26,740011477");
//					put("BB Ações Petrobras",		"20,282291000");
//				}
//			});
			put(LocalDate.of(2023, 11, 24),new HashMap<String, String>() {
				{	
					put("Tesouro Prefixado 2025", 	"895,79");
					put("Tesouro Prefixado 2026", 	"812,43");
					put("Tesouro Prefixado 2029", 	"588,85");
					put("Tesouro IPCA+ 2024", 		"3977,09"); // valores para resgate
					put("Tesouro IPCA+ 2029", 		"3092,47");
					put("Tesouro IPCA+ 2035", 		"2195,99");
					
					put("Tesouro RENDA+ AE 2035", 	"1316,43");
					put("Tesouro RENDA+ AE 2040", 	"980,27");
					
					put("BB Ações Energia",			"16,559529000");												
					put("BB Ações Dividendos", 		"18,801069960");
					put("BB Ações Exportação",		"12,804243930");
					put("BB Ações Muilt Setor",		"2,768675357"); // Ações Seleção Fator
					put("BB Acoes Aloc ETF FI", 	"6,134348102"); // Ações Alocação ETF
					put("BB Ações Consumo", 		"1,777008057");
					put("BB Ações Setor Financ",	"3,372387560");
					put("BB Ações Siderurgia", 		"1,340290976");
					put("BB Ações BB", 				"3,994764611");
					put("BB Ações Const Civil", 	"1,224304301");
					put("BB Ações Infra", 			"1,409828164");
					put("BB Ações BDR Nivel I", 	"2,344483581"); // Ações Globais BDR I
					
					put("BB Ações Vale",			"30,066356888");
					put("BB Ações Petrobras",		"23,530703000");
				}
			});
			
			put(LocalDate.of(2023, 12, 1),new HashMap<String, String>() {
				{	
					put("Tesouro Prefixado 2025", 	"899,15");
					put("Tesouro Prefixado 2026", 	"818,82");
					put("Tesouro Prefixado 2029", 	"599,30");
					put("Tesouro IPCA+ 2024", 		"3.981,46"); // valores para resgate
					put("Tesouro IPCA+ 2029", 		"3.107,68");
					put("Tesouro IPCA+ 2035", 		"2.211,96");
					
					put("Tesouro RENDA+ AE 2035", 	"1.335,72");
					put("Tesouro RENDA+ AE 2040", 	"1.000,19");
					
					put("BB Ações Energia",			"16,826323000");												
					put("BB Ações Dividendos", 		"19,183122991");
					put("BB Ações Exportação",		"12,980963962");
					put("BB Ações Muilt Setor",		"2,826308110"); // Ações Seleção Fator
					put("BB Acoes Aloc ETF FI", 	"6,289114292"); // Ações Alocação ETF
					put("BB Ações Consumo", 		"1,845609420");
					put("BB Ações Setor Financ",	"3,483999438");
					put("BB Ações Siderurgia", 		"1,379898983");
					put("BB Ações BB", 				"4,250909565");
					put("BB Ações Const Civil", 	"1,280504183");
					put("BB Ações Infra", 			"1,439331592");
					put("BB Ações BDR Nivel I", 	"2,331427165"); // Ações Globais BDR I
					
					put("BB Ações Vale",			"30,634425999");
					put("BB Ações Petrobras",		"23,380824000");
				}
			});
			
			put(LocalDate.of(2023, 12, 15),new HashMap<String, String>() {
				{	
					put("Tesouro Prefixado 2025", 	"904,45");
					put("Tesouro Prefixado 2026", 	"825,46");
					put("Tesouro Prefixado 2029", 	"609,42");
					put("Tesouro IPCA+ 2024", 		"3.994,91"); // valores para resgate
					put("Tesouro IPCA+ 2029", 		"3.150,31");
					put("Tesouro IPCA+ 2035", 		"2.258,20");
					put("Tesouro RENDA+ AE 2035", 	"1.382,35");
					put("Tesouro RENDA+ AE 2040", 	"1.043,42");
					put("BB Ações Energia",			"17,058480000");												
					put("BB Ações Dividendos", 		"19,651752522");
					put("BB Ações Exportação",		"13,062568719");
					put("BB Ações Muilt Setor",		"2,855542147"); // Ações Seleção Fator
					put("BB Acoes Aloc ETF FI", 	"6,409445355"); // Ações Alocação ETF
					put("BB Ações Consumo", 		"1,884752517");
					put("BB Ações Setor Financ",	"3,650234246");
					put("BB Ações Siderurgia", 		"1,429596016");
					put("BB Ações BB", 				"4,294939959");
					put("BB Ações Const Civil", 	"1,347928239");
					put("BB Ações Infra", 			"1,453402447");
					put("BB Ações BDR Nivel I", 	"2,427186287"); // Ações Globais BDR I
					
					put("BB Ações Vale",			"30,083281309");
					put("BB Ações Petrobras",		"23,242927000");
				}
			});
			
			put(LocalDate.of(2023, 12, 19),new HashMap<String, String>() {
				{	
					put("Tesouro Prefixado 2025", 	"905,23");
					put("Tesouro Prefixado 2026", 	"826,23");
					put("Tesouro Prefixado 2029", 	"609,07");
					put("Tesouro IPCA+ 2024", 		"3.998,48"); // valores para resgate
					put("Tesouro IPCA+ 2029", 		"3.157,22");
					put("Tesouro IPCA+ 2035", 		"2.274,37");
					put("Tesouro RENDA+ AE 2035", 	"1.390,85");
					put("Tesouro RENDA+ AE 2040", 	"1.051,32");
					
					put("BB Ações Energia",			"17,376419000");												
					put("BB Ações Dividendos", 		"19,906714007");
					put("BB Ações Exportação",		"13,262722080");
					put("BB Ações Muilt Setor",		"2,890524834"); // Ações Seleção Fator
					put("BB Acoes Aloc ETF FI", 	"6,491594345"); // Ações Alocação ETF
					put("BB Ações Consumo", 		"1,912366547");
					put("BB Ações Setor Financ",	"3,676275025");
					put("BB Ações Siderurgia", 		"1,443764888");
					put("BB Ações BB", 				"4,313008547");
					put("BB Ações Const Civil", 	"1,352833158");
					put("BB Ações Infra", 			"1,470063342");
					put("BB Ações BDR Nivel I", 	"2,428501512"); // Ações Globais BDR I
					
					put("BB Ações Vale",			"30,431757686");
					put("BB Ações Petrobras",		"23,808839000");
				}
			});
			
			put(LocalDate.of(2023, 12, 27),new HashMap<String, String>() {
				{	
					put("Tesouro Prefixado 2025", 	"908,05");
					put("Tesouro Prefixado 2026", 	"830,20");
					put("Tesouro Prefixado 2029", 	"617,05");
					put("Tesouro IPCA+ 2024", 		"4.005,51"); // valores para resgate
					put("Tesouro IPCA+ 2029", 		"3.172,61");
					put("Tesouro IPCA+ 2035", 		"2.295,16");
					put("Tesouro RENDA+ AE 2035", 	"1.408,07");
					put("Tesouro RENDA+ AE 2040", 	"1.064,75");
					
					put("BB Ações Energia",			"17,606787000");												
					put("BB Ações Dividendos", 		"20,215466655");
					put("BB Ações Exportação",		"13,568840870");
					put("BB Ações Muilt Setor",		"2,955122753"); // Ações Seleção Fator
					put("BB Acoes Aloc ETF FI", 	"6,593123938"); // Ações Alocação ETF
					put("BB Ações Consumo", 		"1,924720682");
					put("BB Ações Setor Financ",	"3,718398975");
					put("BB Ações Siderurgia", 		"1,482681484");
					put("BB Ações BB", 				"4,313018027");
					put("BB Ações Const Civil", 	"1,363679023");
					put("BB Ações Infra", 			"1,499314552");
					put("BB Ações BDR Nivel I", 	"2,410179545"); // Ações Globais BDR I
					
					put("BB Ações Vale",			"31,504797435");
					put("BB Ações Petrobras",		"24,484968000");
				}
			});
			
			put(LocalDate.of(2023, 12, 28),new HashMap<String, String>() {
				{	
					put("Tesouro Prefixado 2025", 	"908,05");
					put("Tesouro Prefixado 2026", 	"830,20");
					put("Tesouro Prefixado 2029", 	"617,05");
					put("Tesouro IPCA+ 2024", 		"4.005,51"); // valores para resgate
					put("Tesouro IPCA+ 2029", 		"3.172,61");
					put("Tesouro IPCA+ 2035", 		"2.295,16");
					put("Tesouro RENDA+ AE 2035", 	"1.408,07");
					put("Tesouro RENDA+ AE 2040", 	"1.064,75");
					
					put("BB Ações Energia",			"17,719312000");												
					put("BB Ações Dividendos", 		"20,290941697");
					put("BB Ações Exportação",		"13,564736532");
					put("BB Ações Muilt Setor",		"2,958248273"); // Ações Seleção Fator
					put("BB Acoes Aloc ETF FI", 	"6,581573574"); // Ações Alocação ETF
					put("BB Ações Consumo", 		"1,921779429");
					put("BB Ações Setor Financ",	"3,727347508");
					put("BB Ações Siderurgia", 		"1,490043247");
					put("BB Ações BB", 				"4,353839581");
					put("BB Ações Const Civil", 	"1,372786600");
					put("BB Ações Infra", 			"1,499219176");
					put("BB Ações BDR Nivel I", 	"2,419506881"); // Ações Globais BDR I
					
					put("BB Ações Vale",			"31,421164338");
					put("BB Ações Petrobras",		"24,395563000");
				}
			});
			
			put(LocalDate.of(2024, 2, 5),new HashMap<String, String>() {
				{	
					put("Tesouro Prefixado 2025", 	"917,87");
					put("Tesouro Prefixado 2026", 	"837,04");
					put("Tesouro Prefixado 2029", 	"616,34");
					put("Tesouro IPCA+ 2024", 		"4.065,94"); // valores para resgate
					put("Tesouro IPCA+ 2029", 		"3.165,18");
					put("Tesouro IPCA+ 2035", 		"2.266,04");
					put("Tesouro RENDA+ AE 2035", 	"1.380,89");
					put("Tesouro RENDA+ AE 2040", 	"1.040,17");
					
					put("BB Ações Energia",			"16,798465000");												
					put("BB Ações Dividendos", 		"19,468146369");
					put("BB Ações Exportação",		"12,707916996");
					put("BB Ações Muilt Setor",		"2,745426937"); // Ações Seleção Fator
					put("BB Acoes Aloc ETF FI", 	"6,174183359"); // Ações Alocação ETF
					put("BB Ações Consumo", 		"1,738958557");
					put("BB Ações Setor Financ",	"3,599815322");
					put("BB Ações Siderurgia", 		"1,335112831");
					put("BB Ações BB", 				"4,575848993");
					put("BB Ações Const Civil", 	"1,225911474");
					put("BB Ações Infra", 			"1,429992821");
					put("BB Ações BDR Nivel I", 	"2,605431935"); // Ações Globais BDR I
					
					put("BB Ações Vale",			"26,622579917");
					put("BB Ações Petrobras",		"26,500672000");
				}
			});
		}
	};

//	public CotacaoTituloDAO(Logger log, FPDominioImpl servicoFPDominio) {
//		this.log = log;
//		this.servicoFPDominio = servicoFPDominio;
//	}
	
	public BigDecimal paraTituloEm(String titulo, LocalDate dataCotacao) {
		return BigDecimal.ZERO;
//		return this.servicoFPDominio.getCotacaoPorTituloData(titulo, dataCotacao);
//		Map<String, String> x = cotacoes.get(dataCotacao);
//		if (x != null  && x.get(titulo) != null) {
//			String t = x.get(titulo).replaceAll("\\.", "");
//			t = t.replaceFirst(",", ".");
//			BigDecimal y = new BigDecimal(t);
//		if (y != null) {
//			return y.getValorCota();
//		}
//		}
//		log.warn(String.format("cotação não encontrada para o titulo '%s' em '%s'", titulo, dataCotacao.toString()));
//		return BigDecimal.ZERO;
	}
	
}