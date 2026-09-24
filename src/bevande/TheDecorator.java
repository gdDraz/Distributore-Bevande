package bevande;

import sistema.Distributore;
/**
 * Decorator per aggiungere The a una {@link Bevanda}.
 * <p>
 * Questa classe estende {@link BevandaDecorator} e rappresenta l'aggiunta
 * del the a una bevanda base. Il prezzo del the viene recuperato
 * dinamicamente dal {@link sistema.Distributore}
 * </p>
 */
public class TheDecorator extends BevandaDecorator {

	private static final long serialVersionUID = 5317444322996487948L;

	/**
     * Costruisce un {@code TheDecorator} che avvolge una bevanda esistente,
     * aggiungendo l'ingrediente "The".
     *
     * @param bevanda la bevanda base a cui aggiungere il the
     */
	public TheDecorator(Bevanda bevanda) {
		super(bevanda, new The(Distributore.getInstance().getPrezzo("The")/2));
	}
}
