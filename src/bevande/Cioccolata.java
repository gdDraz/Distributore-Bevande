package bevande;

/**
 * La classe {@code Cioccolata} rappresenta una bevanda semplice di cioccolata.
 * <p>
 * Estende {@link Bevanda} specificando nome e prezzo di default (1.60),
 * con la possibilita' di personalizzare il prezzo tramite costruttore.
 * </p>
 */
public class Cioccolata extends Bevanda {

	private static final long serialVersionUID = 307100615080799919L;

	/**
     * Costruisce un'istanza di {@code Cioccolata} con prezzo predefinito di 1.40.
     */
	public Cioccolata() {
		super("Cioccolata", 1.60);
	}
	
	/**
     * Costruisce un'istanza di {@code Cioccolata} con prezzo personalizzato.
     *
     * @param prezzo il prezzo da associare alla bevanda
     */
	public Cioccolata(double prezzo) {
		super("Cioccolata", prezzo);
	}
	
	/**
     * Restituisce una descrizione testuale della preparazione della cioccolata.
     *
     * @return stringa che indica la preparazione
     */
	@Override
	public String prepara() {
		return "Preparando cioccolata ";
	}
}
