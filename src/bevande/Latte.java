package bevande;

/**
 * La classe {@code Latte} rappresenta una bevanda semplice di latte.
 * <p>
 * Estende {@link Bevanda} specificando nome e prezzo di default (1,20),
 * con la possibilita' di personalizzare il prezzo tramite costruttore.
 * </p>
 */
public class Latte extends Bevanda{

	private static final long serialVersionUID = -685937158342766940L;

	/**
     * Costruisce un'istanza di {@code Latte} con prezzo predefinito di 1.20.
     */
	public Latte() {
		super("Latte", 1.20);
	}
	
	/**
     * Costruisce un'istanza di {@code Latte} con prezzo personalizzato.
     *
     * @param prezzo il prezzo da associare alla bevanda
     */
	public Latte(double prezzo) {
		super("Latte", prezzo);
	}
	
	/**
     * Restituisce una descrizione testuale della preparazione del latte.
     *
     * @return stringa che indica la preparazione
     */
	@Override
	public String prepara() {
		return "Preparando latte ";
	}
}
