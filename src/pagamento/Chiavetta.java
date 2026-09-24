package pagamento;

import java.util.List;

/**
 * Rappresenta una chiavetta prepagata per il pagamento nel distributore.
 * Ogni chiavetta ha un saldo e un ID univoco.
 */
public class Chiavetta {
    /** ID della chiavetta nel formato CHV001, CHV002, ... */
    private String id;

    /** Saldo attuale della chiavetta */
    private double saldo;

    /** Ultimo ID numerico generato */
    private static int lastID = 0;

    /**
     * Costruisce una nuova chiavetta con ID e saldo iniziale.
     *
     * @param id ID della chiavetta
     * @param saldoIniziale saldo iniziale
     */
    public Chiavetta(String id, double saldoIniziale) {
        this.id = id;
        this.saldo = saldoIniziale;
    }

    /**
     * Preleva un importo dal saldo della chiavetta.
     *
     * @param importo importo da prelevare
     * @return true se il prelievo ha avuto successo, false se saldo insufficiente
     */
    public boolean preleva(double importo) {
        if (saldo >= importo) {
            saldo -= importo;
            return true;
        }
        return false;
    }

    /**
     * Ricarica la chiavetta con un importo specificato.
     *
     * @param importo importo da aggiungere al saldo
     */
    public void ricarica(double importo) {
        saldo += importo;
    }

    /**
     * Restituisce il saldo attuale della chiavetta.
     *
     * @return saldo attuale
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Restituisce una rappresentazione testuale della chiavetta.
     *
     * @return stringa contenente ID e saldo
     */
    @Override
    public String toString() {
        return "id='" + id + '\'' + ", saldo=" + String.format("%.2f", saldo);
    }


    /**
     * Restituisce l'ID della chiavetta.
     *
     * @return ID della chiavetta
     */
    public String getId() {
        return id;
    }

    /**
     * Genera un nuovo ID libero per una chiavetta, evitando conflitti con chiavette esistenti.
     *
     * @param chiavette lista delle chiavette esistenti
     * @return nuovo ID disponibile
     */
    public static String generaIDLibero(List<Chiavetta> chiavette) {
        int id = 1;
        while (true) {
            String candidate = String.format("CHV%03d", id);
            boolean exists = false;
            for (Chiavetta c : chiavette) {
                if (c.getId().equals(candidate)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                return candidate; // trovato un buco libero
            }
            id++;
        }
    }

    /**
     * Restituisce l'ultimo ID numerico generato.
     *
     * @return ultimo ID numerico
     */
    public static int getLastID() {
        return lastID;
    }

    /**
     * Imposta l'ultimo ID numerico generato.
     *
     * @param lastID nuovo valore per l'ultimo ID
     */
    public static void setLastID(int lastID) {
        Chiavetta.lastID = lastID;
    }
}
