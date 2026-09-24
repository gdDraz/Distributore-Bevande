package bevande;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe astratta che rappresenta una bevanda generica erogabile dal distributore.
 * <p>
 * Ogni sottoclasse di {@code Bevanda} definisce una bevanda concreta
 * (ad esempio {@code Caffe}, {@code Latte}, {@code The}, ecc.)
 * ed implementa il metodo {@link #prepara()}.
 * </p>
 *
 * <p>
 * La classe gestisce anche una lista di possibili aggiunte
 * (ad esempio zucchero, latte, limone), utile per estendere
 * la bevanda tramite il pattern Decorator.
 * </p>
 */
public abstract class Bevanda implements Serializable {
    private static final long serialVersionUID = -7631835341704777350L;

    /** Nome della bevanda. */
    protected String name;

    /** Prezzo base della bevanda. */
    protected double price;

    /** Lista delle possibili aggiunte per questa bevanda. */
    protected List<String> aggiunte;

    /**
     * Costruisce una nuova istanza di {@code Bevanda}.
     *
     * @param name  il nome della bevanda
     * @param price il prezzo base della bevanda
     */
    public Bevanda(String name, double price) {
        this.name = name;
        this.price = price;
        aggiunte = new ArrayList<>();
        aggiunte.add("Nessuna"); // default
    }

    /**
     * Metodo astratto che deve definire la logica di preparazione
     * della bevanda concreta.
     *
     * @return descrizione testuale della preparazione
     */
    public abstract String prepara();

    /**
     * Restituisce il prezzo della bevanda.
     *
     * @return prezzo della bevanda
     */
    public double getPrezzo() {
        return this.price;
    }

    /**
     * Imposta un nuovo prezzo per la bevanda.
     *
     * @param price nuovo prezzo
     */
    public void setPrezzo(double price) {
        this.price = price;
    }

    /**
     * Restituisce il nome della bevanda.
     *
     * @return nome della bevanda
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce una rappresentazione testuale della bevanda,
     * corrispondente al suo nome.
     *
     * @return nome della bevanda
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Due bevande sono considerate uguali se hanno lo stesso nome.
     *
     * @param obj oggetto da confrontare
     * @return {@code true} se i nomi coincidono, {@code false} altrimenti
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bevanda other = (Bevanda) obj;
        return this.name.equals(other.name);
    }

    /**
     * Calcola l'hash della bevanda in base al suo nome.
     *
     * @return hash code della bevanda
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Restituisce la lista delle possibili aggiunte associate alla bevanda.
     *
     * @return lista delle aggiunte disponibili
     */
    public List<String> getPossibiliaggiunte() {
        return this.aggiunte;
    }

    /**
     * Aggiunge una nuova aggiunta alla lista delle possibili aggiunte.
     *
     * @param b nome dell'aggiunta
     */
    public void aggiungiNuovaAggiunta(String b) {
        this.aggiunte.add(b);
    }

    /**
     * Rimuove una aggiunta dalla lista delle possibili aggiunte.
     *
     * @param b nome dell'aggiunta da rimuovere
     */
    public void eliminaAggiunta(String b) {
        this.aggiunte.remove(b);
    }
}
