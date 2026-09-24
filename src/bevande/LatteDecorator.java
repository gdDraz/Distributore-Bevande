package bevande;

import sistema.Distributore;
/**
 * Decorator per aggiungere Latte a una {@link Bevanda}.
 * <p>
 * Questa classe estende {@link BevandaDecorator} e rappresenta l'aggiunta
 * del latte a una bevanda base. Il prezzo del latte viene recuperato
 * dinamicamente dal {@link sistema.Distributore}
 * </p>
 */
public class LatteDecorator extends BevandaDecorator {

	private static final long serialVersionUID = -3202148330979134633L;

	/**
     * Costruisce un {@code LatteDecorator} che avvolge una bevanda esistente,
     * aggiungendo l'ingrediente "Latte".
     *
     * @param bevanda la bevanda base a cui aggiungere il latte
     */
	public LatteDecorator(Bevanda bevanda) {
		super(bevanda, new Latte(Distributore.getInstance().getPrezzo("Latte")));
	}

	

}
