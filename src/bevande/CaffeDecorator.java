package bevande;

import sistema.Distributore;
/**
 * Decorator per aggiungere Caffe a una {@link Bevanda}.
 * <p>
 * Questa classe estende {@link BevandaDecorator} e rappresenta l'aggiunta
 * del caffe a una bevanda base. Il prezzo del caffe viene recuperato
 * dinamicamente dal {@link sistema.Distributore}
 * </p>
 */
public class CaffeDecorator extends BevandaDecorator {

	private static final long serialVersionUID = -8897890052946036321L;

	/**
     * Costruisce un {@code CaffeDecorator} che avvolge una bevanda esistente,
     * aggiungendo l'ingrediente "Caffe".
     *
     * @param bevanda la bevanda base a cui aggiungere il caffe
     */
	public CaffeDecorator(Bevanda bevanda) {
		super(bevanda, new Caffe(Distributore.getInstance().getPrezzo("Caffe")));
	}

}
