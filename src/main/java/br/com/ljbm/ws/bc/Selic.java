package br.com.ljbm.ws.bc;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.logging.LoggingFeature;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
//import com.sun.jersey.api.client.WebResource;

/**
 * Client RestFul para recuperação do fator acumulado da Taxa Selic.
 * A Interface web correspondente está disponível em: http://www.bcb.gov.br/htms/selic/selicacumul.asp
 * 
 * A URL completa do serviço é: 
 * 	https://www3.bcb.gov.br/selic/rest/fatoresAcumulados/pub/search
 * 	?parametrosOrdenacao=[{"nome":"periodo","decrescente":false}]&page=1&pageSize=20
 *
 *	exemplo body (json):{campoPeriodo: "periodo", dataInicial: "19/04/2007", dataFinal: "06/04/2018"}
 *
 */
public class Selic {

//private static final String HTTP_WWW3_BCB_GOV_BR_host = "http://localhost:8080";
private static final String HTTP_WWW3_BCB_GOV_BR_host = "https://www3.bcb.gov.br";

	//	private static final String WS_BC_FATORES_ACUMULADOS = "/selic/rest/fatoresAcumulados/pub/search";
	private static final String WS_BC_FATORES_ACUMULADOS = "/novoselic/rest/fatoresAcumulados/pub/search";//?parametrosOrdenacao=[{\"nome\":\"periodo\",\"decrescente\":false}]&page=1&pageSize=20";
	                                                        
	private static final DateTimeFormatter FORMATO_DATA_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	//@Inject
	private Logger log;
	private java.util.logging.Logger log2;
	
	public Selic() { }
	
	@Inject
	public Selic(Logger log) {
		this.log = log;
	}
	
	public BigDecimal fatorAcumuladoSelic(final LocalDate inicio, final LocalDate termino) {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("logging.properties");
		try {
			java.util.logging.LogManager.getLogManager().readConfiguration(stream);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

//	    Logger log = Logger.getLogger(this.getClass().getName());
	    Client client = ClientBuilder.newBuilder()
	    		.property("com.ibm.ws.jaxrs.client.timeout", "1000")
	        .register(JacksonJsonProvider.class)
	        .register(new LoggingFeature(log2, java.util.logging.Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 20000))
	        .build();
		    
//		Client client = Client.create();
		String jsonParams = Json.createObjectBuilder()
				.add("campoPeriodo", "periodo")
				.add("dataInicial", inicio.format(FORMATO_DATA_BR))
				.add("dataFinal", termino.format(FORMATO_DATA_BR))
//				.add("mes", "")
//				.add("ano", "")
				.build().toString();
		 System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	    WebTarget target = client.target(HTTP_WWW3_BCB_GOV_BR_host).path(WS_BC_FATORES_ACUMULADOS);
	    Invocation.Builder invocationBuilder = target
	    		.request(MediaType.APPLICATION_JSON,"text/plain","*/*")
	    		;
	    invocationBuilder = invocationBuilder
//	    		.header("Content-Type", "application/json")
//				.header("Content-Type",MediaType.APPLICATION_JSON_TYPE)
//				.header("Accept", "application/json, text/plain, */*")
				.header("Connection", "Keep-Alive")
				.header("Host", "www3.bcb.gov.br" )
				.header("Content-Length", "78")
//				.header("User-Agent","Apache-HttpClient/4.1.1 (java 1.5)")
				;
//				.post(ClientResponse.class, jsonParams);
	    Response response = null;
		try {
			Invocation x = invocationBuilder.buildPost(Entity.entity(jsonParams, MediaType.APPLICATION_JSON));
			response = x.invoke(Response.class);
		  
		  if (response.getStatus() != HttpURLConnection.HTTP_OK) {
			  throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		  }
		} catch (Exception e) {
//			if (response.getStatus() != HttpURLConnection.HTTP_OK) {
//				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//			}
			e.printStackTrace();
			System.exit(0);
//			throw new RuntimeException(e);
		}
		String output = response.readEntity(String.class);

		log.debug(output);
		BigDecimal fator;
		
		//{"totalItems":1,"registros":[{"periodo":"19/04/2007 a 21/06/2018","fator":3.11506402336271,"fatorFormatado":"3,11506402336271"}],"observacoes":[],"dataAtual":"06/01/2019 às 18:50:51"}						
		Pattern pattern = Pattern.compile("\"fator\":([\\d|\\.]+)\\,");
		Matcher matcher = pattern.matcher(output); 
		
		if (matcher.find()) {
			fator = new BigDecimal(matcher.group(1));
			return fator;
		}
		else {
			pattern = Pattern.compile("\"fatorFormatado\":\"([\\d|\\,]+)\"}");
			matcher = pattern.matcher(output); 
			if (matcher.find()) {
				fator = new BigDecimal(matcher.group(1).replaceAll(",",	"."));
				return fator;
			}
		}
		throw new RuntimeException("Failed: Fator has not been found in the HTTP Response.");
	}
}