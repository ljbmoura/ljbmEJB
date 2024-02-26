package br.com.ljbm.fp.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.ljbm.utilitarios.Recurso;

public class AvaliadorInvestimentoTest {
	private static final File resourcesDir = Recurso
			.getPastaRecursos(AvaliadorInvestimentoTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testComparaInvestimentosFrenteSELIC() {

		String caminhoSerieHistoricaSELIC = resourcesDir.getPath()
				+ File.separator + "TaxaSelic_Diaria_20060102_20120319.txt";

		String caminhaExtratoInvestimentos = resourcesDir.getPath()
				+ File.separator + "extratoInvestimentos.txt";

		String caminhoResultado = resourcesDir.getPath() + File.separator
				+ "comparativoEsperado.txt";

		AvaliadorInvestimento ai = new AvaliadorInvestimentoImpl();

		// TODO: IMPLEMENTAR MOCK NO CONSTRUTOR PARA REDIRECIONAR ARQUIVOS DE
		// TESTE
		try {
			// String res = ai.comparaInvestimentosFrenteSELIC(
			// caminhaExtratoInvestimentos, caminhoSerieHistoricaSELIC);
			//FIXME
			String res = "PENDENTE";
//			String res = ai.comparaInvestimentosFrenteSELICArquivos(
//					"2012-03-19", caminhaExtratoInvestimentos,
//					caminhoSerieHistoricaSELIC);
			System.out.print(res);
			System.out.println("luc");
			System.out.print(Recurso.getFileContents(caminhoResultado));
			assertEquals(Recurso.getFileContents(caminhoResultado), res);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}

	}

	@Ignore
	public void testComparaInvestimentosFrenteSELIC_ClienteEJB() {

		String caminhoSerieHistoricaSELIC = resourcesDir.getPath()
				+ File.separator + "TaxaSelic_Diaria_20060102_20120319.txt";

		String caminhaExtratoInvestimentos = resourcesDir.getPath()
				+ File.separator + "extratoInvestimentos.txt";

		String caminhoResultado = resourcesDir.getPath() + File.separator
				+ "comparativoEsperado.txt";

		Writer destino = new StringWriter();

		try {
			InitialContext ctx = new InitialContext();
			Object object = ctx.lookup("ejb/AvaliadorInvestimentoBean");
			AvaliadorInvestimento ai = (AvaliadorInvestimento) PortableRemoteObject
					.narrow(object, AvaliadorInvestimento.class);
			// TODO: IMPLEMENTAR MOCK NO CONSTRUTOR PARA REDIRECIONAR ARQUIVOS
			// DE TESTE
			// String res = ai.comparaInvestimentosFrenteSELIC(
			// caminhaExtratoInvestimentos, caminhoSerieHistoricaSELIC);

//			System.out.println(ai.comparaInvestimentosComSELIC(
//					"2012-03-19", caminhaExtratoInvestimentos,
//					caminhoSerieHistoricaSELIC));
			assertEquals(Recurso.getFileContents(caminhoResultado),
					destino.toString());
		} catch (NamingException e) {
			e.printStackTrace();
			fail(e.getExplanation());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}

	}

}
