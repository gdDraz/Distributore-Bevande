package pagamento;

/**
 * Implementazione di {@link PaymentStrategy} per il pagamento tramite carta di credito.
 * <p>
 * Questa classe simula una transazione con carta di credito.
 * </p>
 */
public class CartaCreditoPayment implements PaymentStrategy {

    /**
     * Esegue il pagamento simulando una transazione con carta di credito.
     *
     * @param importo importo da pagare
     * @return sempre {@code true},assumiamo che la transazione viene considerata sempre riuscita
     */
    @Override
    public boolean paga(double importo) {
        System.out.println("Transazione in corso...");
        return true;
    }

    /**
     * Restituisce il metodo di pagamento utilizzato.
     *
     * @return sempre la stringa "Carta di Credito"
     */
    @Override
    public String getMetodo() {
        return "Carta di Credito";
    }
}
