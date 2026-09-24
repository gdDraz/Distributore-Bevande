package bevande;

/**
 * La classe {@code Limone} rappresenta una bevanda semplice di limone.
 * <p>
 * Estende {@link Bevanda} specificando nome e prezzo di default (0.2),
 * con la possibilita' di personalizzare il prezzo tramite costruttore.
 * </p>
 */
public class Limone extends Bevanda{

	private static final long serialVersionUID = -685937158342766940L;

	/**
     * Costruisce un'istanza di {@code Limone} con prezzo predefinito di 0.20.
     */
	public Limone() {
		super("Limone", 0.20);
	}
	
	/**
     * Costruisce un'istanza di {@code Limone} con prezzo personalizzato.
     *
     * @param prezzo il prezzo da associare alla bevanda
     */
	public Limone(double prezzo) {
		super("Limone", prezzo);
	}
	
	/**
     * Restituisce una descrizione testuale della preparazione del limone.
     *
     * @return stringa che indica la preparazione
     */
	@Override
	public String prepara() {
		return "Preparando limone ";
	}
}
