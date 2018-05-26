package br.com.ljbm.ws.bc;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.Logger;

//import com.fincatto.cotacao.classes.Cotacao;
//import com.fincatto.cotacao.classes.Indice;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

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

	private static final String WS_BC_FATORES_ACUMULADOS = "https://www3.bcb.gov.br/selic/rest/fatoresAcumulados/pub/search";
	private static final DateTimeFormatter FORMATO_DATA_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	//@Inject
	private Logger log;
	
	public Selic() { }
	
	@Inject
	public Selic(Logger log) {
		this.log = log;
	}
	
	public BigDecimal fatorAcumuladoSelic(final LocalDate inicio, final LocalDate termino) {

		Client client = Client.create();
		WebResource webResource = client.resource(WS_BC_FATORES_ACUMULADOS);
		String jsonParams = Json.createObjectBuilder()
				.add("campoPeriodo", "periodo")
				.add("dataInicial", inicio.format(FORMATO_DATA_BR))
				.add("dataFinal", termino.format(FORMATO_DATA_BR))
//				.add("mes", "")
//				.add("ano", "")
				.build().toString();
		
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE) .post(ClientResponse.class, jsonParams);

		if (response.getStatus() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);
		log.debug(output);
		BigDecimal fator;
						
		Pattern pattern = Pattern.compile("fator:\\s([\\d|\\.]+)\\,");
		Matcher matcher = pattern.matcher(output); 
		
		if (matcher.find()) {
			fator = new BigDecimal(matcher.group(1));
		}
		else {
			throw new RuntimeException("Failed: Fator has not been found in the HTTP Response.");
		}
		return fator;
	}
}