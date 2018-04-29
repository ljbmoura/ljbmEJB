package br.com.ljbm.fp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.ljbm.utilitarios.FormatadorBR;

public class LeitorSerieHistorica {

	// private final String linhaTitulo1 =
	// "Lista de indices Diarios da TaxaSelic;20060102;20140815;";
	public static final String LinhaTitulo2 =
	"Data;Taxa (%a.a.);Fator diário;Base de cálculo (R$);Média;Mediana;Moda;Desvio padrão;Índice de curtose;";
	public static final String ErroLinhaInvalida = "Arquivo não está no formato da Consulta à Taxa Selic Diária "
			+ "fornecido pelo BC: linha invalida";
	public static final String ErroTituloNaoEncontrado = "Arquivo não está no formato da Consulta à Taxa Selic Diária "
			+ "fornecido pelo BC: título não encontrado.";
	private Logger log = LogManager.getFormatterLogger(LeitorSerieHistorica.class.getName());
	
	public SerieHistorica<BigDecimal> leCotacoesSELICBancoCentral(
			String caminhoArquivoCotacaoSELICBC) {

		SerieHistorica<BigDecimal> serie = null;
		BufferedReader leitorArquivo;
		try {
			leitorArquivo = new BufferedReader(new FileReader(
					caminhoArquivoCotacaoSELICBC));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(
					"Arquivo com as cotaçõs SELIC não encontrado.");
		}
		try {

			String linha;
			linha = obtemProximaLinha(leitorArquivo);
			if (linha == null) {
				throw new IllegalArgumentException(ErroTituloNaoEncontrado);
			}
			// Lista de indices Diarios da TaxaSelic;20060102;20120816;
			Pattern pattern0 = Pattern.compile("(.+);(\\d{8});(\\d{8});");
			Matcher matcherLinhaFiltro;
			matcherLinhaFiltro = pattern0.matcher(linha);
			if (matcherLinhaFiltro.find()) {
				//System.out.println(linha); // Print the data line.
				serie = new SerieHistorica<BigDecimal>(
						FormatadorBR.paraCalendarioyyyyMMdd(matcherLinhaFiltro
								.group(2)),
						FormatadorBR.paraCalendarioyyyyMMdd(matcherLinhaFiltro
								.group(3)));
			} else {
				throw new IllegalArgumentException(ErroLinhaInvalida
						+ "linha: " + linha);
			}
			// busca a linha 2, título das colunas
			linha = obtemProximaLinha(leitorArquivo);
			if (linha == null) {
				throw new IllegalArgumentException(ErroTituloNaoEncontrado);
			}
			if (!LinhaTitulo2.equals(linha)) {
				throw new IllegalArgumentException(ErroTituloNaoEncontrado);
			}

			// trata as linhas de cotação
			// 02/01/2006; 17,98; 1,00065635; 1,0006563500000000;
			// 02/01/2006;17,98;1,00065635;0,00;0,00;0,00;0,00;0,00;0,00;
			Pattern pattern1 = Pattern
					.compile("(\\d{2}\\/\\d{2}\\/\\d{4});\\s*([\\d\\,]+);\\s*([\\d\\,]+);(.+);");
			Matcher matcher;
			BigDecimal fatorAcumuladoAnterior = BigDecimal.ONE.setScale(16);

			while ((linha = leitorArquivo.readLine()) != null) {
				//System.out.println(linha); // Print the data line.
				matcher = pattern1.matcher(linha);
				if (matcher.find()) {
					// for (int i = 1; i <= matcher.groupCount(); i++) {
					// System.out.println(i + ": " + matcher.group(i));
					// }
					try {
						BigDecimal fatorDiario = FormatadorBR
								.paraBigDecimal(matcher.group(3)).setScale(8);
						BigDecimal fatorAcumulado = fatorDiario.multiply(fatorAcumuladoAnterior).setScale(16, RoundingMode.HALF_EVEN);
						serie.addElemento(
								FormatadorBR.paraCalendario(matcher.group(1)),
								fatorAcumulado);
						log.debug(String.format("fator diario %s, fator acumulado %s", fatorDiario.toString(), fatorAcumulado.toPlainString()));
						
						fatorAcumuladoAnterior = fatorAcumulado;
					} catch (ParseException e) {
						throw new IllegalArgumentException(ErroLinhaInvalida, e);
					}
				} else {
					throw new IllegalArgumentException(ErroLinhaInvalida);
				}
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Erro no acesso ao arquivo com as cotações SELIC.");
		} catch (ParseException e) {
			throw new IllegalArgumentException(
					"Erro na leitura do arquivo com as cotações SELIC.");
		} finally {
			try {
				leitorArquivo.close();
			} catch (IOException e) {
				throw new IllegalArgumentException(
						"Erro no acesso ao arquivo com as cotações SELIC.");
			}
		}

		return serie;
	}

	private String obtemProximaLinha(BufferedReader leitorArquivo)
			throws IOException {
		String linha = null;
		do {
			linha = leitorArquivo.readLine();
			if (linha == null)
				return null;
		} while (linha.trim().isEmpty());
		return linha;
	}
}
