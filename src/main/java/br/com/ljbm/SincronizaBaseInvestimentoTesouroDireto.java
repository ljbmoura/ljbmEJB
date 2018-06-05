package br.com.ljbm;

import static br.com.ljbm.fp.modelo.Corretora.cnpjAgora;
import static br.com.ljbm.fp.modelo.Corretora.cnpjBB;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.ljbm.fp.LeitorExtratoTesouroDireto;
import br.com.ljbm.fp.modelo.Aplicacao;
import br.com.ljbm.fp.modelo.Corretora;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.PosicaoTituloPorAgente;
import br.com.ljbm.fp.modelo.TipoFundoInvestimento;
import br.com.ljbm.utilitarios.Recurso;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SincronizaBaseInvestimentoTesouroDireto {
	private static final String FUNDOS_INVESTIMENTO_ENDPOINT = "/fundosInvestimento";

	private static final BigDecimal TAXA_IMPOSTO_RENDA_POS_2ANOS = new BigDecimal("0.15");
	private static Logger log;
	private static Corretora bB;
	private static Corretora agora;

	public static void main(String[] args) throws IOException {

		log = LogManager.getFormatterLogger(SincronizaBaseInvestimentoTesouroDireto.class);
		baseURI = "http://localhost:9080";
		basePath = "/ljbmWeb/rest";
		
		bB = new Corretora();
		bB.setIde(1l);
		agora = new Corretora();
		agora.setIde(2l);		
		
		List<PosicaoTituloPorAgente> extrato = 
				new LeitorExtratoTesouroDireto()
					.caminhoArquivoExtratoTD(
							Recurso.getPastaRecursos(SincronizaBaseInvestimentoTesouroDireto.class)
							+ File.separator +
							"ExtratoTDAmostra.txt")
					.le();
		
		extrato.stream().forEach(posicao -> {
			
			FundoInvestimento ref = new FundoInvestimento();

			FundoInvestimento fundo = buscaFundoInvestimento(
					posicao.getAgenteCustodia()
					, posicao.getTitulo());

			if (fundo != null) {
				ref.setIde(fundo.getIde());
			} else {
				Long ideFundoNovo = criaFundoInvestimento(
						posicao.getAgenteCustodia()
						, posicao.getTitulo());
				ref.setIde(ideFundoNovo);
			}
			
			posicao.getCompras().forEach(compra -> {
				compra.setFundoInvestimento(ref);
				log.info(compra);
				criaAplicacao(compra);
				
			});
		});
		
		
		
		
		
		
		
	}
	
	private static Long criaFundoInvestimento(String agente, String titulo) {
		String cnpj = "";
		Corretora corretora = null;
		
		if ("AGORA CTVM S/A".equalsIgnoreCase(agente)) {
			cnpj = cnpjAgora;
			corretora = agora;
		}
		else if ("BB BANCO DE INVESTIMENTO S/A".equalsIgnoreCase(agente)) {
			cnpj = cnpjBB;
			corretora = bB;
		}
		FundoInvestimento novoFundo = new FundoInvestimento(
				cnpj, 
				titulo, 
				TAXA_IMPOSTO_RENDA_POS_2ANOS,
				TipoFundoInvestimento.TesouroDireto, 
				corretora);
		
		Response response = 
			given()
				.contentType(APPLICATION_JSON)
				.body(novoFundo)
			.when()
				.post("/fundosInvestimento")
			.andReturn();
		
		if (log.isDebugEnabled()) {
			log.debug("Response Headers\n" + response.headers().toString());
			response.prettyPrint();
		}
		Long ideFundo = null;
		if (response.getStatusCode() == HttpStatus.SC_CREATED) {
			String header = response.getHeader("Location");
			ideFundo = Long.parseLong(
				header.substring(header.lastIndexOf("/")+1,header.length()));
			log.info(String.format("fundo criado: %s, %s", ideFundo, novoFundo.toString()));
			return ideFundo;
		} else {
			String msg = "Fundo não pode ser criado: " + response.getStatusLine();
			log.error(msg);
			throw new RuntimeException(msg);
		}
	}
	
	private static Long criaAplicacao(Aplicacao aplicacaoNova) {
		
		int x = 1;
		Response response = 
				given()
					.contentType(APPLICATION_JSON)
					.body(aplicacaoNova)
				.when()
						.post(FUNDOS_INVESTIMENTO_ENDPOINT + "/" + aplicacaoNova.getFundoInvestimento().getIde()
								+ "/aplicacoes")
						.andReturn();
			
			if (log.isDebugEnabled()) {
				log.debug("Response Headers\n" + response.headers().toString());
				response.prettyPrint();
			}
			Long ideFundo = null;
			if (response.getStatusCode() == HttpStatus.SC_CREATED) {
				String header = response.getHeader("Location");
				ideFundo = Long.parseLong(
					header.substring(header.lastIndexOf("/")+1,header.length()));
				log.info(String.format("aplicação criada: %s, %s", ideFundo, aplicacaoNova.toString()));
				return ideFundo;
			} else {
				String msg = "Aplicação não pode ser criada: " + response.getStatusLine();
				log.error(msg);
				throw new RuntimeException(msg);
			}
	}

	private static FundoInvestimento buscaFundoInvestimento(String agente, String titulo) {
		
		
		String location = FUNDOS_INVESTIMENTO_ENDPOINT;
		log.info(String.format("get %s ? '%s' e '%s'", location, agente, titulo));
		Response response = 
			given()
				.header("Accept", APPLICATION_JSON)
				.queryParam("agente", agente)
				.queryParam("titulo", titulo)
			.when()
				.get(location)
			.andReturn();

		if (log.isDebugEnabled()) {
			log.debug("Response Headers\n" + response.headers().toString());
			log.debug("Response Body");
			response.getBody().prettyPrint();
		}
		
		if (response.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
			log.info("Fundo não Encontrado");
			return null;
		} 

		JsonPath retorno = response.body().jsonPath();
		FundoInvestimento fundo = retorno.getList("ArrayList", FundoInvestimento.class).get(0);
		log.info("Encontrado: " + fundo);
		return fundo;
	}	
}
