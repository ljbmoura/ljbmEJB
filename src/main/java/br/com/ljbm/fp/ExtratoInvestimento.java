/**
 * 
 */
package br.com.ljbm.fp;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.utilitarios.FormatadorBR;

/**
 * @author guest
 * 
 */


public class ExtratoInvestimento {

	private Calendar data;
	private BigDecimal valorCotaData;
	private List<Aplicacao> aplicacoes;
	private FundoInvestimento fundoInvestimento;

	/**
	 * @param data
	 * @param valorCotaData
	 * @param aplicacoes
	 * @param fundoInvestimento
	 */
	public ExtratoInvestimento(Calendar data, BigDecimal valorCotaData,
			List<Aplicacao> aplicacoes, FundoInvestimento fundoInvestimento) {
		this.data = data;
		this.valorCotaData = valorCotaData;
		this.aplicacoes = aplicacoes;
		this.fundoInvestimento = fundoInvestimento;
	}

	/**
	 * @return the data
	 */
	public Calendar getData() {
		return data;
	}

	/**
	 * @return the valorCota
	 */
	public BigDecimal getValorCotaData() {
		return valorCotaData;
	}

	/**
	 * @return the aplicacoes
	 */
	public List<Aplicacao> getAplicacoes() {
		return aplicacoes;
	}

	/**
	 * @return the fundoInvestimento
	 */
	public FundoInvestimento getFundoInvestimento() {
		return fundoInvestimento;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\nExtratoInvestimento [fundoInvestimento="
				+ fundoInvestimento.getNome() + " ,data="
				+ FormatadorBR.formataDataCurta(data) + ", valorCotaData="
				+ FormatadorBR.formataDecimal(valorCotaData) + ", aplicacoes="
				+ aplicacoes.toString() + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((aplicacoes == null) ? 0 : aplicacoes.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime
				* result
				+ ((fundoInvestimento == null) ? 0 : fundoInvestimento
						.hashCode());
		result = prime * result
				+ ((valorCotaData == null) ? 0 : valorCotaData.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtratoInvestimento other = (ExtratoInvestimento) obj;
		if (aplicacoes == null) {
			if (other.aplicacoes != null)
				return false;
		} else if (!aplicacoes.equals(other.aplicacoes))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (fundoInvestimento == null) {
			if (other.fundoInvestimento != null)
				return false;
		} else if (!fundoInvestimento.equals(other.fundoInvestimento))
			return false;
		if (valorCotaData == null) {
			if (other.valorCotaData != null)
				return false;
		} else if (!valorCotaData.equals(other.valorCotaData))
			return false;
		return true;
	}

}
