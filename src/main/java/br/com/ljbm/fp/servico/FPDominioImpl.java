/**
 * 
 */
package br.com.ljbm.fp.servico;

//import org.hibernate.cache.ehcache.*;.*;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
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
import br.com.ljbm.fp.modelo.FundoInvestimento;

/**
 * Financas pessoais acesso ao modelo, implementacao
 * 
 * @author ljbm
 * @since 09/04/2012
 * 
 */
@Stateless
@Remote(FPDominio.class)
//@Interceptors(value={LogDesempenho.class})
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FPDominioImpl implements FPDominio {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	Logger log;

	public FPDominioImpl() {
	}

	protected FPDominioImpl(EntityManager em, Logger log) {
		super();
		this.em = em;
		this.log = log;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Corretora addCorretora(Corretora corretora) throws FPException {
		try {
			em.persist(corretora);
			return corretora;
		} catch (EntityExistsException ex) {
			throw new FPException("Corretora, Duplicate Ide : " + corretora.getIde());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#addFundoInvestimento(br.com.ljbm
	 * .fp.modelo.FundoInvestimento)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FundoInvestimento addFundoInvestimento(FundoInvestimento fundoInvestimento) {
//		try {
			em.persist(fundoInvestimento);
			return fundoInvestimento;
//		} catch (PersistenceException pe) {
//			log.error(pe.getMessage());
//			throw new FPException(String.format("Erro ao inserir FundoInvestimento: %s ", pe.getMessage()));
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#deleteFundoInvestimento(br.com.ljbm
	 * .fp.modelo.FundoInvestimento)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
	@Override
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
	@Override
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public FundoInvestimento getFundoInvestimento(Long ide) throws FPException {
		
//		TypedQuery<FundoInvestimento> query = em.createQuery("select fi from FundoInvestimento fi join fetch fi.corretora where fi.ide=:ide", FundoInvestimento.class);
//		query.setParameter("ide", ide);
//		FundoInvestimento fundoInvestimento = query.getSingleResult();
		FundoInvestimento fundoInvestimento = em.find(FundoInvestimento.class, ide);
		if (fundoInvestimento == null) {
			throw new FPException("FundoInvestimento, Record for " + ide + " not found");
		} else {
			return fundoInvestimento;
		}
	}

	@Override
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
	@Override
//	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<FundoInvestimento> getAllFundoInvestimento() {

//		return em.createNamedQuery("FundoInvestimento.Todos").getResultList();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FundoInvestimento> criteria = cb.createQuery(FundoInvestimento.class);
		Root<FundoInvestimento> fundoInvestimento = criteria.from(FundoInvestimento.class);
		criteria.select(fundoInvestimento);
		return em.createQuery(criteria).getResultList();
	}

	@Override
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
	@Override
	public void addAplicacao(Aplicacao aplicacao) throws FPException {
		try {
			em.persist(aplicacao);
		} catch (EntityExistsException exe) {
			throw new FPException("Aplicacao, Duplicate Ide : " + aplicacao.getDocumento());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#updateAplicacao(br.com.ljbm.fp.modelo
	 * .Aplicacao)
	 */
	@Override
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
	@Override
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
	@Override
	public Aplicacao getAplicacao(Long documento) throws FPException {
		Aplicacao aplicacao = em.find(Aplicacao.class, documento);
		if (aplicacao == null) {
			throw new FPException("Aplicacao, Record for " + documento + " not found");
		} else {
			return aplicacao;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.ljbm.fp.modelo.FPDominio#getAllAplicacao()
	 */
	@Override
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

	@Override
	public Corretora getCorretora(Long ide) throws FPException {
		Corretora obj = em.find(Corretora.class, ide);
		if (obj == null) {
			log.info("Corretora, Record for ide " + ide + " not found");
			throw new FPException("Corretora, Record for ide " + ide + " not found");
		} else {
			return obj;
		}
	}
	
	public List<FundoInvestimento> getFundosCorretora(Long ide) {

		TypedQuery<FundoInvestimento> query = em.createQuery(
				"select fi from FundoInvestimento fi where fi.corretora.ide=:ide", FundoInvestimento.class);
		query.setParameter("ide", ide);
		return query.getResultList();
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

}
