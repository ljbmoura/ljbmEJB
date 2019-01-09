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
