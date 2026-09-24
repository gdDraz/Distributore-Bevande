package bevande;

/**
 * La classe {@code Caffe} rappresenta una bevanda semplice di caffe.
 * <p>
 * Estende {@link Bevanda} specificando nome e prezzo di default (1),
 * con la possibilita' di personalizzare il prezzo tramite costruttore.
 * </p>
 */
public class Caffe extends Bevanda{

	private static final long serialVersionUID = 3700307523263831511L;
	
	/**
     * Costruisce un'istanza di {@code Caffe} con prezzo predefinito di 1.
     */
	public Caffe() {
		super("Caffe", 1);
	}
	
	/**
     * Costruisce un'istanza di {@code Caffe} con prezzo personalizzato.
     *
     * @param prezzo il prezzo da associare alla bevanda
     */
	public Caffe(double prezzo) {
		super("Caffe", prezzo);		
	}
	
	/**
     * Restituisce una descrizione testuale della preparazione del caffe.
     *
     * @return stringa che indica la preparazione
     */
	@Override
	public String prepara() {
		return "Preparo Caffe' ";
	}
	
}
