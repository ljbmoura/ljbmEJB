package br.com.ljbm.fp.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CotacaoTituloDAO {
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
							put("Tesouro Prefixado 2023", new BigDecimal("637.54"));
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


		}
	};

	public BigDecimal paraTituloEm(String titulo, LocalDate em) {
		Map<String, BigDecimal> x = cotacoes.get(em);
		if (x != null) {
			BigDecimal y = x.get(titulo);
			if (y != null) {
				return y;
			}
		}
		return BigDecimal.ZERO;
	}
	
}
