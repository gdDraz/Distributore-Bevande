package bevande;

/**
 * La classe {@code Camomilla} rappresenta una bevanda semplice di camomilla.
 * <p>
 * Estende {@link Bevanda} specificando nome e prezzo di default (1.40),
 * con la possibilita' di personalizzare il prezzo tramite costruttore.
 * </p>
 */
public class Camomilla extends Bevanda{

	private static final long serialVersionUID = 7280529991250927246L;

	/**
     * Costruisce un'istanza di {@code Camomilla} con prezzo predefinito di 1.40.
     */
	public Camomilla() {
		super("Camomilla", 1.40);
	}
	
	/**
     * Costruisce un'istanza di {@code Camomilla} con prezzo personalizzato.
     *
     * @param prezzo il prezzo da associare alla bevanda
     */
	public Camomilla(double prezzo) {
		super("Camomilla", prezzo);
	}
	
	/**
     * Restituisce una descrizione testuale della preparazione della camomilla.
     *
     * @return stringa che indica la preparazione
     */
	@Override
	public String prepara() {
		return "Preparando camomilla ";
	}
}
