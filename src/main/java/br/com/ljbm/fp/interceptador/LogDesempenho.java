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
		log.debug(String.format("duração %s[%s]: %sms", contexto.getMethod().getName(),
				contexto.getTarget().getClass().getSimpleName(), System.currentTimeMillis() - inicio));
		return retorno;
	}
}
