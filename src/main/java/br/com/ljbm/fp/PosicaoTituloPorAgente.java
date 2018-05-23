package br.com.ljbm.fp;

import java.time.LocalDate;
import java.util.List;

import br.com.ljbm.fp.modelo.Aplicacao;

public class PosicaoTituloPorAgente {
	private String titulo; 				// Tesouro IPCA+ 2024
	private String agenteCustodia; 		// BB BANCO DE INVESTIMENTO S/A
	private LocalDate posicaoEm;   		// 14/05/2018
	private LocalDate vencimentoTitulo; // 15/08/2024	
	
	private List<Aplicacao> compras; // 
	// data compra		quantidade cotas compra		valor cota
	// 9/04/2007		5,20						555,95

	public String getTitulo() {
		return titulo;
	}

	public String getAgenteCustodia() {
		return agenteCustodia;
	}

	public LocalDate getPosicaoEm() {
		return posicaoEm;
	}

	public LocalDate getVencimentoTitulo() {
		return vencimentoTitulo;
	}

	public List<Aplicacao> getCompras() {
		return compras;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setAgenteCustodia(String agenteCustodia) {
		this.agenteCustodia = agenteCustodia;
	}

	public void setPosicaoEm(LocalDate posicaoEm) {
		this.posicaoEm = posicaoEm;
	}

	public void setVencimentoTitulo(LocalDate vencimentoTitulo) {
		this.vencimentoTitulo = vencimentoTitulo;
	}

	public void setCompras(List<Aplicacao> compras) {
		this.compras = compras;
	}


}
