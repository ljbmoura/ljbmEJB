package br.com.ljbm.fp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.util.StringUtil;

import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.fp.modelo.Corretora;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.TipoFundoInvestimento;
import br.com.ljbm.utilitarios.FormatadorBR;

public class LeitorExtratoTesouroDireto {

	enum EstadoElemento {
		INICIO, NOVA_APLICACAO, LINHAS_APLICACOES, VALOR_COTA_ATUAL, FIM
	};

	private String caminhoArquivoExtrato;
	EstadoElemento elementoAtual;
	EstadoElemento elementoAnterior;
	BufferedReader leitorArquivo;
	Calendar dataExtrato;
	List<Aplicacao> aplicacoes;
	BigDecimal valorCotaDataExtrato;
	String nomeFundo;
	String cnpjFundo;

	public LeitorExtratoTesouroDireto(String caminhoArquivoExtrato) {
		this.caminhoArquivoExtrato = caminhoArquivoExtrato;
	}

	public List<ExtratoInvestimento> analisador() {

		List<ExtratoInvestimento> extratoInvestimentos = new ArrayList<ExtratoInvestimento>(0);

		elementoAtual = EstadoElemento.INICIO;

		try {
			leitorArquivo = new BufferedReader(new FileReader(caminhoArquivoExtrato));
			while (elementoAtual != EstadoElemento.FIM) {
				// estadoAtual = analisaBloco(leitorArquivo);
				if ((elementoAtual == EstadoElemento.INICIO || elementoAtual == EstadoElemento.NOVA_APLICACAO) && // ) {
				// extratoInvestimentos.clear();
				// if (
						novaAplicacao()) {
					elementoAtual = EstadoElemento.LINHAS_APLICACOES;
					continue;
					// }
				}

				if (elementoAtual == EstadoElemento.LINHAS_APLICACOES) {
					this.aplicacoes = new ArrayList<Aplicacao>(0);
					if (aplicacoes()) {

						ExtratoInvestimento investimento = new ExtratoInvestimento(dataExtrato, valorCotaDataExtrato,
								aplicacoes, new FundoInvestimento(cnpjFundo, nomeFundo, new BigDecimal("0.15"),
										TipoFundoInvestimento.RendaFixa));
						extratoInvestimentos.add(investimento);
						// System.out.println(investimento.toString());
						elementoAtual = EstadoElemento.NOVA_APLICACAO;

						continue;
					}
				}
				elementoAtual = EstadoElemento.FIM;
			}
			leitorArquivo.close();
		} catch (ParseException e) {
			throw new IllegalArgumentException("Arquivo com formato invalido.");
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Arquivo nao encontrado.");
		} catch (IOException e) {
			throw new IllegalArgumentException("Erro na leitura do arquivo.");
		}

		return extratoInvestimentos;
	}

	private boolean aplicacoes() throws IOException, ParseException {

		// if (true) {
		// return true;
		// }
		String linha;

		Pattern pattern1 = Pattern.compile(
				/*
				 * 1847195 Liquidada 01/04/2012 NTNB_Principal_150535 1,60 656,58 1,05 5,25
				 * 1.056,82
				 */
				"\\s*([\\d]+)"

						+ "\\s+(.+)"

						+ "\\s+(\\d{2}\\/\\d{2}\\/\\d{4})"

						+ "\\s+(.+)"

						+ "\\s+([\\d\\.\\,]+)\\s+([\\d\\.\\,]+)\\s+([\\d\\.\\,]+)\\s+([\\d\\.\\,]+)\\s+([\\d\\.\\,]+)");

		Matcher matcher;
		while ((linha = leitorArquivo.readLine()) != null) {
			if (linha.trim().isEmpty()) {
				continue;
			}
			matcher = pattern1.matcher(linha);
			if (matcher.find()) {
				// for (int i = 1; i <= matcher.groupCount(); i++) {
				// System.out.println(i + ": " + matcher.group(i));
				// }

				Aplicacao aplicacao = new Aplicacao();

				aplicacao.setQuantidadeCotas(FormatadorBR.paraBigDecimal(matcher.group(5)).setScale(2));

				aplicacao.setValorAplicado(aplicacao.getQuantidadeCotas()
						.multiply(FormatadorBR.paraBigDecimal(matcher.group(6)).setScale(2)));
				aplicacao.setSaldoCotas(aplicacao.getQuantidadeCotas());
				aplicacao.setData(FormatadorBR.paraCalendario(matcher.group(3)));
				aplicacao.setDocumento(Long.parseLong(matcher.group(1)));
				aplicacoes.add(aplicacao);
				// System.out.println("Linha processada : " + linha);

				// for (int i = 1; i <= matcher.groupCount(); i++) {
				// System.out.println(i + ": " + matcher.group(i));
				// }
			} else {
				break;
			}
		}
		return true;
	}

