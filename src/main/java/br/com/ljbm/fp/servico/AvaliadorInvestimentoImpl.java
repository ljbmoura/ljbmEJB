/**
 * 
 */
package br.com.ljbm.fp.servico;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

import br.com.ljbm.fp.ExtratoInvestimento;
import br.com.ljbm.fp.LeitorExtratoInvestimentosBB;
import br.com.ljbm.fp.LeitorExtratoTesouroDireto;
import br.com.ljbm.fp.PosicaoTituloPorAgente;
import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.fp.modelo.ComparacaoInvestimentoVersusSELIC;
import br.com.ljbm.utilitarios.FormatadorBR;
import br.com.ljbm.ws.bc.Selic;

@Stateless
@Remote(AvaliadorInvestimento.class)
public class AvaliadorInvestimentoImpl implements AvaliadorInvestimento {

//	private static final String EXTRATO_INVESTIMENTO_BB_ATUAL = resourcesDir
//			.getPath() + File.separator + "extratoInvestimentosAtual.txt";

//	private static final String SERIE_HISTORICA_DIARIA_SELIC = resourcesDir
//			.getPath() + File.separator + "TaxaSelic_Diaria_20060102_Atual.txt";

//	private static final String LINHA_FORMATACAO_VISAO_GERAL = "%s\t %s\t %s\t %s\t %s\t %s";

//	private static final String CESTAS_COMPRAS_TD_ATUAL = ;
	
	private static final MathContext MC_BR = new MathContext(17, RoundingMode.DOWN);
	
	private Logger log;
	
	private CotacaoTituloDAO daoCotacoes;
	
	private Selic selicWS;
	
	private String cestaComprasTD;

	@EJB
	FPDominio model;

	/**
	 * 
	 */
	@Inject
	public AvaliadorInvestimentoImpl(String cestaComprasTD, Selic selicWS, CotacaoTituloDAO daoCotacoes, Logger log
			, FPDominio model) {
		this.cestaComprasTD = cestaComprasTD;
		this.selicWS = selicWS;
		this.daoCotacoes =  daoCotacoes;
		this.log = log;
		this.model = model;
	}


