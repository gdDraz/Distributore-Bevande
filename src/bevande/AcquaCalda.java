package bevande;

/**
 * La classe {@code AcquaCalda} rappresenta una bevanda semplice di acqua calda.
 * <p>
 * Estende {@link Bevanda} specificando nome e prezzo di default (0.60),
 * con la possibilita' di personalizzare il prezzo tramite costruttore.
 * </p>
 */
public class AcquaCalda extends Bevanda {
	
    private static final long serialVersionUID = 430210876201099368L;

    /**
     * Costruisce un'istanza di {@code AcquaCalda} con prezzo predefinito di 0.60.
     */
    public AcquaCalda() {
        super("Acqua Calda", 0.60);
    }

    /**
     * Costruisce un'istanza di {@code AcquaCalda} con prezzo personalizzato.
     *
     * @param prezzo il prezzo da associare alla bevanda
     */
    public AcquaCalda(double prezzo) {
        super("Acqua Calda", prezzo);
    }

    /**
     * Restituisce una descrizione testuale della preparazione dell'acqua calda.
     *
     * @return stringa che indica la preparazione
     */
    @Override
    public String prepara() {
        return "Preparando acqua calda ";
    }
}
