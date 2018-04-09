package br.com.ljbm.lab;

import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.rmi.PortableRemoteObject;

import br.com.ljbm.fp.servico.AvaliadorInvestimentoRemote;


public class labJNDI_JBoss {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		exploraJNDIJBoss();	
	}
	
	
	private static void exploraJNDIJBoss () {
		
		try {
			// final Hashtable env = new Hashtable();
			Properties env = new Properties();
			

			env.put(Context.INITIAL_CONTEXT_FACTORY,
					InitialContextFactory.class
							.getName());
			//env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			env.put(Context.PROVIDER_URL, "remote://localhost:4447");
			env.put(Context.SECURITY_PRINCIPAL, "aplicacao");
			env.put(Context.SECURITY_CREDENTIALS, "dun541vast207");
//			env.put(Context.SECURITY_PROTOCOL, "ssl");
//			env.put(Context.SECURITY_AUTHENTICATION, "none");
	// TODO linha deativada		
//				   Security.addProvider(new JBossSaslProvider());
				

			Context ctx = new InitialContext(env);
			
			//String raiz = "";
	        String raiz = "java:app";
			System.out.println(raiz);

			//showTree(ctx, Integer.MAX_VALUE);
			//showTree(nome, ctx, 0, Integer.MAX_VALUE);
			
			
	        NamingEnumeration<NameClassPair> nameList = ctx.list(raiz);
	        while(nameList.hasMore()) {
	            NameClassPair name = (NameClassPair) nameList.next();
	            System.out.println("contexto " + raiz + "/" + name.getName() );
	            showTree(raiz + "/" + name.getName() + "/", (Context) ctx.lookup(raiz + "/" + name.getName()), 0, Integer.MAX_VALUE);
//	            logger.info("{}", name);
	        }
			
			
			NamingEnumeration<Binding> lb = ctx.listBindings(raiz);
			while (lb.hasMoreElements()) {
				Binding b = (Binding) lb.next();
				System.out.println(b.toString());
			}

			//showTree(ctx, Integer.MAX_VALUE);
			Object object = ctx.lookup("AvaliadorInvestimentoRemote");
			AvaliadorInvestimentoRemote ai = (AvaliadorInvestimentoRemote) PortableRemoteObject
					.narrow(object, AvaliadorInvestimentoRemote.class);
			System.out.println(ai.getMessage());

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void showTree(Context ctx) throws NamingException {
		showTree(ctx, Integer.MAX_VALUE);
	}

	public void showTree(Context ctx, int maxDepth)
			throws NamingException {
		System.out.println("----------------------------");
		showTree("java", ctx, 0, maxDepth);
		System.out.println("----------------------------");
	}

	private static void showTree(String indent, Context ctx, int depth,
			int maxDepth) throws NamingException {
		if (depth == maxDepth)
			return;
		NamingEnumeration<NameClassPair> rel = ctx.list("");
		while (rel.hasMoreElements()) {
			NameClassPair ncp = (NameClassPair) rel.next();
			System.out.println(indent + ncp);
			if (ncp.getClassName().indexOf("Context") != -1)
				showTree(indent + ncp.getName() + "/",
						(Context) ctx.lookup(ncp.getName()), depth + 1,
						maxDepth);
		}
	}

}
