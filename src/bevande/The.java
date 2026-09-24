package bevande;

/**
 * La classe {@code The} rappresenta una bevanda semplice di the.
 * <p>
 * Estende {@link Bevanda} specificando nome e prezzo di default (1,20),
 * con la possibilita' di personalizzare il prezzo tramite costruttore.
 * </p>
 */
public class The extends Bevanda{

	private static final long serialVersionUID = -685937158342766940L;

	/**
     * Costruisce un'istanza di {@code The} con prezzo predefinito di 1.20.
     */
	public The() {
		super("The", 1.20);
	}
	
	/**
     * Costruisce un'istanza di {@code The} con prezzo personalizzato.
     *
     * @param prezzo il prezzo da associare alla bevanda
     */
	public The(double prezzo) {
		super("The", prezzo);
	}
	
	/**
     * Restituisce una descrizione testuale della preparazione del the.
     *
     * @return stringa che indica la preparazione
     */
	@Override
	public String prepara() {
		return "Preparando the ";
	}
}
