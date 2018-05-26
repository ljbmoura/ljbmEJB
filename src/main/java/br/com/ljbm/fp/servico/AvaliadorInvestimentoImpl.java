/**
 * 
 */
package br.com.ljbm.fp.servico;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

import br.com.ljbm.fp.ExtratoInvestimento;
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


	private static final MathContext MC_BR = new MathContext(17, RoundingMode.DOWN);
	
	private Logger log;
	
	private CotacaoTituloDAO daoCotacoes;
	
	private Selic selicWS;
	
	private LeitorExtratoTesouroDireto leitor;

	@EJB
	FPDominio model;

	@Inject
	public AvaliadorInvestimentoImpl(LeitorExtratoTesouroDireto leitor, Selic selicWS, CotacaoTituloDAO daoCotacoes,
			Logger log, FPDominio model) {
		this.leitor = leitor;
		this.selicWS = selicWS;
		this.daoCotacoes = daoCotacoes;
		this.log = log;
		this.model = model;
	}

	@Override
	public List<ComparacaoInvestimentoVersusSELIC> comparaInvestimentosComSELIC(String dataPosicao) {

		ArrayList<ComparacaoInvestimentoVersusSELIC> ret = new ArrayList<ComparacaoInvestimentoVersusSELIC>(0);
		for (PosicaoTituloPorAgente posicao : leitor.extratoLido()) {
			ret.add(comparativoExtratoInvestimento_X_SELIC(dataPosicao, posicao));
		}
		return ret;
	}

	@Override
	public String getMessage() {

		return "EJB AvaliadorInvestimento disponivel por essa 'view'.";
	}

//	private List<ExtratoInvestimento> leExtratoInvestimentosBB(
//			String caminhaExtratoInvestimentosBB) {
//
//		LeitorExtratoInvestimentosBB l = new LeitorExtratoInvestimentosBB();
//		return l.analisador(caminhaExtratoInvestimentosBB);
//	}


	private ComparacaoInvestimentoVersusSELIC comparativoExtratoInvestimento_X_SELIC(
			String dataPosicao, PosicaoTituloPorAgente posicao) {

		LocalDate dataAlvo = LocalDate.from(FormatadorBR.paraLocalDate(dataPosicao));

		BigDecimal totalAplicado = BigDecimal.ZERO;
		BigDecimal totalAtualBruto = BigDecimal.ZERO;
		BigDecimal totalEquivalenteSELIC = BigDecimal.ZERO;
		BigDecimal totalDiferencaSELIC = BigDecimal.ZERO;
		
		for (Aplicacao compra : posicao.getCompras()) {
			
					
			BigDecimal fatorRemuneracaoAcumuladaSELIC = selicWS.fatorAcumuladoSelic(compra.getDataCompra(), dataAlvo);
			
			BigDecimal vEquivalenteSELIC = compra.getValorAplicado().multiply(fatorRemuneracaoAcumuladaSELIC, MC_BR);
			BigDecimal vAtualBruto = compra.getSaldoCotas().multiply(
					daoCotacoes.paraTituloEm(posicao.getTitulo(), dataAlvo), MC_BR);

			log.debug("valor Atual no fundo   : " + vAtualBruto);
			log.debug("valor Equivalente SELIC: " + vEquivalenteSELIC);

			totalAplicado = totalAplicado.add(compra.getValorAplicadoRemanescente());
			totalAtualBruto = totalAtualBruto.add(vAtualBruto);
			totalEquivalenteSELIC = totalEquivalenteSELIC.add(vEquivalenteSELIC);
			totalDiferencaSELIC = totalDiferencaSELIC.add(vAtualBruto.subtract(vEquivalenteSELIC));

		}
		BigDecimal rentabilidadeFundo = totalAtualBruto
				.subtract(totalAplicado)
				.divide(totalAplicado, 2, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.00"));
		BigDecimal rentabilidadeEquivSELIC = totalEquivalenteSELIC
				.subtract(totalAplicado)
				.divide(totalAplicado, 2, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.00"));

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
								a.getDataCompra()
							, FormatadorBR.paraLocalDate (dataPosicao));
			
			BigDecimal vRemunerado = a.getValorAplicado().multiply(coeficiente, MC_BR); 
			log.debug(String.format("valor %s remunerado pela SELIC entre %s e %s (* %s): %s",
					a.getValorAplicado()
					, a.getDataCompra()
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
				.divide(totalValorAplicado, 2, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.00"));
		BigDecimal rentabilidadeEquivSELIC = totalValorEquivSELIC
				.subtract(totalValorAplicado)
				.divide(totalValorAplicado, 2, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.00"));

		return new ComparacaoInvestimentoVersusSELIC(e.getFundoInvestimento().getNome(),
				rentabilidadeFundo, rentabilidadeEquivSELIC,
				totalDiferencaSELIC, totalValorAtual, totalValorEquivSELIC);
	}

	

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

					"%30s %7.2f %7.2f %15.2f %15.2f %15.2f"
					
					, r.getNomeInvestimento(),

					r.getTaxaRentabilidadeFundo(),

					r.getTaxaRentabilidadeSELICEquivalenteFundo(),

					r.getDiferencaRentabilidadeFundoSELIC(),

					r.getTotalValorFundo(),

					r.getTotalValorEquivalenteSELIC()

			) );
			
		}
		
		
	}
}

