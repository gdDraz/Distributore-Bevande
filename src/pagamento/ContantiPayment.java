package pagamento;

/**
 * Implementazione di {@link PaymentStrategy} per il pagamento in contanti.
 * <p>
 * Gestisce l'importo inserito e calcola il resto se il pagamento risulta sufficiente.
 * </p>
 */
public class ContantiPayment implements PaymentStrategy {

    private double inserito;
    private double resto;

    /**
     * Costruisce un pagamento in contanti con l'importo inserito dall'utente.
     *
     * @param inserito importo di denaro inserito
     */
    public ContantiPayment(double inserito) {
        this.inserito = inserito;
    }

    /**
     * Esegue il pagamento in contanti.
     * <p>
     * Se l'importo inserito maggiore o uguale a quello richiesto,
     * il pagamento viene considerato valido e viene calcolato il resto.
     * </p>
     *
     * @param importo importo da pagare
     * @return {@code true} se il pagamento risulta sufficiente, {@code false} altrimenti
     */
    @Override
    public boolean paga(double importo) {
        if (inserito >= importo) {
            resto = inserito - importo;
            return true;
        }
        return false;
    }

    /**
     * Restituisce il resto calcolato dopo il pagamento.
     *
     * @return resto se il pagamento va a buon fine, 0 altrimenti
     */
    public double getResto() {
        return resto;
    }

    /**
     * Restituisce il metodo di pagamento utilizzato.
     *
     * @return sempre la stringa "Contanti"
     */
    @Override
    public String getMetodo() {
        return "Contanti";
    }
}
