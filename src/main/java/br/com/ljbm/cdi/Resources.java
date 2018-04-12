package br.com.ljbm.cdi;

import java.time.Clock;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationScoped
public class Resources {
	
    @Produces 
    Logger produceLog(InjectionPoint injectionPoint) {
        return LogManager.getFormatterLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
    
//    @Produces @ApplicationScoped 
//    Client jaxRSClient = ClientBuilder.newClient();
    
    @Produces @RequestScoped
    Clock clockDataHoje(){
    	return Clock.systemDefaultZone();
    };
    

    @PersistenceContext(unitName = "ljbmUP")
    private EntityManager entityManager; 
//    
//    @Produces @ApplicationScoped
//    EntityManager entityManager() {
//    	return entityManager;
//    }
//    
}
