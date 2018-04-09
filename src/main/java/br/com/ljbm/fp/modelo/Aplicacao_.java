package br.com.ljbm.fp.modelo;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-02-27T01:35:40.024-0300")
@StaticMetamodel(Aplicacao.class)
public class Aplicacao_ {
	public static volatile SingularAttribute<Aplicacao, Calendar> data;
	public static volatile SingularAttribute<Aplicacao, Long> documento;
	public static volatile SingularAttribute<Aplicacao, BigDecimal> valorAplicado;
	public static volatile SingularAttribute<Aplicacao, BigDecimal> quantidadeCotas;
	public static volatile SingularAttribute<Aplicacao, BigDecimal> saldoCotas;
}
