package br.com.ljbm.fp.modelo;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-02-27T01:35:40.065-0300")
@StaticMetamodel(FundoInvestimento.class)
public class FundoInvestimento_ {
	public static volatile SingularAttribute<FundoInvestimento, Long> id;
	public static volatile SingularAttribute<FundoInvestimento, Integer> version;
	public static volatile SingularAttribute<FundoInvestimento, String> CNPJ;
	public static volatile SingularAttribute<FundoInvestimento, String> nome;
	public static volatile SingularAttribute<FundoInvestimento, BigDecimal> taxaImpostoRenda;
	public static volatile SingularAttribute<FundoInvestimento, TipoFundoInvestimento> tipoFundoInvestimento;
}
