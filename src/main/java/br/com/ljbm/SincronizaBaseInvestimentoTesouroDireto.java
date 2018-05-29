package br.com.ljbm;

import static br.com.ljbm.fp.modelo.Corretora.cnpjBB;
import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import br.com.ljbm.fp.LeitorExtratoTesouroDireto;
import br.com.ljbm.fp.modelo.Corretora;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.PosicaoTituloPorAgente;
import br.com.ljbm.fp.modelo.TipoFundoInvestimento;
import br.com.ljbm.utilitarios.Recurso;

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
		
		List<PosicaoTituloPorAgente> extrato = 
				new LeitorExtratoTesouroDireto()
					.caminhoArquivoExtratoTD(
							Recurso.getPastaRecursos(SincronizaBaseInvestimentoTesouroDireto.class)
							+ File.separator +
							"ExtratoTDCompleto.txt")
					.le();
		
		extrato.stream().forEach(posicao -> {
			
			FundoInvestimento fundo = buscaFundoInvestimento(
					posicao.getAgenteCustodia()
					, posicao.getTitulo());
			
			log.info(fundo);
//			if (fundo == null) {
//				Response retorno = criaFundoInvestimento(
//						posicao.getAgenteCustodia()
//						, posicao.getTitulo());
//			}
//			 
			
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	private static Response criaFundoInvestimento(String agente, String titulo) {
		
		FundoInvestimento novoFundo = new FundoInvestimento(
				cnpjBB, 
				titulo, 
				TAXA_IMPOSTO_RENDA_POS_2ANOS,
				TipoFundoInvestimento.TesouroDireto, 
				bB);
		
		Response response = 
			given()
				.contentType(APPLICATION_JSON)
				.body(novoFundo)
			.when()
				.post("/fundosInvestimento")
			.andReturn();
		log.info("Response Headers\n" + response.headers().toString());
		response.prettyPrint();

		Assert.assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());
		String header = response.getHeader("Location");
		Long ideFundo = Long.parseLong(
			header.substring(header.lastIndexOf("/")+1,header.length()));
		log.info("fundo criado: " + ideFundo);
		return response;
	}

	private static FundoInvestimento buscaFundoInvestimento(String agente, String titulo) {
		
		
		String location = FUNDOS_INVESTIMENTO_ENDPOINT;
		log.info("enviando get para " + location);
		JsonPath retorno = 
			given()
				.header("Accept", APPLICATION_JSON)
				.queryParam("agente", agente)
				.queryParam("titulo", titulo)
			.when()
				.get(location)
			.andReturn()
				.jsonPath();
		FundoInvestimento resourceLido = retorno.getObject("FundoInvestimento", FundoInvestimento.class);
		return resourceLido;
	}	
}
