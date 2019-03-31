package br.com.ljbm.fp.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.Logger;



public class CotacaoTituloDAO {
	@Inject	
	private Logger log;
	
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
			put(LocalDate.of(2019, 01, 17), 
					new HashMap<String, String>() 
			{
				{
					put("Tesouro IPCA+ 2024", 		"2493,17");
					put("Tesouro IPCA+ 2035", 		"1487,88");
					put("Tesouro Prefixado 2023", 	"721,17");
					put("Tesouro Prefixado 2025", 	"595,10");
					
					put("BB Ações Energia",		"12,007915000");												
					put("BB Ações Dividendos", 	"16,572053456");
					put("BB Ações Exportação",	"8,180158828");
					put("BB Ações Vale",		"16,597368459");
					put("BB Acoes Aloc ETF FI", "5,151663844");
					put("BB Ações Consumo", 	"2,390760513");
					put("BB Ações BB", 			"2,857222066");
					put("BB Ações Const Civil", "1,224191788");
					put("BB Ações BDR Nivel I", "1,124166638");
					
					put("BB Ações Petrobras",	"7,775753000");
					put("BB Ações Siderurgia", 	"0,818002434");
					
				}
			});			
			put(LocalDate.of(2019, 01, 18), 
					new HashMap<String, String>() 
			{
				{
					put("Tesouro IPCA+ 2024", 		"2492,64");
					put("Tesouro IPCA+ 2035", 		"1489,20");
					put("Tesouro Prefixado 2023", 	"722,43");
					put("Tesouro Prefixado 2025", 	"596,81");
					
					put("BB Ações Energia",		"12,073362000");												
					put("BB Ações Dividendos", 	"16,646429983");
					put("BB Ações Exportação",	"8,232493050");
					put("BB Ações Vale",		"16,757629688");
					put("BB Acoes Aloc ETF FI", "5,193503962");
					put("BB Ações Consumo", 	"2,402477112");
					put("BB Ações BB", 			"2,833262284");
					put("BB Ações Const Civil", "1,228028045");
					put("BB Ações BDR Nivel I", "1,137357954");
					
					put("BB Ações Petrobras",	"7,866723000");
					put("BB Ações Siderurgia", 	"0,825337475");
					
				}
			});			
			put(LocalDate.of(2019, 01, 24), 
					new HashMap<String, String>() 
			{
				{
					put("Tesouro IPCA+ 2024", 		"2501,46");
					put("Tesouro IPCA+ 2035", 		"1505,33");
					put("Tesouro Prefixado 2023", 	"728,62");
					put("Tesouro Prefixado 2025", 	"602,85");
					
					put("BB Ações Energia",		"12,614899000");												
					put("BB Ações Dividendos", 	"16,964148853");
					put("BB Ações Exportação",	"8,426497062");
					put("BB Ações Vale",		"17,176114027");
					put("BB Acoes Aloc ETF FI", "5,286957661");
					put("BB Ações Consumo", 	"2,421320412");
					put("BB Ações BB", 			"2,855660214");
					put("BB Ações Const Civil", "1,268277476");
					put("BB Ações BDR Nivel I", "1,134536317");
					
					put("BB Ações Petrobras",	"7,936072000");
					put("BB Ações Siderurgia", 	"0,840219881");
				}
			});			
			put(LocalDate.of(2019, 01, 29), 
					new HashMap<String, String>() 
			{
				{
					put("Tesouro IPCA+ 2024", 		"2505,59");
					put("Tesouro IPCA+ 2035", 		"1501,59");
					put("Tesouro Prefixado 2023", 	"730,41");
					put("Tesouro Prefixado 2025", 	"604,24");
					
					put("BB Ações Energia",		"12,779440000");												
					put("BB Ações Dividendos", 	"16,762701287");
					put("BB Ações Exportação",	"7,955774999");
					put("BB Ações Vale",		"13,108843522");
					put("BB Acoes Aloc ETF FI", "5,207768772");
					put("BB Ações Consumo", 	"2,456263715");
					put("BB Ações BB", 			"2,911921214");
					put("BB Ações Const Civil", "1,276295512");
					put("BB Ações BDR Nivel I", "1,120670279");
					
					put("BB Ações Petrobras",	"7,838432000");
					put("BB Ações Siderurgia", 	"0,803736009");
				}
			});			
	
