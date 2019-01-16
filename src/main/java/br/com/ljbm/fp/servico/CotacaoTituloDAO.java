﻿package br.com.ljbm.fp.servico;

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
	static Map<LocalDate, Map<String, String>> cotacoes = 
			new HashMap<LocalDate, Map<String, String>>() 
	{
		{
			put(LocalDate.of(2018, 6, 21), 
					new HashMap<String, String>() 
					{
						{
							put("Tesouro IPCA+ 2019", "3018.96");
							put("Tesouro Prefixado 2019", "964.82");
							put("Tesouro Prefixado 2023", "622.17");							
							put("Tesouro Prefixado 2025", "483.39");
							put("Tesouro IPCA+ 2024", "2214.30");
							put("Tesouro IPCA+ 2035", "1173.29");
						}
					});
			
			put(LocalDate.of(2018, 05, 25), 
					new HashMap<String, String>() 
					{
						{
							put("Tesouro IPCA+ 2019", "2999.48");
							put("Tesouro IPCA+ 2024", "2229.48");
							put("Tesouro IPCA+ 2035", "1204.79");
							put("Tesouro Prefixado 2019", "962.13");
							put("Tesouro Prefixado 2023", "646.64");
							put("Tesouro Prefixado 2025", "517.62"); 
						}
					});
			
			put(LocalDate.of(2018, 4, 6), 
					new HashMap<String, String>() 
					{
						{
							put("Tesouro IPCA+ 2019", "2995.76");
							put("Tesouro Prefixado 2019", "956.43");
							put("Tesouro Prefixado 2023", "661.08");							
							put("Tesouro IPCA+ 2024", "2296.890000000");
							put("Tesouro IPCA+ 2035", "1247.350000000");
						}
					});
			
			put(LocalDate.of(2018, 10, 16), 
					new HashMap<String, String>() 
					{
						{
							put("Tesouro IPCA+ 2024", "2380.49");
							put("Tesouro IPCA+ 2035", "1339.35");
							put("Tesouro Prefixado 2023", "680.27"); 
							put("Tesouro Prefixado 2025", "548.42"); 

						}
					});
			put(LocalDate.of(2019, 01, 04), 
					new HashMap<String, String>() 
					{
						{
							put("Tesouro IPCA+ 2024", "2475.89");
							put("Tesouro IPCA+ 2035", "1413.94");
							put("Tesouro Prefixado 2023", "723.00");
							put("Tesouro Prefixado 2025", "596.51");
							put("BB Ações Energia","11.656781000");
							put("BB Ações Petrobras","7.499930000");
							put("BB Ações Dividendos", "16.179030645");
							put("BB Ações Exportação","7.908582269");
							put("BB Ações Vale","15.985051758");
							put("BB Ações PIBB", "4.971429687");
							put("BB Ações Consumo", "2.256197807");
							put("BB Ações Siderurgia", "0.823749004");
							put("BB Ações BB", "2.849427795");
							put("BB Ações Const Civil", "1.187927375");

						}
					});
			
			put(LocalDate.of(2019, 01, 9), 
					new HashMap<String, String>() 
					{
						{
							put("Tesouro IPCA+ 2024", "2483.22");
							put("Tesouro IPCA+ 2035", "1459.84");
							put("Tesouro Prefixado 2023", "723.15");
							put("Tesouro Prefixado 2025", "597.98");

							put("BB Ações Energia","11.693457000");												
							put("BB Ações Petrobras","7.814492000");
							put("BB Ações Dividendos", "16.225454745");
							put("BB Ações Exportação","8.066576333");
							put("BB Ações Vale","16.439668866");
							put("BB Acoes Aloc ETF FI", "5.052408403");
							put("BB Ações Consumo", "2.287631391");
							put("BB Ações Siderurgia", "0.839018384");
							put("BB Ações BB", "2.790912649");
							put("BB Ações Const Civil", "1.197746596");
							
						}
					});			
			put(LocalDate.of(2019, 01, 10), 
					new HashMap<String, String>() 
					{
						{
							put("Tesouro IPCA+ 2024", "2485.87");
							put("Tesouro IPCA+ 2035", "1459.84");
							put("Tesouro Prefixado 2023", "722.35");
							put("Tesouro Prefixado 2025", "597.00");

							put("BB Ações Energia",		"11.816338000");												
							put("BB Ações Petrobras",	"7.767873000");
							put("BB Ações Dividendos", 	"16.274040793");
							put("BB Ações Exportação",	"8.050878283");
							put("BB Ações Vale",		"16.258818616");
							put("BB Acoes Aloc ETF FI", "5.064097736");
							put("BB Ações Consumo", 	"2.314023258");
							put("BB Ações Siderurgia", 	"0.838710723");
							put("BB Ações BB", 			"2.831299483");
							put("BB Ações Const Civil", "1.196936788");
							put("BB Ações BDR Nivel I", "1.096759343");
							
						}
					});	
			put(LocalDate.of(2019, 01, 14), 
					new HashMap<String, String>() 
			{
				{
					put("Tesouro IPCA+ 2024", "2486.96");
					put("Tesouro IPCA+ 2035", "1474.99");
					put("Tesouro Prefixado 2023", "723.88");
					put("Tesouro Prefixado 2025", "598.06");
					
					put("BB Ações Energia",		"12.011539000");												
					put("BB Ações Petrobras",	"7.677386000");
					put("BB Ações Dividendos", 	"16.478736745");
					put("BB Ações Exportação",	"8.045854577");
					put("BB Ações Vale",		"16.104498855");
					put("BB Acoes Aloc ETF FI", "5.111463956");
					put("BB Ações Consumo", 	"2.346460816");
					put("BB Ações Siderurgia", 	"0.817151996");
					put("BB Ações BB", 			"2.908119414");
					put("BB Ações Const Civil", "1.203882824");
					put("BB Ações BDR Nivel I", "1.094493752");
					
				}
			});	
			put(LocalDate.of(2019, 01, 15), 
					new HashMap<String, String>() 
			{
				{
					put("Tesouro IPCA+ 2024", 		"2494,34");
					put("Tesouro IPCA+ 2035", 		"1489,27");
					put("Tesouro Prefixado 2023", 	"722,80");
					put("Tesouro Prefixado 2025", 	"596,64");
					
					put("BB Ações Energia",		"11,922618000");												
					put("BB Ações Dividendos", 	"16,392190777");
					put("BB Ações Exportação",	"8,057518823");
					put("BB Ações Vale",		"16,026916199");
					put("BB Acoes Aloc ETF FI", "5,083552506");
					put("BB Ações Consumo", 	"2,344786657");
					put("BB Ações BB", 			"2,874356251");
					put("BB Ações Const Civil", "1,202852182");
					put("BB Ações BDR Nivel I", "1,104813675");

					put("BB Ações Petrobras",	"7,657617000");
					put("BB Ações Siderurgia", 	"0,804840884");
					
				}
			});			
		}
	};

	public CotacaoTituloDAO(Logger log) {
		this.log = log;
	}

	public BigDecimal paraTituloEm(String titulo, LocalDate em) {
		Map<String, String> x = cotacoes.get(em);
		if (x != null) {
			BigDecimal y = new BigDecimal(x.get(titulo).replaceFirst(",", "."));
			if (y != null) {
				return y;
			}
		}
		log.warn(String.format("cotação não encontrada para o titulo '%s' em '%s'", titulo, em.toString()));
		return BigDecimal.ZERO;
	}
	
}
