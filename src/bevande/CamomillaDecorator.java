package bevande;

import sistema.Distributore;
/**
 * Decorator per aggiungere Camomilla a una {@link Bevanda}.
 * <p>
 * Questa classe estende {@link BevandaDecorator} e rappresenta l'aggiunta
 * della camomilla a una bevanda base. Il prezzo della camomilla viene recuperato
 * dinamicamente dal {@link sistema.Distributore}
 * </p>
 */
public class CamomillaDecorator extends BevandaDecorator {

	private static final long serialVersionUID = 5317444322996487948L;

	/**
     * Costruisce un {@code CamomillaDecorator} che avvolge una bevanda esistente,
     * aggiungendo l'ingrediente "Camomilla".
     *
     * @param bevanda la bevanda base a cui aggiungere la camomilla
     */
	public CamomillaDecorator(Bevanda bevanda) {
		super(bevanda, new Camomilla(Distributore.getInstance().getPrezzo("Camomilla")));
	}
}
