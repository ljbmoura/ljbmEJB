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

import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.fp.modelo.ComparacaoInvestimentoVersusSELIC;
import br.com.ljbm.fp.modelo.PosicaoTituloPorAgente;
import br.com.ljbm.fp.modelo.TipoFundoInvestimento;
import br.com.ljbm.utilitarios.FormatadorBR;
import br.com.ljbm.ws.bc.Selic;

@Stateless
@Remote(AvaliadorInvestimento.class)
public class AvaliadorInvestimentoImpl implements AvaliadorInvestimento {

//	private static final String EXTRATO_INVESTIMENTO_BB_ATUAL = resourcesDir
//			.getPath() + File.separator + "extratoInvestimentosAtual.txt";


	private static final MathContext MC_BR = new MathContext(17, RoundingMode.DOWN);
	
	@Inject	
	private Logger log;
	
	private CotacaoTituloDAO daoCotacoes;
	
	@Inject
	private Selic selicWS;
	
	@EJB
	FPDominio model;

	public AvaliadorInvestimentoImpl() {
	}
	
	public AvaliadorInvestimentoImpl(
			Selic selicWS, CotacaoTituloDAO daoCotacoes,
			Logger log, FPDominio model) {
		this.selicWS = selicWS;
		this.daoCotacoes = daoCotacoes;
		this.log = log;
		this.model = model;
	}

