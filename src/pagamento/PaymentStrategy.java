package pagamento;

/**
 * Interfaccia per il pattern Strategy dei metodi di pagamento.
 * <p>
 * Le classi che implementano questa interfaccia definiscono il comportamento 
 * specifico di un metodo di pagamento (contanti, carta, chiavetta).
 * </p>
 */
public interface PaymentStrategy {

    /**
     * Esegue il pagamento di un determinato importo.
     *
     * @param importo importo da pagare
     * @return {@code true} se il pagamento va a buon fine, {@code false} altrimenti
     */
    boolean paga(double importo);

    /**
     * Restituisce il nome del metodo di pagamento implementato.
     *
     * @return stringa rappresentante il metodo di pagamento
     */
    String getMetodo();
}