			put(LocalDate.of(2019, 02, 14),new HashMap<String, String>() {
				{	put("Tesouro IPCA+ 2024", 		"2520,54");
					put("Tesouro IPCA+ 2035", 		"1518,55");
					put("Tesouro Prefixado 2023", 	"736,35");
					put("Tesouro Prefixado 2025", 	"610,63");
					
					put("BB Ações Energia",		"12,825853000");												
					put("BB Ações Dividendos", 	"17,074029313");
					put("BB Ações Exportação",	"8,035628192");
					put("BB Ações Muilt Setor",	"1,837466718");
					put("BB Ações Vale",		"13,985741073");
					put("BB Acoes Aloc ETF FI", "5,299305060");
					put("BB Ações Consumo", 	"2,448487962");
					put("BB Ações Setor Financ","3,396284247");
					put("BB Ações BB", 			"3,189405108");
					put("BB Ações Const Civil", "1,254566993");
					put("BB Ações Infra", 		"0,855591644");
					put("BB Ações BDR Nivel I", "1,151647800");
					put("BB Ações Petrobras",	"8,397181000");
					put("BB Ações Siderurgia", 	"0,808592157");
				}
			});			
			put(LocalDate.of(2019, 02, 21),new HashMap<String, String>() {
				{	put("Tesouro IPCA+ 2024", 		"2522,14");
					put("Tesouro IPCA+ 2035", 		"1528,27");
					put("Tesouro Prefixado 2023", 	"735,68");
					put("Tesouro Prefixado 2025", 	"609,68");
					
					put("BB Ações Energia",		"12,679823000");												
					put("BB Ações Dividendos", 	"16,780854882");
					put("BB Ações Exportação",	"8,073768127");
					put("BB Ações Muilt Setor",	"1,814989534");
					put("BB Ações Vale",		"14,017596556");
					put("BB Acoes Aloc ETF FI", "5,250734768");
					put("BB Ações Consumo", 	"2,391496384");
					put("BB Ações Setor Financ","3,327257210");
					put("BB Ações BB", 			"3,085513700");
					put("BB Ações Const Civil", "1,224895095");
					put("BB Ações Infra", 		"0,849951682");
					put("BB Ações BDR Nivel I", "1,174400271");
					put("BB Ações Petrobras",	"8,395884000");
					put("BB Ações Siderurgia", 	"0,802411725");
				}
			});			
			put(LocalDate.of(2019, 02, 25),new HashMap<String, String>() {
				{	
					put("Tesouro IPCA+ 2024", 	"2.529,48");
					put("Tesouro IPCA+ 2035", 		"1.534,29");
					put("Tesouro Prefixado 2023", 	"737,71");
					put("Tesouro Prefixado 2025", 	"611,73");
					
					put("BB Ações Energia",		"12,771293000");												
					put("BB Ações Dividendos", 	"16,920796188");
					put("BB Ações Exportação",	"8,315794710");
					put("BB Ações Muilt Setor",	"1,814890193");
					put("BB Ações Vale",		"14,409411384");
					put("BB Acoes Aloc ETF FI", "5,283496935");
					put("BB Ações Consumo", 	"2,413063577");
					put("BB Ações Setor Financ","3,356040248");
					put("BB Ações BB", 			"3,076366570");
					put("BB Ações Const Civil", "1,235118513");
					put("BB Ações Infra", 		"0,859305997");
					put("BB Ações BDR Nivel I", "1,184672342");
					put("BB Ações Petrobras",	"8,221303000");
					put("BB Ações Siderurgia", 	"0,829068752");
				}
			});			
			