	@Override
	public List<ComparacaoInvestimentoVersusSELIC> comparaInvestimentosComSELIC(
			List<PosicaoTituloPorAgente> extrato, String dataPosicao) {

		ArrayList<ComparacaoInvestimentoVersusSELIC> ret = new ArrayList<ComparacaoInvestimentoVersusSELIC>(0);
		for (PosicaoTituloPorAgente posicao : extrato) {
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
			String dataPosicao, PosicaoTituloPorAgente posicao)  {

		LocalDate dataAlvo = LocalDate.from(FormatadorBR.paraLocalDate(dataPosicao));

		BigDecimal totalAplicado = BigDecimal.ZERO;
		BigDecimal totalAtualBruto = BigDecimal.ZERO;
		BigDecimal totalEquivalenteSELIC = BigDecimal.ZERO;
		BigDecimal totalDiferencaSELIC = BigDecimal.ZERO;
		
		int cont = 0;
		for (Aplicacao compra : posicao.getCompras()) {
			
			BigDecimal fatorRemuneracaoAcumuladaSELIC = model.getCoeficienteSELIC(compra.getDataCompra(), dataAlvo);
			if (fatorRemuneracaoAcumuladaSELIC == null) {				
				cont++;
				fatorRemuneracaoAcumuladaSELIC = selicWS.fatorAcumuladoSelic(compra.getDataCompra(), dataAlvo);
				model.addCoeficienteSELIC(compra.getDataCompra(), dataAlvo, fatorRemuneracaoAcumuladaSELIC);
				if((cont % 10) == 0) {
					try {
						Thread.sleep(6000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			BigDecimal vEquivalenteSELIC = compra.getValorAplicado().multiply(fatorRemuneracaoAcumuladaSELIC, MC_BR);
			BigDecimal vAtualBruto = compra.getSaldoCotas().multiply(
					daoCotacoes.paraTituloEm(posicao.getTitulo(), dataAlvo), MC_BR);

			System.out.println( 
				String.format(
						"Comparando %22s, compra %s: Atual %,10.2f Selic %,10.2f, diferença %,10.2f, fator %12.9f" 
						, compra.getFundoInvestimento().getNome() 
						, compra.getDataCompra().toString()
						, vAtualBruto
						, vEquivalenteSELIC
						, vAtualBruto.subtract(vEquivalenteSELIC)
						, fatorRemuneracaoAcumuladaSELIC
					)
				);
			
			totalAplicado = totalAplicado.add(compra.getValorAplicadoRemanescente());
			totalAtualBruto = totalAtualBruto.add(vAtualBruto);
			totalEquivalenteSELIC = totalEquivalenteSELIC.add(vEquivalenteSELIC);
			totalDiferencaSELIC = totalDiferencaSELIC.add(vAtualBruto.subtract(vEquivalenteSELIC));

		}
		BigDecimal rentabilidadeFundo = totalAtualBruto
				.subtract(totalAplicado)
				.divide(totalAplicado, 4, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.00"))
				;
		BigDecimal rentabilidadeEquivSELIC = totalEquivalenteSELIC
				.subtract(totalAplicado)
				.divide(totalAplicado, 4, RoundingMode.DOWN)
				.multiply(new BigDecimal("100.00"))
				;

		return new ComparacaoInvestimentoVersusSELIC(
				posicao.getAgenteCustodia(), 
				posicao.getTitulo(), 
				posicao.getTipoFundoInvestimento(),
				rentabilidadeFundo, rentabilidadeEquivSELIC,
				totalDiferencaSELIC, totalAtualBruto, totalEquivalenteSELIC);

	}

	public void imprimeComparacaoInvestComSELIC(List<ComparacaoInvestimentoVersusSELIC> comparativo) {
		System.out.println( 
				String.format("%-10s %-24s %7s %7s %15s %15s %15s"
					,"Corretora"	
					,"Título/Fundo"
					,"%Rentab"
					,"%RSelic"
					,"Diferença"
					,"Valor Fundo"
					,"Valor Eq Selic"
				));
		BigDecimal totalFundos = BigDecimal.ZERO;
		BigDecimal totalEqSelic = BigDecimal.ZERO;
		BigDecimal totalDiferenca = BigDecimal.ZERO;
		
		BigDecimal subtotalFundos = BigDecimal.ZERO;
		BigDecimal subtotalEqSelic = BigDecimal.ZERO;
		BigDecimal subtotalDiferenca = BigDecimal.ZERO;
		TipoFundoInvestimento tfi = comparativo.get(0).getTipoFundoInvestimento();
		BigDecimal zero6Casas = new BigDecimal("0.000000");
		for (ComparacaoInvestimentoVersusSELIC r : comparativo) {
			if (zero6Casas.equals(r.getTotalValorFundo())) {
				continue;
			}
					
			// subtotal por TipoFundoInvestimento
			if (!r.getTipoFundoInvestimento().equals(tfi)) {
				System.out.println(String.format( 
						"%35s %7s %7s %,15.2f %,15.2f %,15.2f"
						, ""
						, ""
						, ""
						, subtotalDiferenca
						, subtotalFundos
						, subtotalEqSelic
				) );
				tfi = r.getTipoFundoInvestimento();
				subtotalFundos = BigDecimal.ZERO;
				subtotalEqSelic = BigDecimal.ZERO;
				subtotalDiferenca = BigDecimal.ZERO;
			}
			
			System.out.println(
				String.format( 

					"%10s %-24s %7.2f %7.2f %,15.2f %,15.2f %,15.2f"
					, r.getSiglaAgente()
					
					,r.getNomeInvestimento()

					,r.getTaxaRentabilidadeFundo()

					,r.getTaxaRentabilidadeSELICEquivalenteFundo()

					,r.getDiferencaRentabilidadeFundoSELIC()

					,r.getTotalValorFundo()

					,r.getTotalValorEquivalenteSELIC()

			) );
			subtotalFundos=subtotalFundos.add(r.getTotalValorFundo());
			subtotalEqSelic=subtotalEqSelic.add(r.getTotalValorEquivalenteSELIC());
			subtotalDiferenca=subtotalDiferenca.add(r.getDiferencaRentabilidadeFundoSELIC());

			totalDiferenca = totalDiferenca.add(r.getDiferencaRentabilidadeFundoSELIC());
			totalFundos = totalFundos.add(r.getTotalValorFundo());
			totalEqSelic = totalEqSelic.add(r.getTotalValorEquivalenteSELIC());
			
		}
		System.out.println(String.format( 
				"%35s %7s %7s %,15.2f %,15.2f %,15.2f"
				, ""
				, ""
				, ""
				, subtotalDiferenca
				, subtotalFundos
				, subtotalEqSelic
		) );		
		System.out.println(
				String.format( 

					"%35s %7s %7s %,15.2f %,15.2f %,15.2f"
					, ""
					, ""
					, ""
					, totalDiferenca
					, totalFundos
					, totalEqSelic
			) );
	}
}

