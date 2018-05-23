package br.com.ljbm.fp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.utilitarios.FormatadorBR;

public class LeitorExtratoTesouroDireto {
	@Inject
	Logger log = LogManager.getFormatterLogger(LeitorExtratoTesouroDireto.class);

	private String caminhoArquivoExtrato;
	private List<PosicaoTituloPorAgente> extratoTD;
	
	public LeitorExtratoTesouroDireto(String caminhoArquivoExtrato) {
		this.caminhoArquivoExtrato = caminhoArquivoExtrato;
	}
	
	public void le() throws IOException {
		
		BufferedReader leitorArquivo = new BufferedReader(new FileReader(caminhoArquivoExtrato));
		String linha;

		this.extratoTD = new ArrayList<PosicaoTituloPorAgente>(0);
		
		ParserLinhaExtratoTD parserTitulo = new ParserLinhaTitulo();
		ParserLinhaExtratoTD parserAgenteCustodia = new ParserLinhaAgenteCustodia();
		ParserLinhaExtratoTD parserPosicaoEm = new ParserLinhaADescartar();
		ParserLinhaExtratoTD parserVencimentoTitulo = new ParserLinhaADescartar();
		ParserLinhaExtratoTD parserCompra = new ParserLinhaCompra();
		
		parserTitulo.setProximo(parserAgenteCustodia);
		parserAgenteCustodia.setProximo(parserPosicaoEm);
		parserPosicaoEm.setProximo(parserVencimentoTitulo);
		parserVencimentoTitulo.setProximo(parserCompra);
		parserCompra.setProximo(parserCompra); // o próprio
		
		PosicaoTituloPorAgenteBuilder builder = new PosicaoTituloPorAgenteBuilder();
		ParserLinhaExtratoTD parserLinhaAtual = parserTitulo;
		try {
			while ((linha = leitorArquivo.readLine()) != null) {
				if (StringUtils.isEmpty(linha.trim())) {
					this.extratoTD.add(builder.getPosicaoTituloPorAgente());
					parserLinhaAtual = parserTitulo;
					builder = new PosicaoTituloPorAgenteBuilder();
					continue;
				}
				builder.obtemValores(linha, parserLinhaAtual);
				parserLinhaAtual = parserLinhaAtual.getProximo();
				
				if (parserLinhaAtual == null) {
					break;
				}
			}
			this.extratoTD.add(builder.getPosicaoTituloPorAgente());
		} finally {
			if (leitorArquivo != null) {
				leitorArquivo.close();
			}
		}
	}
	
	public List<PosicaoTituloPorAgente> extratoLido() {
		return extratoTD;
	}
	
	protected interface ParserLinhaExtratoTD {

		void preencheAtributos(String linha, PosicaoTituloPorAgente apl);

		void setProximo(ParserLinhaExtratoTD proximo);

		ParserLinhaExtratoTD getProximo();
	}

	protected abstract class ParserLinhaExtratoTDComum {
		ParserLinhaExtratoTD proximoParse = null;

		public void setProximo(ParserLinhaExtratoTD proximo) {
			proximoParse = proximo;
		}

		public ParserLinhaExtratoTD getProximo() {
			return proximoParse;
		}
	}

	protected class ParserLinhaTitulo extends ParserLinhaExtratoTDComum implements ParserLinhaExtratoTD {
		/*
		 * Título: Tesouro IPCA+ 2024
		 */
		Pattern padraoLinha = Pattern.compile("Título:\\s*(.+)");

		@Override
		public void preencheAtributos(String linha, PosicaoTituloPorAgente extrato) {
			Matcher matcher = padraoLinha.matcher(linha);
			if (matcher.find()) {
				extrato.setTitulo(matcher.group(1));
			} else {
				throw new IllegalStateException("Título não encotrado no extrado");
			}
		}
	}

	protected class ParserLinhaAgenteCustodia extends ParserLinhaExtratoTDComum implements ParserLinhaExtratoTD {
		/*
		 * Agente de custódia: BB BANCO DE INVESTIMENTO S/A
		 */
		Pattern padraoLinha = Pattern.compile("Agente de custódia:\\s*(.+)");

		@Override
		public void preencheAtributos(String linha, PosicaoTituloPorAgente extrato) {
			Matcher matcher = padraoLinha.matcher(linha);
			if (matcher.find()) {
				extrato.setAgenteCustodia(matcher.group(1));
			} else {
				throw new IllegalStateException("Agente de custódia não encotrado no extrado");
			}
		}
	}

	protected class ParserLinhaADescartar extends ParserLinhaExtratoTDComum implements ParserLinhaExtratoTD {

		public void preencheAtributos(String linha, PosicaoTituloPorAgente apl) {
			// nada faz, apenas ignora a linha
		}
	}

	protected class ParserLinhaCompra extends ParserLinhaExtratoTDComum implements ParserLinhaExtratoTD {
		/*
		 * 9/04/2007 5,20 555,95 2.890,94 IPCA + 6,47% IPCA + 7,44% 310,95 11.880,44 4.043 15,00 1.348,42 0,00 204,25 335,34 9.992,43
		 */
		Pattern padraoLinha = Pattern.compile(
				"\\s*(\\d{2}\\/\\d{2}\\/\\d{4})" // data da aplicação
			  + "\\s+([\\d\\.\\,]+)"			 // quantidade de títulos
			  + "\\s+[\\d\\.\\,]+"				 // preço do título na aplicação
			  + "\\s+([\\d\\.\\,]+)"			 // valor investido
			  + "\\s+\\.*"						 // demais campos
		);

		@Override
		public void preencheAtributos(String linha, PosicaoTituloPorAgente extrato) {
			Matcher matcher = padraoLinha.matcher(linha);
			if (matcher.find()) {
				Aplicacao apl = new Aplicacao();
				try {
					apl.setData(FormatadorBR.paraCalendario(matcher.group(1)));
					apl.setQuantidadeCotas(FormatadorBR.paraBigDecimal(matcher.group(2)).setScale(2));
					apl.setValorAplicado(FormatadorBR.paraBigDecimal(matcher.group(3)).setScale(2));
					apl.setSaldoCotas(apl.getQuantidadeCotas());
					log.debug("Aplicação(Compra) Lida: " + apl.toString());
					extrato.getCompras().add(apl);
				} catch (ParseException e) {
					throw new RuntimeException(e.getMessage());
				}
			} else {
				throw new IllegalStateException("Aplicação(Compra) mal formatada no extrado");
			}
		}
	}

	public class PosicaoTituloPorAgenteBuilder {
		private PosicaoTituloPorAgente posicao;

		public PosicaoTituloPorAgenteBuilder() {
			this.posicao = new PosicaoTituloPorAgente();
			this.posicao.setCompras(new ArrayList<Aplicacao>(0));
		}

		public void obtemValores(String linha, ParserLinhaExtratoTD parserLinha) {

			parserLinha.preencheAtributos(linha, posicao);
		}

		public PosicaoTituloPorAgente getPosicaoTituloPorAgente() {
			return posicao;
		}
	}

}
