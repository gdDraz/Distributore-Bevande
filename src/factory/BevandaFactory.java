package factory;

import bevande.*;

/**
 * La classe {@code BevandaFactory} si occupa della creazione di oggetti {@link Bevanda}
 * a partire da una bevanda base, un'aggiunta opzionale e un numero di cucchiaini di zucchero.
 * <p>
 * Implementa il <b>Factory Pattern</b> per semplificare l'istanziazione
 * delle combinazioni di bevande con i rispettivi {@link BevandaDecorator}.
 * </p>
 */
public class BevandaFactory {

    /**
     * Crea una nuova bevanda a partire da una base e, opzionalmente, da un'aggiunta
     * e da una quantita' di zucchero.
     * <ul>
     *     <li>Se presente un'aggiunta, viene avvolta con il decoratore appropriato.</li>
     *     <li>Se presente zucchero, viene applicato il {@link ZuccheroDecorator}.</li>
     * </ul>
     *
     * @param base     la bevanda di partenza (es. {@code Caffe}, {@code Latte}, ecc.)
     * @param aggiunta la bevanda da utilizzare come aggiunta (es. {@code Latte}, {@code Limone}, ecc.);
     *                 puo' essere {@code null} se non sono richieste aggiunte
     * @param zucchero quantita' di cucchiaini di zucchero da aggiungere (0 se non richiesto)
     * @return una nuova istanza di {@link Bevanda}, decorata secondo i parametri passati
     * @throws IllegalArgumentException se l'aggiunta non viene riconosciuta
     */
    public Bevanda creaBevanda(Bevanda base, Bevanda aggiunta, int zucchero) {
        Bevanda result = base;

        if (aggiunta != null && !aggiunta.getClass().equals(base.getClass())) {
            switch (aggiunta.getName()) {
                case "Caffe":
                    result = new CaffeDecorator(result);
                    break;
                case "Latte":
                    result = new LatteDecorator(result);
                    break;
                case "Camomilla":
                    result = new CamomillaDecorator(result);
                    break;
                case "Cioccolata":
                    result = new CioccolataDecorator(result);
                    break;
                case "Limone":
                    result = new LimoneDecorator(result);
                    break;
                case "The":
                    result = new TheDecorator(result);
                    break;
                default:
                    throw new IllegalArgumentException("Bevanda sconosciuta " + aggiunta.getName());
            }
        }

        if (zucchero > 0) {
            result = new ZuccheroDecorator(result, zucchero);
        }

        return result;
    }
}
