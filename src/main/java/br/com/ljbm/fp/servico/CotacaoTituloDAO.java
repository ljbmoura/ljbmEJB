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
	static Map<LocalDate, Map<String, BigDecimal>> cotacoes = 
			new HashMap<LocalDate, Map<String, BigDecimal>>() 
	{
		{
			put(LocalDate.of(2018, 6, 21), 
					new HashMap<String, BigDecimal>() 
					{
						{
							put("Tesouro IPCA+ 2019", new BigDecimal("3018.96"));
							put("Tesouro Prefixado 2019", new BigDecimal("964.82"));
							put("Tesouro Prefixado 2023", new BigDecimal("622.17"));							
							put("Tesouro Prefixado 2025", new BigDecimal("483.39"));
							put("Tesouro IPCA+ 2024", new BigDecimal("2214.30"));
							put("Tesouro IPCA+ 2035", new BigDecimal("1173.29"));
						}
					});
			
			put(LocalDate.of(2018, 05, 25), 
					new HashMap<String, BigDecimal>() 
					{
						{
							put("Tesouro IPCA+ 2019", new BigDecimal("2999.48"));
							put("Tesouro IPCA+ 2024", new BigDecimal("2229.48"));
							put("Tesouro IPCA+ 2035", new BigDecimal("1204.79"));
							put("Tesouro Prefixado 2019", new BigDecimal("962.13"));
							put("Tesouro Prefixado 2023", new BigDecimal("646.64"));
							put("Tesouro Prefixado 2025", new BigDecimal("517.62")); 
						}
					});
			
			put(LocalDate.of(2018, 4, 6), 
					new HashMap<String, BigDecimal>() 
					{
						{
							put("Tesouro IPCA+ 2019", new BigDecimal("2995.76"));
							put("Tesouro Prefixado 2019", new BigDecimal("956.43"));
							put("Tesouro Prefixado 2023", new BigDecimal("661.08"));							
							put("Tesouro IPCA+ 2024", new BigDecimal("2296.890000000"));
							put("Tesouro IPCA+ 2035", new BigDecimal("1247.350000000"));
						}
					});
			
			put(LocalDate.of(2018, 10, 16), 
					new HashMap<String, BigDecimal>() 
					{
						{
							put("Tesouro IPCA+ 2024", new BigDecimal("2380.49"));
							put("Tesouro IPCA+ 2035", new BigDecimal("1339.35"));
							put("Tesouro Prefixado 2023", new BigDecimal("680.27")); 
							put("Tesouro Prefixado 2025", new BigDecimal("548.42")); 

						}
					});
			put(LocalDate.of(2019, 01, 04), 
					new HashMap<String, BigDecimal>() 
					{
						{
							put("Tesouro IPCA+ 2024", new BigDecimal("2475.89"));
							put("Tesouro IPCA+ 2035", new BigDecimal("1413.94"));
							put("Tesouro Prefixado 2023", new BigDecimal("723.00"));
							put("Tesouro Prefixado 2025", new BigDecimal("596.51"));
							put("BB Ações Energia",new BigDecimal("11.656781000"));
							put("BB Ações Petrobras",new BigDecimal("7.499930000"));
							put("BB Ações Dividendos", new BigDecimal("16.179030645"));
							put("BB Ações Exportação",new BigDecimal("7.908582269"));
							put("BB Ações Vale",new BigDecimal("15.985051758"));
							put("BB Ações PIBB", new BigDecimal("4.971429687"));
							put("BB Ações Consumo", new BigDecimal("2.256197807"));
							put("BB Ações Siderurgia", new BigDecimal("0.823749004"));
							put("BB Ações BB", new BigDecimal("2.849427795"));
							put("BB Ações Const Civil", new BigDecimal("1.187927375"));

						}
					});
			
			put(LocalDate.of(2019, 01, 9), 
					new HashMap<String, BigDecimal>() 
					{
						{
							put("Tesouro IPCA+ 2024", new BigDecimal("2483.22"));
							put("Tesouro IPCA+ 2035", new BigDecimal("1459.84"));
							put("Tesouro Prefixado 2023", new BigDecimal("723.15"));
							put("Tesouro Prefixado 2025", new BigDecimal("597.98"));

							put("BB Ações Energia",new BigDecimal("11.693457000"));												
							put("BB Ações Petrobras",new BigDecimal("7.814492000"));
							put("BB Ações Dividendos", new BigDecimal("16.225454745"));
							put("BB Ações Exportação",new BigDecimal("8.066576333"));
							put("BB Ações Vale",new BigDecimal("16.439668866"));
							put("BB Acoes Aloc ETF FI", new BigDecimal("5.052408403"));
							put("BB Ações Consumo", new BigDecimal("2.287631391"));
							put("BB Ações Siderurgia", new BigDecimal("0.839018384"));
							put("BB Ações BB", new BigDecimal("2.790912649"));
							put("BB Ações Const Civil", new BigDecimal("1.197746596"));
							
						}
					});			
			put(LocalDate.of(2019, 01, 10), 
					new HashMap<String, BigDecimal>() 
					{
						{
							put("Tesouro IPCA+ 2024", new BigDecimal("2485.87"));
							put("Tesouro IPCA+ 2035", new BigDecimal("1459.84"));
							put("Tesouro Prefixado 2023", new BigDecimal("722.35"));
							put("Tesouro Prefixado 2025", new BigDecimal("597.00"));

							put("BB Ações Energia",		new BigDecimal("11.816338000"));												
							put("BB Ações Petrobras",	new BigDecimal("7.767873000"));
							put("BB Ações Dividendos", 	new BigDecimal("16.274040793"));
							put("BB Ações Exportação",	new BigDecimal("8.050878283"));
							put("BB Ações Vale",		new BigDecimal("16.258818616"));
							put("BB Acoes Aloc ETF FI", new BigDecimal("5.064097736"));
							put("BB Ações Consumo", 	new BigDecimal("2.314023258"));
							put("BB Ações Siderurgia", 	new BigDecimal("0.838710723"));
							put("BB Ações BB", 			new BigDecimal("2.831299483"));
							put("BB Ações Const Civil", new BigDecimal("1.196936788"));
							put("BB Ações BDR Nivel I", new BigDecimal("1.096759343"));
							
						}
					});	
			put(LocalDate.of(2019, 01, 14), 
					new HashMap<String, BigDecimal>() 
			{
				{
					put("Tesouro IPCA+ 2024", new BigDecimal("2486.96"));
					put("Tesouro IPCA+ 2035", new BigDecimal("1474.99"));
					put("Tesouro Prefixado 2023", new BigDecimal("723.88"));
					put("Tesouro Prefixado 2025", new BigDecimal("598.06"));
					
					put("BB Ações Energia",		new BigDecimal("12.011539000"));												
					put("BB Ações Petrobras",	new BigDecimal("7.677386000"));
					put("BB Ações Dividendos", 	new BigDecimal("16.478736745"));
					put("BB Ações Exportação",	new BigDecimal("8.045854577"));
					put("BB Ações Vale",		new BigDecimal("16.104498855"));
					put("BB Acoes Aloc ETF FI", new BigDecimal("5.111463956"));
					put("BB Ações Consumo", 	new BigDecimal("2.346460816"));
					put("BB Ações Siderurgia", 	new BigDecimal("0.817151996"));
					put("BB Ações BB", 			new BigDecimal("2.908119414"));
					put("BB Ações Const Civil", new BigDecimal("1.203882824"));
					put("BB Ações BDR Nivel I", new BigDecimal("1.094493752"));
					
				}
			});	
		}
	};

	public CotacaoTituloDAO(Logger log) {
		this.log = log;
	}

	public BigDecimal paraTituloEm(String titulo, LocalDate em) {
		Map<String, BigDecimal> x = cotacoes.get(em);
		if (x != null) {
			BigDecimal y = x.get(titulo);
			if (y != null) {
				return y;
			}
		}
		log.warn(String.format("cotação não encontrada para o titulo '%s' em '%s'", titulo, em.toString()));
		return BigDecimal.ZERO;
	}
	
}
