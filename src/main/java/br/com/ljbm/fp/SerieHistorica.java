package br.com.ljbm.fp;

/**
 * @author ljbm
 * armazena uma serie historica de objetos T 
 */

import java.util.Calendar;
import java.util.Hashtable;

public class SerieHistorica<T> {

	private final Calendar menorChave;
	@SuppressWarnings("PMD")
	private final Calendar maiorChave;

	/**
	 * @param menorChave
	 */
	public SerieHistorica(Calendar menorChave, Calendar maiorChave) {
		this.menorChave = menorChave;
		this.maiorChave = maiorChave;
	}

	private Hashtable<Calendar, T> elementos = new Hashtable<Calendar, T>();

	public void addElemento(Calendar data, T elemento) {
		elementos.put(data, elemento);
	}

	public T getElemento(Calendar data) {
		return elementos.get(data);
	}

	public T getElementoNaDataOuAnterior(Calendar data) {
		Calendar dataAlternativa = data;
		T elemento = null;
		do {
			elemento = elementos.get(dataAlternativa);
			if (elemento != null) {
				break;
			} else {
				dataAlternativa.add(Calendar.DAY_OF_MONTH, -1);
			}
		} while (dataAlternativa.after(menorChave));

		return elemento;
	}

}
