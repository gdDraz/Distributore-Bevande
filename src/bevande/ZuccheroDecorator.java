package bevande;

import sistema.Distributore;
/**
 * Decorator per aggiungere Zucchero a una {@link Bevanda}.
 * <p>
 * Questa classe estende {@link BevandaDecorator} e rappresenta l'aggiunta
 * dello zucchero a una bevanda base. Il prezzo dello zucchero viene recuperato
 * dinamicamente dal {@link sistema.Distributore}
 * </p>
 */
public class ZuccheroDecorator extends BevandaDecorator {

	private static final long serialVersionUID = -1601553196134408816L;
	protected int dosi;
	/**
     * Costruisce un {@code ZuccheroDecorator} che avvolge una bevanda esistente,
     * aggiungendo l'ingrediente "Zucchero". Il prezzo moltiplicato per 2 visto che il suo 
     * prezzo vale solo come aggiunta
     *
     * @param bevanda la bevanda base a cui aggiungere lo zucchero
     * @param dosi numero di dosi da aggiungere da 1 a 5
     */
	public ZuccheroDecorator(Bevanda bevanda, int dosi) {
	    super(bevanda, new Zucchero(Distributore.getInstance().getPrezzo("Zucchero") * dosi*2));
	    this.dosi = dosi;
	}
	/**
	 * Override di {@link BevandaDecorator} per una corretta stampa singolare/plurale
	 */
	@Override
    public String prepara() {
        return bevanda.prepara() + " + aggiunta di " + dosi + (dosi == 1 ? " dose" : " dosi") + " di zucchero";
    }

}
