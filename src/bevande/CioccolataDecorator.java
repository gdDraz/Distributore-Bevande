package bevande;

import sistema.Distributore;
/**
 * Decorator per aggiungere Cioccolata a una {@link Bevanda}.
 * <p>
 * Questa classe estende {@link BevandaDecorator} e rappresenta l'aggiunta
 * della cioccolata a una bevanda base. Il prezzo della cioccolata viene recuperato
 * dinamicamente dal {@link sistema.Distributore}
 * </p>
 */
public class CioccolataDecorator extends BevandaDecorator {

	private static final long serialVersionUID = -7354474797044625331L;

	/**
     * Costruisce un {@code CioccolataDecorator} che avvolge una bevanda esistente,
     * aggiungendo l'ingrediente "Cioccolata".
     *
     * @param bevanda la bevanda base a cui aggiungere la cioccolata
     */
	public CioccolataDecorator(Bevanda bevanda) {
		super(bevanda, new Cioccolata(Distributore.getInstance().getPrezzo("Cioccolata")));
	}

}
