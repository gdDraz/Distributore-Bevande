package pagamento;

/**
 * Implementazione di {@link PaymentStrategy} per il pagamento tramite Chiavetta.
 * <p>
 * Utilizza un oggetto {@link Chiavetta} per prelevare l'importo richiesto.
 * </p>
 */
public class ChiavettaPayment implements PaymentStrategy {

    private Chiavetta chiavetta;

    /**
     * Costruisce un pagamento tramite una specifica chiavetta.
     *
     * @param chiavetta chiavetta da utilizzare per il pagamento
     */
    public ChiavettaPayment(Chiavetta chiavetta) {
        this.chiavetta = chiavetta;
    }

    /**
     * Esegue il pagamento prelevando l'importo dalla chiavetta.
     *
     * @param importo importo da pagare
     * @return {@code true} se il pagamento viene effettuato con successo, {@code false} altrimenti
     */
    @Override
    public boolean paga(double importo) {
        return chiavetta.preleva(importo);
    }

    /**
     * Restituisce il metodo di pagamento utilizzato.
     *
     * @return sempre la stringa "Chiavetta"
     */
    @Override
    public String getMetodo() {
        return "Chiavetta";
    }
}
