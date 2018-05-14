package br.com.ljbm.fp.interceptador;

import javax.inject.Inject;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.Logger;

//@Interceptor
public class LogDesempenho {

	@Inject
	Logger log;

//	 @AroundInvoke
	public Object logaTempoExecucao(InvocationContext contexto) throws Exception {
		long inicio = System.currentTimeMillis();
		Object retorno = contexto.proceed();
		log.debug(String.format("%s.%s: %sms"
				, contexto.getMethod().getDeclaringClass().getSimpleName() // contexto.getTarget().getClass().getSimpleName()
				, contexto.getMethod().getName()
				, System.currentTimeMillis() - inicio));
		return retorno;
	}
}
