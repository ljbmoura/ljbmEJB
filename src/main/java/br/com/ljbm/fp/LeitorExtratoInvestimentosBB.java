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

import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.TipoFundoInvestimento;
import br.com.ljbm.utilitarios.FormatadorBR;

public class LeitorExtratoInvestimentosBB {

	enum EstadoElemento {
		INICIO, NOVA_APLICACAO, LINHAS_APLICACOES, VALOR_COTA_ATUAL, FIM
	};

	EstadoElemento elementoAtual;
	EstadoElemento elementoAnterior;
	BufferedReader leitorArquivo;
	Calendar dataExtrato;
	List<Aplicacao> aplicacoes;
	BigDecimal valorCotaDataExtrato;
	String nomeFundo;
	String cnpjFundo;

	public List<ExtratoInvestimento> analisador(String caminhoArquivoExtrato) {

		List<ExtratoInvestimento> extratoInvestimentos = new ArrayList<ExtratoInvestimento>(
				0);

		elementoAtual = EstadoElemento.INICIO;
		leitorArquivo = null;
		try {
			leitorArquivo = new BufferedReader(new FileReader(
					caminhoArquivoExtrato));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(
					"Arquivo de extrato nao encontrado.");
		}

		try {
			while (elementoAtual != EstadoElemento.FIM) {
				// estadoAtual = analisaBloco(leitorArquivo);
				if (
					(elementoAtual == EstadoElemento.INICIO
						|| elementoAtual == EstadoElemento.NOVA_APLICACAO) 
					// extratoInvestimentos.clear();
					 && novaAplicacao()) {
						elementoAtual = EstadoElemento.VALOR_COTA_ATUAL;
						continue;
					
				}

				if (
					elementoAtual == EstadoElemento.VALOR_COTA_ATUAL 
					&& cotacao()) {
						elementoAtual = EstadoElemento.LINHAS_APLICACOES;

						continue;
					
				}

				if (elementoAtual == EstadoElemento.LINHAS_APLICACOES) {
					this.aplicacoes = new ArrayList<Aplicacao>(0);
					if (aplicacoes()) {
						ExtratoInvestimento investimento = new ExtratoInvestimento(
								dataExtrato, valorCotaDataExtrato, aplicacoes,
								new FundoInvestimento(cnpjFundo, nomeFundo,
										new BigDecimal("0.15"), TipoFundoInvestimento.RendaFixa));
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
			throw new IllegalArgumentException(
					"Arquivo de extrato com formato invalido.");
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Erro no acesso ao arquivo de extrato.");
		} finally {
			try {
				leitorArquivo.close();
			} catch (IOException e) {
				throw new IllegalArgumentException(
						"Erro no acesso ao arquiv de extrato.");
			}
		}

		return extratoInvestimentos;
	}

	private boolean aplicacoes() throws IOException, ParseException {

		String linha;

		Pattern pattern1 = Pattern
				.compile("(\\d{2}\\/\\d{2}\\/\\d{4})\\s+([\\d]+)\\s+([\\d\\.\\,]+)");
		Matcher matcher;

		while ((linha = leitorArquivo.readLine()) != null) {
			if (linha.trim().isEmpty()) {
				continue;
			}
			if ("Data                     Documento                         Valor aplicado"
					.equals(linha)) {
				break;
			}
		}
		linha = leitorArquivo.readLine();
		if (!"-------------------------------------------------------------------------"
				.equals(linha)) {
			return false;
		}

		while ((linha = leitorArquivo.readLine()) != null) {
			matcher = pattern1.matcher(linha);
			if (matcher.find()) {
				Aplicacao aplicacao = new Aplicacao();
				// System.out.println(1 + ": " + matcher.group(1));
				aplicacao
						.setData(FormatadorBR.paraCalendario(matcher.group(1)));
				// System.out.println(2 + ": " + matcher.group(2));
				aplicacao.setDocumento(Long.parseLong(matcher.group(2)));
				// System.out.println(3 + ": " + matcher.group(3));

				aplicacao.setValorAplicado(FormatadorBR.paraBigDecimal(
						matcher.group(3)).setScale(2));
				// System.out.println(linha);
				aplicacoes.add(aplicacao);

				// for (int i = 1; i <= matcher.groupCount(); i++) {
				// System.out.println(i + ": " + matcher.group(i));
				// }
			} else {
				if (!"-------------------------------------------------------------------------"
						.equals(linha)) {
					return false;
				}
				break;
			}
		}
		while ((linha = leitorArquivo.readLine()) != null) {
			if (linha.trim().isEmpty()) {
				continue;
			}
			if ("Quantidade Cotas                             Saldo Cotas"
					.equals(linha.trim())) {
				break;
			}
		}
		linha = leitorArquivo.readLine();
		if (!"-------------------------------------------------------------------------"
				.equals(linha)) {
			return false;
		}
		Pattern pattern2 = Pattern
				.compile("\\s+([\\d\\.\\,]+)\\s+([\\d\\.\\,]+)");
		int contador = 0;
		while ((linha = leitorArquivo.readLine()) != null) {
			if (linha.trim().isEmpty()) {
				continue;
			}
			matcher = pattern2.matcher(linha);
			if (matcher.find()) {
				Aplicacao aplicacao = aplicacoes.get(contador);

				aplicacao.setQuantidadeCotas(FormatadorBR.paraBigDecimal(
						matcher.group(1)).setScale(6));
				aplicacao.setSaldoCotas(FormatadorBR.paraBigDecimal(
						matcher.group(2)).setScale(6));
				// System.out.println(linha);
				contador++;
			} else {
				break;
			}
		}
		if (contador != aplicacoes.size()) {
			return false;
		}
		return true;
	}

	private boolean cotacao() throws IOException, ParseException {
		String linha;
		boolean retorno = false;

		// 24/02/2012 7,462610000 APLICACOES (+) 5.000,00
		Pattern pattern = Pattern
				.compile("(\\d{2}\\/\\d{2}\\/\\d{4})\\s+([\\d\\.\\,]*)\\s+APLICACOES.+");
		Matcher matcher;

		while ((linha = leitorArquivo.readLine()) != null) {
			if (linha.trim().isEmpty()) {
				continue;
			}
			matcher = pattern.matcher(linha);
			if (matcher.find()) {
				// for (int i = 0; i <= matcher.groupCount(); i++) {
				// System.out.println(i + ": " + matcher.group(i));
				// }
				dataExtrato = FormatadorBR.paraCalendario(matcher.group(1));
				valorCotaDataExtrato = FormatadorBR.paraBigDecimal(matcher
						.group(2));
				valorCotaDataExtrato.setScale(9);
				retorno = true;
				break;
			}
		}
		return retorno;
	}

	private boolean novaAplicacao() throws IOException {
		String linha;
		boolean retorno = false;

		Pattern pattern = Pattern.compile("(.+) - CNPJ: (.+)");
		Matcher matcher;

		while ((linha = leitorArquivo.readLine()) != null) {
			if (linha.trim().isEmpty()) {
				continue;
			}
			matcher = pattern.matcher(linha);
			if (matcher.find()) {
				nomeFundo = matcher.group(1).trim();
				// 02.020.528/0001-58
				cnpjFundo = matcher.group(2).trim().replace(".", "")
						.replace("-", "").replace("/", "");
				retorno = true;
				break;
			}
		}
		return retorno;
	}

}
