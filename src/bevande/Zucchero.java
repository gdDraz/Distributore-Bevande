package bevande;
/**
 * La classe {@code Zucchero} rappresenta lo zucchero.
 * <p>
 * Estende {@link Bevanda} specificando nome e prezzo di default (0.10),
 * con la possibilita' di personalizzare il prezzo tramite costruttore.
 * </p>
 */
public class Zucchero extends Bevanda {
	
	private static final long serialVersionUID = 1022893724207213108L;
	
	/**
     * Costruisce un'istanza di {@code Zucchero} con prezzo predefinito di 0.10.
     */
	public Zucchero() {
		super("Zucchero", 0.1);
	}
	
	/**
     * Costruisce un'istanza di {@code Zucchero} con prezzo personalizzato.
     *
     * @param prezzo il prezzo da associare alla bevanda
     */
	public Zucchero(double prezzo) {
		super("Zucchero", prezzo);
	}
	
	/**
     * Lo zucchero restitusce una stringa vuota.
     *
     * @return stringa che indica la preparazione
     */
	@Override
	public String prepara() {
		return "";
	}
}