	@Override
	public List<ComparacaoInvestimentoVersusSELIC> comparaInvestimentosComSELIC(
			String dataPosicao) {

		List<ComparacaoInvestimentoVersusSELIC> comparativoTD =  null;
		try {
			comparativoTD = 
				this.comparaInvestimentosFrenteSELICEm(
					leCestaComprasTD(cestaComprasTD)
					, dataPosicao);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return comparativoTD;
	}

//	@Override
//	public void atualizaBaseInvestimentos_CotacaoSELIC(
//			String caminhaExtratoInvestimentosBB,
//			String caminhoSerieHistoricaSELIC_BC) {
//
//		List<ExtratoInvestimento> extratoInvestimentos = leExtratoInvestimentosBB(caminhaExtratoInvestimentosBB);
//
//		for (ExtratoInvestimento ei : extratoInvestimentos) {
//			try {
//				FundoInvestimento fi = ei.getFundoInvestimento();
//				System.out.println("Sincronizando " + fi.toString());
//				FundoInvestimento existente;
//				if (null != (existente = model.getFundoInvestimentoByCNPJ(fi
//						.getCNPJ()))) {
//					System.out.println("Sincronizado " + fi.toString());
//					existente.setNome(fi.getNome());
//					existente.setTaxaImpostoRenda(fi.getTaxaImpostoRenda());
//					model.updateFundoInvestimento(existente);
//
//				} else {
//					model.addFundoInvestimento(fi);
//				}
//
//			} catch (FPException e) {
//				throw new IllegalArgumentException(e.getMessage());
//			}
//		}
//
//	}
//	private List<ComparacaoInvestimentoVersusSELIC> comparaInvestimentosFrenteSELICEm(
//			String dataPosicao, List<ExtratoInvestimento> extratoInvestimentos,
//			SerieHistorica<BigDecimal> serieCotacaoSELIC) {
//
//		ArrayList<ComparacaoInvestimentoVersusSELIC> ret = new ArrayList<ComparacaoInvestimentoVersusSELIC>(
//				0);
//		for (ExtratoInvestimento ei : extratoInvestimentos) {
//			ret.add(comparativoExtratoInvestimento_X_SELIC(serieCotacaoSELIC,
//					ei));
//		}
//		return ret;
//	}
	
//	private List<ComparacaoInvestimentoVersusSELIC> comparaInvestimentosFrenteSELICEm(String dataPosicao,
//			List<ExtratoInvestimento> extratoInvestimentos) {
//		ArrayList<ComparacaoInvestimentoVersusSELIC> ret = new ArrayList<ComparacaoInvestimentoVersusSELIC>(0);
//		for (ExtratoInvestimento extrato : extratoInvestimentos) {
//			ret.add(comparativoExtratoInvestimento_X_SELIC(dataPosicao, extrato));
//		}
//		return ret;
//	}
	private List<ComparacaoInvestimentoVersusSELIC> comparaInvestimentosFrenteSELICEm(
			List<PosicaoTituloPorAgente> extrato 
			,String dataPosicao	){
		
		ArrayList<ComparacaoInvestimentoVersusSELIC> ret = new ArrayList<ComparacaoInvestimentoVersusSELIC>(0);
		for (PosicaoTituloPorAgente posicao : extrato) {
			ret.add(comparativoExtratoInvestimento_X_SELIC(dataPosicao, posicao));
		}
		return ret;
	}

	@Override
	public String getMessage() {

		return "EJB AvaliadorInvestimento dispon�vel por essa 'view'.";
	}

	private List<ExtratoInvestimento> leExtratoInvestimentosBB(
			String caminhaExtratoInvestimentosBB) {

		LeitorExtratoInvestimentosBB l = new LeitorExtratoInvestimentosBB();
		return l.analisador(caminhaExtratoInvestimentosBB);

	}


//	private List<ExtratoInvestimento> leCestaComprasTD(
//			String caminhoCestaCompras) {
//
//		LeitorCestasCompraTesouroDireto l = new LeitorCestasCompraTesouroDireto(
//				caminhoCestaCompras);
//		return l.analisador();
//
//	}
	private List<PosicaoTituloPorAgente> leCestaComprasTD(
			String caminhoCestaCompras) throws IOException {
		
		LeitorExtratoTesouroDireto leitor = new LeitorExtratoTesouroDireto(caminhoCestaCompras);
		leitor.le();
		return leitor.extratoLido();
	}

	private ComparacaoInvestimentoVersusSELIC comparativoExtratoInvestimento_X_SELIC(
			String dataPosicao, PosicaoTituloPorAgente posicao) {

		LocalDate dataAlvo = LocalDate.from(FormatadorBR.paraLocalDate(dataPosicao));

		BigDecimal totalAplicado = BigDecimal.ZERO;
		BigDecimal totalAtualBruto = BigDecimal.ZERO;
		BigDecimal totalEquivalenteSELIC = BigDecimal.ZERO;
		BigDecimal totalDiferencaSELIC = BigDecimal.ZERO;
		
		for (Aplicacao compra : posicao.getCompras()) {
			
			LocalDate dataCompra = LocalDate.of(
					compra.getData().get(Calendar.YEAR)
					,compra.getData().get(Calendar.MONTH)+1
					,compra.getData().get(Calendar.DAY_OF_MONTH));
			
			BigDecimal fatorRemuneracaoAcumuladaSELIC = selicWS.fatorAcumuladoSelic(dataCompra, dataAlvo);
			
			BigDecimal vEquivalenteSELIC = compra.getValorAplicado().multiply(fatorRemuneracaoAcumuladaSELIC);
			BigDecimal vAtualBruto = compra.getSaldoCotas().multiply(
					daoCotacoes.paraTituloEm(posicao.getTitulo(), dataAlvo));

			log.debug("valor Atual no fundo   : " + vAtualBruto);
			log.debug("valor Equivalente SELIC: " + vEquivalenteSELIC);

			totalAplicado = totalAplicado.add(compra.getValorAplicadoRemanescente());
			totalAtualBruto = totalAtualBruto.add(vAtualBruto);
			totalEquivalenteSELIC = totalEquivalenteSELIC.add(vEquivalenteSELIC);
			totalDiferencaSELIC = totalDiferencaSELIC.add(vAtualBruto.subtract(vEquivalenteSELIC));

		}
		BigDecimal rentabilidadeFundo = totalAtualBruto
				.subtract(totalAplicado)
				.divide(totalAplicado, 6, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.0"));
		BigDecimal rentabilidadeEquivSELIC = totalEquivalenteSELIC
				.subtract(totalAplicado)
				.divide(totalAplicado, 6, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.0"));

		return new ComparacaoInvestimentoVersusSELIC(posicao.getTitulo(),
				rentabilidadeFundo, rentabilidadeEquivSELIC,
				totalDiferencaSELIC, totalAtualBruto, totalEquivalenteSELIC);

	}


	private ComparacaoInvestimentoVersusSELIC comparativoExtratoInvestimento_X_SELIC(
			String dataPosicao,
			ExtratoInvestimento e) {
		
		BigDecimal totalValorAplicado = BigDecimal.ZERO;
		BigDecimal totalValorAtual = BigDecimal.ZERO;
		BigDecimal totalValorEquivSELIC = BigDecimal.ZERO;
		BigDecimal totalDiferencaSELIC = BigDecimal.ZERO;
		
		for (Aplicacao a : e.getAplicacoes()) {
			BigDecimal coeficiente = 
					selicWS.fatorAcumuladoSelic (
							LocalDate.of (
								a.getData().get(Calendar.YEAR), 
								a.getData().get(Calendar.MONTH) + 1, 
								a.getData().get(Calendar.DAY_OF_MONTH))
							, FormatadorBR.paraLocalDate (dataPosicao));
			
			BigDecimal vRemunerado = a.getValorAplicado().multiply(coeficiente, MC_BR); 
			log.debug(String.format("valor %s remunerado pela SELIC entre %s e %s (* %s): %s",
					a.getValorAplicado()
					, FormatadorBR.formataDataCurta(a.getData())
					, dataPosicao
					, coeficiente
					, FormatadorBR.formataDecimal(vRemunerado, 2)));
					
			BigDecimal valorAtual = a.getSaldoCotas().multiply(
					e.getValorCotaData());

			BigDecimal valorAplicadoRemanescente = a
					.getValorAplicadoRemanescente();

			totalValorAplicado = totalValorAplicado
					.add(valorAplicadoRemanescente);
			totalValorAtual = totalValorAtual.add(valorAtual);
			totalValorEquivSELIC = totalValorEquivSELIC.add(vRemunerado);
			totalDiferencaSELIC = totalDiferencaSELIC.add(valorAtual
					.subtract(vRemunerado));

		}
		BigDecimal rentabilidadeFundo = totalValorAtual
				.subtract(totalValorAplicado)
				.divide(totalValorAplicado, 6, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.0"));
		BigDecimal rentabilidadeEquivSELIC = totalValorEquivSELIC
				.subtract(totalValorAplicado)
				.divide(totalValorAplicado, 6, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.0"));

		return new ComparacaoInvestimentoVersusSELIC(e.getFundoInvestimento().getNome(),
				rentabilidadeFundo, rentabilidadeEquivSELIC,
				totalDiferencaSELIC, totalValorAtual, totalValorEquivSELIC);
	}

//	private ComparacaoInvestimentoVersusSELIC comparativoExtratoInvestimento_X_SELIC(
//			String dataPosicao,
//			ExtratoInvestimento e) {
//		
//		BigDecimal totalValorAplicado = BigDecimal.ZERO;
//		BigDecimal totalValorAtual = BigDecimal.ZERO;
//		BigDecimal totalValorEquivSELIC = BigDecimal.ZERO;
//		BigDecimal totalDiferencaSELIC = BigDecimal.ZERO;
//		
//		for (Aplicacao a : e.getAplicacoes()) {
//			BigDecimal coeficiente = 
//					selicWS.fatorAcumuladoSelic (
//							LocalDate.of (
//								a.getData().get(Calendar.YEAR), 
//								a.getData().get(Calendar.MONTH) + 1, 
//								a.getData().get(Calendar.DAY_OF_MONTH))
//							, FormatadorBR.paraLocalDate (dataPosicao));
//			
//			BigDecimal vRemunerado = a.getValorAplicado().multiply(coeficiente, MC_BR); 
//			log.debug(String.format("valor %s remunerado pela SELIC entre %s e %s (* %s): %s",
//					a.getValorAplicado()
//					, FormatadorBR.formataDataCurta(a.getData())
//					, dataPosicao
//					, coeficiente
//					, FormatadorBR.formataDecimal(vRemunerado, 2)));
//					
//			BigDecimal valorAtual = a.getSaldoCotas().multiply(
//					e.getValorCotaData());
//
//			BigDecimal valorAplicadoRemanescente = a
//					.getValorAplicadoRemanescente();
//
//			totalValorAplicado = totalValorAplicado
//					.add(valorAplicadoRemanescente);
//			totalValorAtual = totalValorAtual.add(valorAtual);
//			totalValorEquivSELIC = totalValorEquivSELIC.add(vRemunerado);
//			totalDiferencaSELIC = totalDiferencaSELIC.add(valorAtual
//					.subtract(vRemunerado));
//
//		}
//		BigDecimal rentabilidadeFundo = totalValorAtual
//				.subtract(totalValorAplicado)
//				.divide(totalValorAplicado, 6, RoundingMode.DOWN)
//				.multiply(new BigDecimal("100.0"));
//		BigDecimal rentabilidadeEquivSELIC = totalValorEquivSELIC
//				.subtract(totalValorAplicado)
//				.divide(totalValorAplicado, 6, RoundingMode.DOWN)
//				.multiply(new BigDecimal("100.0"));
//
//		return new ComparacaoInvestimentoVersusSELIC(e.getFundoInvestimento().getNome(),
//				rentabilidadeFundo, rentabilidadeEquivSELIC,
//				totalDiferencaSELIC, totalValorAtual, totalValorEquivSELIC);
//	}
	

//	private BigDecimal remuneraSELIC(BigDecimal valorAplicado,
//			BigDecimal vSelicEpoca, BigDecimal vSelicHoje) {
//		return valorAplicado.multiply(vSelicHoje.divide(vSelicEpoca,
//				RoundingMode.DOWN));
//	}


	public void imprimeComparacaoInvestComSELIC(List<ComparacaoInvestimentoVersusSELIC> comparativo) {
		System.out.println( 
				String.format( "%30s %7s %7s %15s %15s %15s"
					,"fundo"
					,"%Rentab"
					,"%RSelic"
					,"Diferença"
					,"Valor Fundo"
					,"Valor Eq Selic"
				));
				
		for (ComparacaoInvestimentoVersusSELIC r : comparativo) {
			System.out.println(
				String.format( 

//					LINHA_FORMATACAO_VISAO_GERAL
					"%30s %7.2f %7.2f %15.2f %15.2f %15.2f"
					
					, r.getNomeInvestimento(),

					// % Rentabilidade do Fundo
					r.getTaxaRentabilidadeFundo(),

					// % Rentabilidade SELIC Equivalente ao Fundo
					r.getTaxaRentabilidadeSELICEquivalenteFundo(),

					// Diferença Rentabilidade FUNDO X SELIC
					r.getDiferencaRentabilidadeFundoSELIC(),

					// Total Valor Fundo
					r.getTotalValorFundo(),

					// Total Valor Equiv. SELIC
					r.getTotalValorEquivalenteSELIC()

			) );
			
		}
		
		
	}

	// private static void atualizaBasePosicaoFinanceira() {
	//
	// AvaliadorInvestimento avaliadorInvestimento = new
	// AvaliadorInvestimento();
	//
	// avaliadorInvestimento.atualizaBaseInvestimentos_CotacaoSELIC(
	// EXTRATO_INVESTIMENTO_BB_ATUAL, SERIE_HISTORICA_DIARIA_SELIC);
	//
	// }

	// private static void posicaoFinanceiraBBViaBD() {
	//
	// AvaliadorInvestimento avaliadorInvestimento = new
	// AvaliadorInvestimento();
	// List<ComparativoFundoSELIC> ret = avaliadorInvestimento
	// .comparaInvestimentosFrenteSELIC("15-04-2012");
	//
	// for (ComparativoFundoSELIC r : ret) {
	// System.out.print(formataComparativoFundoSELIC(r) + "\r");
	// }
	// }

//	private static void posicaoFinanceira() {
//		Selic w = new Selic(log);
//		AvaliadorInvestimentoImpl avaliador = new AvaliadorInvestimentoImpl(w, new CotacaoTituloDAO(), log);
//
//		List<ComparacaoInvestimentoVersusSELIC> comparativo = avaliador
//				.comparaInvestimentosComSELIC("06/04/2018");
//		System.out.print(
//				String.format("%30s %7s %7s %15s %15s %15s"
//					,"fundo"
//					,"%Rentab"
//					,"%RSelic"
//					,"Diferença"
//					,"Valor Fundo"
//					,"Valor Eq Selic"
//				));
//				
//		for (ComparacaoInvestimentoVersusSELIC r : comparativo) {
//			System.out.print(formataComparativoComSELIC(r) + "\r");
//		}
//	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		posicaoFinanceira();
//		
////		 atualizaBasePosicaoFinanceira();
//	}
}

