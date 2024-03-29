/**
 * 
 */
package br.com.ljbm.fp.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
//import org.hibernate.cache.ehcache.*;.*;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.Logger;

import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.fp.modelo.Corretora;
import br.com.ljbm.fp.modelo.CotacaoFundo;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.SerieCoeficienteSELIC;

/**
 * Financas pessoais acesso ao modelo, implementacao
 * 
 * @author ljbm
 * @since 09/04/2012
 * 
 */
@Stateless
// @Interceptors(value={LogDesempenho.class})
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FPDominioImpl  {

	@PersistenceContext
	private EntityManager em;

	@PersistenceContext
	private EntityManager emSeries;
	
	@Inject
	Logger log;

	public FPDominioImpl() {
	}

	public FPDominioImpl(EntityManager em, EntityManager emSeries, Logger log) {
		super();
		this.em = em;
		this.emSeries = emSeries;
		this.log = log;
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Corretora addCorretora(Corretora corretora) throws FPException {
		em.persist(corretora);
		return corretora;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#addFundoInvestimento(br.com.ljbm
	 * .fp.modelo.FundoInvestimento)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FundoInvestimento addFundoInvestimento(FundoInvestimento fundoInvestimento) {
		em.persist(fundoInvestimento);
		return fundoInvestimento;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#deleteFundoInvestimento(br.com.ljbm
	 * .fp.modelo.FundoInvestimento)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteFundoInvestimento(FundoInvestimento fundoInvestimento) throws FPException {
		Long ide = fundoInvestimento.getIde();
		fundoInvestimento = em.find(FundoInvestimento.class, ide);
		if (fundoInvestimento == null) {
			throw new FPException("FundoInvestimento, Record for " + ide + " not found");
		} else {
			em.remove(fundoInvestimento);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#updateFundoInvestimento(br.com.ljbm
	 * .fp.modelo.FundoInvestimento)
	 */

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateFundoInvestimento(FundoInvestimento fundoInvestimento) throws FPException {
		FundoInvestimento c = em.find(FundoInvestimento.class, fundoInvestimento.getIde());
		if (c == null) {
			throw new FPException("FundoInvestimento, Record for " + fundoInvestimento.getIde() + " not found");
		} else {
			em.merge(fundoInvestimento);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#getFundoInvestimento(java.lang.Long)
	 */

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public FundoInvestimento getFundoInvestimento(Long ide) throws FPException {

		FundoInvestimento fundoInvestimento = em.find(FundoInvestimento.class, ide);
		if (fundoInvestimento == null) {
			throw new FPException("FundoInvestimento, record for ide = " + ide + " not found");
		} else {
			return fundoInvestimento;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.ljbm.fp.modelo.FPDominio#getFundoInvestimentoByAgenteCustodiaETitulo(
	 * java.lang.String, java.lang.String)
	 */

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<FundoInvestimento> getFundoInvestimentoByAgenteCustodiaETitulo(String agenteCustodia,
			String nomeTitulo) {

		TypedQuery<FundoInvestimento> query = em.createQuery(
				"select fi from FundoInvestimento fi join fi.corretora c where fi.nome=:titulo and c.razaoSocial=:agente",
				FundoInvestimento.class);
		query.setParameter("agente", agenteCustodia);
		query.setParameter("titulo", nomeTitulo);
		return query.getResultList();
		// if (fundoInvestimento == null) {
		// throw new FPException("FundoInvestimento, record not found");
		// } else {
		// return fundoInvestimento;
		// }
	}


	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public FundoInvestimento getFundoInvestimentoByCNPJ(String cnpj) throws FPException {

		CriteriaBuilder cb = em.getCriteriaBuilder(); // passo1
		CriteriaQuery<FundoInvestimento> cqry = cb.createQuery(FundoInvestimento.class);

		Root<FundoInvestimento> root = cqry.from(FundoInvestimento.class); // passo2
		cqry.select(root); // passo3

		Predicate pCNPJ = cb.equal(root.get("CNPJ"), cnpj); // Step
															// 4
		// Predicate pGtDateCreated=
		// cb.greaterThan(root.get("dateCreated"),date); //Step 4
		// Predicate pAnd = cb.and(pGtDateCreated,pGtAge); //Step 4

		cqry.where(pCNPJ);

		try {
			return em.createQuery(cqry).getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		// List<FundoInvestimento> a = em.createQuery(cqry).getResultList();
		//
		// if (a.isEmpty()) {
		// // throw new FPException("FundoInvestimento, Record for " + cnpj
		// // + " not found");
		// return null;
		// } else {
		// return a.get(0);
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#getAllFundoInvestimento()
	 */

	// @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<FundoInvestimento> getAllFundoInvestimento() {

		// return em.createNamedQuery("FundoInvestimento.Todos").getResultList();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FundoInvestimento> criteria = cb.createQuery(FundoInvestimento.class);
		Root<FundoInvestimento> fundoInvestimento = criteria.from(FundoInvestimento.class);
//		Predicate p = cb.equal(fundoInvestimento.get("tipoFundoInvestimento"), 2); 
//		criteria.where(p);
//		criteria.select(fundoInvestimento);
		criteria.select(fundoInvestimento)
			.orderBy(
					cb.asc(fundoInvestimento.get("corretora"))
					, cb.asc(fundoInvestimento.get("tipoFundoInvestimento"))
					, cb.asc(fundoInvestimento.get("nome")));
		return em.createQuery(criteria).getResultList();
	}


	public List<FundoInvestimento> retrieveFundosInvestimentoOrderedByName() {
		// JPA 2's criteria API is used to create a list of members sorted by
		// name. You can try out the type safe criteria API as well by swapping
		// the criteria statements as described
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FundoInvestimento> criteria = cb.createQuery(FundoInvestimento.class);
		Root<FundoInvestimento> fundoInvestimento = criteria.from(FundoInvestimento.class);
		// TODO experimentar "type-safe criteria queries" conforme abaixo
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new feature in JPA 2.0
		// criteria.select(fundoInvestimento).orderBy(cb.asc(fundoInvestimento.get(FundoInvestimento_.nome)));
		criteria.select(fundoInvestimento).orderBy(cb.asc(fundoInvestimento.get("nome")));
		return em.createQuery(criteria).getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#addAplicacao(br.com.ljbm.fp.modelo
	 * .Aplicacao)
	 */

	public Aplicacao addAplicacao(Aplicacao aplicacao) throws FPException {
		em.persist(aplicacao);
		return aplicacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#updateAplicacao(br.com.ljbm.fp.modelo
	 * .Aplicacao)
	 */

	public void updateAplicacao(Aplicacao aplicacao) throws FPException {

		Aplicacao s = em.find(Aplicacao.class, aplicacao.getDocumento());
		if (s == null) {
			throw new FPException("Aplicacao, Record for " + aplicacao.getDocumento() + " not found");
		} else {
			em.merge(aplicacao);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#deleteAplicacao(br.com.ljbm.fp.modelo
	 * .Aplicacao)
	 */

	public void deleteAplicacao(Aplicacao aplicacao) throws FPException {
		Long documento = aplicacao.getDocumento();
		aplicacao = em.find(Aplicacao.class, documento);
		if (aplicacao == null) {
			throw new FPException("Aplicacao, Record for " + documento + " not found");
		} else {
			em.remove(aplicacao);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#getAplicacao(java.lang.Long)
	 */

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Aplicacao getAplicacao(Long ide) throws FPException {
		Aplicacao aplicacao = em.find(Aplicacao.class, ide);
		if (aplicacao == null) {
			throw new FPException("Aplicacao, Record for " + ide + " not found");
		} else {
			return aplicacao;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#getAllAplicacao()
	 */

	public List<Aplicacao> getAllAplicacao() throws FPException {

		Query query = em.createNamedQuery("Aplicacao.Todos");
		// Query query =
		// em.createNativeQuery("SELECT ide, descricao FROM tipo_historico",
		// Aplicacao.class);
		@SuppressWarnings("unchecked")
		List<Aplicacao> lista = query.getResultList();
		// return (Aplicacao[])lista.toArray(new Aplicacao[0]);
		return lista;
	}


	public Corretora getCorretora(Long ide) throws FPException {
		Corretora obj = em.find(Corretora.class, ide);
		if (obj != null) {
			return obj;
		} else {
			String message = String.format("Corretora, Record for id %d not found", ide);
			log.debug(message);
			throw new FPException(message);
		}
	}


	public Corretora getFundosCorretora(Long ide) {

		TypedQuery<Corretora> query = em.createQuery(
				"select c from Corretora c join fetch c.fundosComprados where c.ide=:ide", Corretora.class);
		query.setParameter("ide", ide);
		return query.getSingleResult();
	}


	public BigDecimal getCoeficienteSELIC(LocalDate dataCompra, LocalDate dataAlvo) {
		TypedQuery<BigDecimal> query = emSeries.createQuery(
				"select c.fator from SerieCoeficienteSELIC c where c.dataInicio=:dataCompra and c.dataFim=:dataAlvo"
				, BigDecimal.class);
		query.setParameter("dataCompra", dataCompra);
		query.setParameter("dataAlvo", dataAlvo);
		try {
			return query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}


	public void addCoeficienteSELIC(LocalDate dataCompra, LocalDate dataAlvo,
			BigDecimal fatorRemuneracaoAcumuladaSELIC) {
		if (! emSeries.getTransaction().isActive()) {
			emSeries.getTransaction().begin();
		}
		SerieCoeficienteSELIC x = new SerieCoeficienteSELIC();
		x.setDataFim(dataAlvo);
		x.setDataInicio(dataCompra);
		x.setFator(fatorRemuneracaoAcumuladaSELIC);
		emSeries.persist(x);
		emSeries.getTransaction().commit();
//		emSeries.flush();
		
	}

	// public FundoInvestimento[] getAllLancamentoCCbyTipoHistorico(Short ide)
	// throws FPException {
	//
	// Query query = em.createNamedQuery("FundoInvestimento.findByHistorico");
	// query.setParameter("ide_tipo_historico", ide);
	// List<FundoInvestimento> lancsCC = query.getResultList();
	// if (lancsCC.size() == 0) {
	// throw new FPException("LancamentosCC for Historico " + ide
	// + " not found");
	// } else {
	// return (FundoInvestimento[]) lancsCC
	// .toArray(new FundoInvestimento[0]);
	// }
	// }

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal getCotacaoPorTituloData(FundoInvestimento fi, LocalDate dataCotacao) {
		TypedQuery<CotacaoFundo> query = em.createQuery(
				"select c from CotacaoFundo c where c.fundoInvestimento=:fundo and c.dataCotacao=:dataCotacao",
				CotacaoFundo.class);
		query.setParameter("dataCotacao", dataCotacao);
		query.setParameter("fundo", fi);
		try {			
			return query.getSingleResult().getValorCota();
		} catch (NoResultException e) {
			return BigDecimal.ZERO;
		}
		
	}
}