	private boolean novaAplicacao() throws IOException, ParseException {
		String linha;
		boolean retorno = false;

		Pattern pattern = Pattern.compile("(.+)\\s+(\\d{2}\\/\\d{2}\\/\\d{4})\\s+([\\d\\.\\,]+)");
		Matcher matcher;

		while ((linha = leitorArquivo.readLine()) != null) {
			if (linha.trim().isEmpty()) {
				continue;
			}
			matcher = pattern.matcher(linha);
			if (matcher.find()) {
				// for (int i = 1; i <= matcher.groupCount(); i++) {
				// System.out.println(i + ": " + matcher.group(i));
				// }
				nomeFundo = matcher.group(1).trim();
				cnpjFundo = "CNPJ 0";
				valorCotaDataExtrato = FormatadorBR.paraBigDecimal(matcher.group(3)).setScale(2);
				dataExtrato = FormatadorBR.paraCalendario(matcher.group(2));
				retorno = true;
				break;
			}
		}
		return retorno;
	}

	public interface ParseLinhaExtratoTD {
		
		void preencheAtributos(String linha, Aplicacao apl);
		void setProximo (ParseLinhaExtratoTD proximo);
		ParseLinhaExtratoTD getProximo();
	}

	public class ParseLinhaTitulo implements ParseLinhaExtratoTD {
		
		ParseLinhaExtratoTD proximoParse = null;
		
		/*
		 * Título: Tesouro IPCA+ 2024
		 */
		Pattern padraoLinha = Pattern.compile(
				"Título:\\s*(.+)");

		@Override
		public void preencheAtributos(String linha, Aplicacao apl) {
			Matcher matcher = padraoLinha.matcher(linha);
			if (matcher.find()) {			
				FundoInvestimento fi = new FundoInvestimento();
				fi.setNome(matcher.group(1));
				apl.setFundoInvestimento(fi);
			}
		}

		@Override
		public void setProximo(ParseLinhaExtratoTD proximo) {
			proximoParse = proximo;
		}

		@Override
		public ParseLinhaExtratoTD getProximo() {
			return proximoParse;
		}
	}

	public class ParseLinhaAgenteCustodia implements ParseLinhaExtratoTD {
		/*
		 * Agente de custódia: BB BANCO DE INVESTIMENTO S/A
		 */
		Pattern padraoLinha = Pattern.compile(
				"Agente de custódia:\\s*(.+)");

		ParseLinhaExtratoTD proximoParse = null;

		@Override
		public void setProximo(ParseLinhaExtratoTD proximo) {
			proximoParse = proximo;
		}
		
		@Override
		public void preencheAtributos(String linha, Aplicacao apl) {
			Matcher matcher = padraoLinha.matcher(linha);
			if (matcher.find()) {			
				Corretora c = new Corretora();
				c.setRazaoSocial(matcher.group(1));
				apl.getFundoInvestimento().setCorretora(c);
			}
		}

		@Override
		public ParseLinhaExtratoTD getProximo() {
			return proximoParse;
		}
	}

	public class CriadorAplicacao {
		private Aplicacao apl;

		public CriadorAplicacao() {
			this.apl = new Aplicacao();
		}

		public void analisaLinha(String linha, ParseLinhaExtratoTD analisador) {
			
			analisador.preencheAtributos(linha, apl);
		}

		public Aplicacao getAplicacaoPronta() {
			return apl;
		}
	}

	public void parse() throws IOException {
		this.aplicacoes = new ArrayList<Aplicacao>(0);
		leitorArquivo = new BufferedReader(new FileReader(caminhoArquivoExtrato));
		String linha;
		ParseLinhaExtratoTD plt = new ParseLinhaTitulo();
		ParseLinhaAgenteCustodia plac = new ParseLinhaAgenteCustodia();
		
		plt.setProximo(plac);
		plac.setProximo(null);
		CriadorAplicacao l = new CriadorAplicacao();
		ParseLinhaExtratoTD pl = plt;
		try {
			while ((linha = leitorArquivo.readLine()) != null) {
				l.analisaLinha(linha, pl);
				if (StringUtils.isEmpty(linha.trim())) {
					pl = plt;
				} else {
					pl = pl.getProximo();
				}
				if (pl == null) {
					break;
				}
			}
			this.aplicacoes.add(l.getAplicacaoPronta());
		} finally {
			if (leitorArquivo != null) {
				leitorArquivo.close();
			}
		}
	}

	public List<Aplicacao> getAplicacoes() {
		return aplicacoes;
	}
}
