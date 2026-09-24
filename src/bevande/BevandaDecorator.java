package bevande;

/**
 * Classe astratta che implementa il pattern Decorator per le {@link Bevanda}.
 * <p>
 * Ogni {@code BevandaDecorator} permette di arricchire una bevanda di base
 * con un'aggiunta (zucchero, latte, limone, ecc.), mantenendo compatibilita'
 * con la gerarchia delle {@link Bevanda}.
 * </p>
 */
public abstract class BevandaDecorator extends Bevanda {
    private static final long serialVersionUID = 4000980280877744886L;

    /** La bevanda base che viene decorata. */
    protected Bevanda bevanda;

    /** L'aggiunta che arricchisce la bevanda base. */
    protected Bevanda aggiunta;

    /**
     * Costruisce un {@code BevandaDecorator} che avvolge una bevanda di base
     * con una specifica aggiunta.
     *
     * <p>Il nome della bevanda risultante sara' costruito concatenando i nomi
     * della bevanda e dell'aggiunta, mentre il prezzo sara' la somma del prezzo
     * base e della meta' del prezzo dell'aggiunta.</p>
     *
     * @param bevanda la bevanda di base
     * @param aggiunta l'aggiunta da incorporare
     */
    public BevandaDecorator(Bevanda bevanda, Bevanda aggiunta) {    
        super(bevanda.name + " con " + aggiunta.name, bevanda.price + aggiunta.price / 2);
        this.bevanda = bevanda;
        this.aggiunta = aggiunta;
    }

    /**
     * Restituisce la descrizione testuale della preparazione,
     * includendo sia la bevanda base sia l'aggiunta.
     *
     * @return stringa descrittiva della preparazione
     */
    @Override
    public String prepara() {
        return this.bevanda.prepara() + " con " + this.aggiunta.name;
    }

    /**
     * Restituisce il nome della bevanda decorata.
     *
     * @return nome della bevanda decorata
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Restituisce la bevanda base (senza l'aggiunta).
     *
     * @return la bevanda di base
     */
    public Bevanda getBevanda() {
        return bevanda;
    }

    /**
     * Restituisce l'aggiunta che arricchisce la bevanda.
     *
     * @return l'aggiunta
     */
    public Bevanda getAggiunta() {
        return aggiunta;
    }
}