			put(LocalDate.of(2019, 03, 22),new HashMap<String, String>() {
				{	
					put("Tesouro IPCA+ 2024", 		"2.558,50");
					put("Tesouro IPCA+ 2035", 		"1.567,41");
					put("Tesouro Prefixado 2023", 	"739,09");
					put("Tesouro Prefixado 2025", 	"611,00");
					
					put("BB Ações Energia",		"12,552139000");												
					put("BB Ações Dividendos", 	"16,169489728");
					put("BB Ações Exportação",	"8,328428229");
					put("BB Ações Muilt Setor",	"1,787077813");
					put("BB Ações Vale",		"15,257756054");
					put("BB Acoes Aloc ETF FI", "5,091457837");
					put("BB Ações Consumo", 	"2,297350400");
					put("BB Ações Setor Financ","3,143015615");
					put("BB Ações BB", 			"2,816051109");
					put("BB Ações Const Civil", "1,174285847");
					put("BB Ações Infra", 		"0,831584805");
					put("BB Ações BDR Nivel I", "1,240512934");
					put("BB Ações Petrobras",	"8,285750000");
					put("BB Ações Siderurgia", 	"0,825521310");
				}
			});			
			put(LocalDate.of(2019, 03, 26),new HashMap<String, String>() {
				{	
					put("Tesouro IPCA+ 2024", 		"2.552,20"); // valores para resgate
					put("Tesouro IPCA+ 2035", 		"1.558,81");
					put("Tesouro Prefixado 2023", 	"740,59");
					put("Tesouro Prefixado 2025", 	"612,71");
					
					put("BB Ações Energia",		"12,842902000");												
					put("BB Ações Dividendos", 	"16,426372685");
					put("BB Ações Exportação",	"8,462818288");
					put("BB Ações Muilt Setor",	"1,787077813");
					put("BB Ações Vale",		"15,257756054");
					put("BB Acoes Aloc ETF FI", "5,091457837");
					put("BB Ações Consumo", 	"2,297350400");
					put("BB Ações Setor Financ","3,143015615");
					put("BB Ações BB", 			"2,816051109");
					put("BB Ações Const Civil", "1,174285847");
					put("BB Ações Infra", 		"0,831584805");
					put("BB Ações BDR Nivel I", "1,240512934");
					put("BB Ações Petrobras",	"8,285750000");
					put("BB Ações Siderurgia", 	"0,825521310");
				}
			});			
			put(LocalDate.of(2019, 03, 29),new HashMap<String, String>() {
				{	
					put("Tesouro IPCA+ 2024", 		"2.559,55"); // valores para resgate
					put("Tesouro IPCA+ 2035", 		"1.552,18");
					put("Tesouro Prefixado 2023", 	"742,32");
					put("Tesouro Prefixado 2025", 	"616,25");
					put("BB Ações Energia",			"12,712726000");												
					put("BB Ações Dividendos", 		"16,400805629");
					put("BB Ações Exportação",		"8,489144198");
					put("BB Ações Muilt Setor",		"1,814416386");
					put("BB Ações Vale",			"15,518495354");
					put("BB Acoes Aloc ETF FI", 	"5,176375529");
					put("BB Ações Consumo", 		"2,339975619");
					put("BB Ações Setor Financ",	"3,199114712");
					put("BB Ações BB", 				"2,877408207");
					put("BB Ações Const Civil", 	"1,181286357");
					put("BB Ações Infra", 			"0,837066981");
					put("BB Ações BDR Nivel I", 	"1,254257975");
					put("BB Ações Petrobras",		"8,449819000");
					put("BB Ações Siderurgia", 		"0,840722645");
				}
			});			
		}
	};

	public CotacaoTituloDAO(Logger log) {
		this.log = log;
	}

	public BigDecimal paraTituloEm(String titulo, LocalDate em) {
		Map<String, String> x = cotacoes.get(em);
		if (x != null  && x.get(titulo) != null) {
			String t = x.get(titulo).replaceAll("\\.", "");
			t = t.replaceFirst(",", ".");
			BigDecimal y = new BigDecimal(t);
			if (y != null) {
				return y;
			}
		}
		log.warn(String.format("cotação não encontrada para o titulo '%s' em '%s'", titulo, em.toString()));
		return BigDecimal.ZERO;
	}
	
}
