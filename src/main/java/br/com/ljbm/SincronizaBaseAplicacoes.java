package br.com.ljbm;

import static br.com.ljbm.fp.modelo.Corretora.cnpjAgora;
import static br.com.ljbm.fp.modelo.Corretora.cnpjBB;
import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.jayway.restassured.response.Response;

import br.com.ljbm.fp.LeitorExtratoTesouroDireto;
import br.com.ljbm.fp.modelo.Corretora;
import br.com.ljbm.fp.modelo.FundoInvestimento;
import br.com.ljbm.fp.modelo.TipoFundoInvestimento;


public class SincronizaBaseAplicacoes {


	private static final BigDecimal TAXA_IMPOSTO_RENDA_POS_2ANOS = new BigDecimal("0.15");


	private static Response enviaPost(FundoInvestimento fundoInvestimento) {
		Response retorno = given().header("Accept", "application/json").contentType("application/json")
				.body(fundoInvestimento).when().post("http://pc:9080/ljbmWeb/rest/fundosInvestimento").andReturn();
		return retorno;
	}

	public static void main(String[] args) {

//		basePath = "http://localhost:9080/ljbmWeb";
		baseURI = "http://localhost:9080";
		basePath = "/ljbmWeb/rest";		

//		cadastroFundosInvestimento();

		sincronizaCestaTD();
		
	}

	private static void sincronizaCestaTD() {
		LeitorExtratoTesouroDireto leitor = new LeitorExtratoTesouroDireto("CestaComprasTD.txt");
		
	}

	

}
