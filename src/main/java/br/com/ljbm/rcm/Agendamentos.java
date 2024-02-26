package br.com.ljbm.rcm;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.logging.LoggingFeature;

public class Agendamentos {
	
	static List<String> moradores = new ArrayList<String>();

	private static final String HTTP_RCM_host = "https://www.portalrcm.net.br";

	private static final String WS_RESERVA_ATIVIDADE = 
			"_model/marcacaodetalhe.aspx"
			;
			

	static {
		moradores = Arrays.asList("14258", "15716", "15717"); // luciana, luciano, isadora
	}
	
	                                                        
//	private static final DateTimeFormatter FORMATO_DATA_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private Logger log;
	private java.util.logging.Logger log2;
	
	public Agendamentos() { }
	
//	@Inject
	public Agendamentos(Logger log) {
		this.log = log;
	}
	
	public void efetuaReserva() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("logging.properties");
		try {
			java.util.logging.LogManager.getLogManager().readConfiguration(stream);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

//	    Logger log = Logger.getLogger(this.getClass().getName());
	    Client client = ClientBuilder.newBuilder()
	    		.property("com.ibm.ws.jaxrs.client.timeout", "1000")
	        //.register(JacksonJsonProvider.class)
	        .register(new LoggingFeature(log2, java.util.logging.Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 20000))
	        .build();
		    
	    System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	    WebTarget target = client
	    		.target(HTTP_RCM_host)
	    		.path(WS_RESERVA_ATIVIDADE)
	    		
	    		.queryParam("id_cond", "13")
	    		.queryParam("status", "Dispon%C3%ADvel")
	    		.queryParam("id_atividade", 250)
	    		.queryParam("dia", 6) //sábado
	    		.queryParam("inicio", "07%3A00")
	    		.queryParam("termino", "07%3A50")
	    		.queryParam("data", "2021-01-16")
	    		
				.queryParam("id_morador", moradores.get(0))
				.queryParam("_", "1610230290000")
	    		
	    		;
	    
	    
	    Invocation.Builder invocationBuilder = target
	    		.request("*/*");
	    invocationBuilder = invocationBuilder
	    		.header("Host", "www.portalrcm.net.br" )
	    		.header("Connection", "Keep-Alive")
	    		.header("Sec-Fetch-Site", "same-origin")
	    		.header("Sec-Fetch-Mode", "cors")
	    		.header("Sec-Fetch-Dest", "empty")
	    		.header("Referer", "https://www.portalrcm.net.br/base?scr=HAmQnGyasE8fN%2bqxXSAJwxLi0zlh7M4%2f7hjbj%2bfoVxo%3d&st=0h00DWn8tubeVNONImIFqgRlpPaqDWgw&ic=%2fHh2uwzywrk%3d&ia=nMMVJeOi5%2fQ%3d&di=EqlbGT3Qq8I%3d&in=i%2f3zD9tadEFMFmyOuxnnFw%3d%3d&te=DGpTyQvCChSdFgneewyFyg%3d%3d&dA=B3yaOKpSJfmSd7VkHbJeB2yoPPM0zff3&im=a1xsAJYxq5zL5H3GYlDsSQ%3d%3d")
	    		.header("Accept-Language", "pt-PT,pt;q=0.9,en-US;q=0.8,en;q=0.7")
	    		.cookie("ASP.NET_SessionId", "nfeekr52x4yhz3hdqlwtef1q; ARRAffinity=5883741e0e7b1c8b96956bf39ecaf230fcb6e32b3ebf09caece01e4102480f26; ARRAffinitySameSite=5883741e0e7b1c8b96956bf39ecaf230fcb6e32b3ebf09caece01e4102480f26")
//				.header("User-Agent","Apache-HttpClient/4.1.1 (java 1.5)")
	    		
	    		

//				User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36
//				X-Requested-With: XMLHttpRequest
//				Sec-Fetch-Site: same-origin
//				Sec-Fetch-Mode: cors
//				Sec-Fetch-Dest: empty
//				Referer: 
//				Accept-Encoding: gzip, deflate, br
//				Accept-Language: pt-PT,pt;q=0.9,en-US;q=0.8,en;q=0.7
//				Cookie: ASP.NET_SessionId=
		
			    		
				;
	    Response response = null;
		try {
			Invocation x = invocationBuilder.buildGet();
			response = x.invoke(Response.class);
		  
		  if (response.getStatus() != HttpURLConnection.HTTP_OK) {
			  throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		  }
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		log.info("sucesso reserva");

	}
	

	
	public static void main(String[] args) throws Exception {

		Agendamentos x = new Agendamentos(LogManager.getFormatterLogger(Agendamentos.class));
		x.efetuaReserva();
	}
}