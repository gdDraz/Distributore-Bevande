package sistema;

import bevande.*;
import factory.BevandaFactory;
import pagamento.Chiavetta;

import org.json.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Singleton che rappresenta il distributore automatico di bevande.
 * <p>
 * Questa classe gestisce le scorte, i prezzi, i consumi e le chiavette
 * per i pagamenti, il cuore logico del sistema e centralizza tutte
 * le operazioni principali.
 * </p>
 */
public class Distributore {
    /** Istanza singleton */
    private static Distributore instance;

    /** Scorte correnti del distributore, organizzate come JSONObject */
    private JSONObject scorte;

    /** Mappa dei consumi mensili, chiave = "yyyy-MM_nomeBevanda", valore = litri */
    private Map<String, Double> consumi;

    /** Mappa dei prezzi per tipo di bevanda */
    private Map<String, Double> prezzi;

    /** Lista delle bevande disponibili */
    private List<Bevanda> bevande;

    /** Lista delle chiavette registrate */
    private List<Chiavetta> chiavette;

    /** Quantita' standard erogata per una bevanda (in litri) */
    private static final double Quantita_BASE = 0.25; // 250ml

    /** Quantita' erogata per una aggiunta (meta' della quantita' base) */
    private static final double Quantita_AGGIUNTA = Quantita_BASE/2;

    /** Soglia sotto la quale una bevanda e' considerata sotto scorta */
    public static final double SOGLIA = 1.0;

    /**
     * Costruttore privato per garantire il pattern Singleton.
     * Inizializza scorte, consumi e chiavette.
     */
    private Distributore() {
        bevande = new ArrayList<>();
        prezzi = new HashMap<>();
        scorte = new JSONObject();
        consumi = new HashMap<>();
        inizializzaScorte();
    }

    /**
     * Restituisce l'istanza unica del distributore.
     *
     * @return istanza singleton
     */
    public static Distributore getInstance() {
        if (instance == null) {
            instance = new Distributore();
        }
        return instance;
    }

    /**
     * Restituisce il prezzo di una bevanda.
     *
     * @param s nome della bevanda
     * @return prezzo della bevanda
     */
    public double getPrezzo(String s) {
        return prezzi.get(s);
    }

    /**
     * Inizializza le scorte leggendo da file oppure generando valori di default.
     */
    private void inizializzaScorte() {
        File f = new File(FileManager.FILE_SCORTE);
        if (f.exists()) {
            this.caricaDaFile();
        } else {
            inizializza(); // crea scorte iniziali
        }
        this.caricaConsumi();
        this.caricaChiavette();
    }

    /**
     * Inizializza l'elenco delle bevande disponibili creando un set predefinito.
     */
    private void inizializza() {
        List<Class<? extends Bevanda>> tipiBevande = Arrays.asList(
            Caffe.class, Latte.class, The.class, Camomilla.class,
            Cioccolata.class, AcquaCalda.class,
            Zucchero.class, Limone.class
        );

        for (Class<? extends Bevanda> tipo : tipiBevande) {
            try {
                Bevanda b = tipo.getDeclaredConstructor().newInstance();
                bevande.add(b);
                aggiungiScorta(b, 5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Aggiunge scorta per una bevanda.
     *
     * @param b bevanda da rifornire
     * @param QuantitaLitri litri da aggiungere
     */
    public void aggiungiScorta(Bevanda b, double QuantitaLitri) {
        String nome = b.getName();
        JSONObject obj = scorte.optJSONObject(nome);

        if (obj == null) {
            obj = new JSONObject();
            obj.put("Prezzo", b.getPrezzo());
            obj.put("Quantita", QuantitaLitri);
        } else {
            double attuale = obj.getDouble("Quantita");
            obj.put("Quantita", attuale + QuantitaLitri);
        }

        List<String> aggiunte = b.getPossibiliaggiunte();
        if (aggiunte != null && !aggiunte.isEmpty() &&
            !(aggiunte.size() == 1 && aggiunte.contains("Nessuna"))) {
            obj.put("Aggiunte", new JSONArray(aggiunte));
        }

        scorte.put(nome, obj);
        this.salvaScorte();
    }

    /**
     * Imposta un nuovo prezzo per una bevanda.
     *
     * @param b bevanda di riferimento
     * @param nuovoPrezzo nuovo prezzo
     */
    public void impostaPrezzo(Bevanda b, double nuovoPrezzo) {
        String nome = b.getName();
        JSONObject obj = scorte.optJSONObject(nome);
        if (obj != null) {
            obj.put("Prezzo", nuovoPrezzo);
            this.salvaScorte();
        }
    }

    /**
     * Verifica se una bevanda risulta sotto scorta.
     *
     * @param b bevanda da controllare
     * @return true se sotto scorta, false altrimenti
     */
    public boolean sottoScorta(Bevanda b) {
        if (b instanceof BevandaDecorator) {
            BevandaDecorator decorator = (BevandaDecorator)b;
            return sottoScorta(decorator.getBevanda()) || sottoScorta(decorator.getAggiunta());
        }

        String nome = b.getName();
        JSONObject obj = scorte.optJSONObject(nome);
        if (obj == null) return true;
        return obj.getDouble("Quantita") < SOGLIA;
    }

    /**
     * Eroga una bevanda, aggiornando scorte e consumi.
     *
     * @param b bevanda richiesta
     */
    public void eroga(Bevanda b) {
        if (b instanceof BevandaDecorator) {
            BevandaDecorator decorator = (BevandaDecorator)b;
            erogazione(decorator.getBevanda(), false);
            erogazione(decorator.getAggiunta(), true);
        } else {
            erogazione(b, false);
        }
    }

    /**
     * Esegue l'erogazione di una singola bevanda o aggiunta.
     *
     * @param b bevanda
     * @param halfQuantity se true eroga meta' quantita'
     */
    private void erogazione(Bevanda b, boolean halfQuantity) {
        String nome = b.getName();
        JSONObject obj = scorte.optJSONObject(nome);
        if (obj != null) {
            double attuale = obj.getDouble("Quantita");
            double quantity = (halfQuantity) ? Quantita_AGGIUNTA : Quantita_BASE;
            double nuovo = Math.max(0, attuale - quantity);
            obj.put("Quantita", nuovo);
            scorte.put(nome, obj);
            this.salvaScorte();

            // aggiorna consumi
            String meseCorrente = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            String key = meseCorrente + "_" + b.getName();
            consumi.put(key, consumi.getOrDefault(key, 0.0) + quantity);
            this.salvaConsumi();
        }
    }

    /**
     * Restituisce la quantita' in scorta di una bevanda.
     *
     * @param b bevanda
     * @return quantita' disponibile in litri
     */
    public double getScorta(Bevanda b) {
        String nome = b.getName();
        JSONObject obj = scorte.optJSONObject(nome);
        if (obj != null) {
            return obj.getDouble("Quantita");
        }
        return 0;
    }

    /**
     * Crea una nuova bevanda decorata (con aggiunta e zucchero).
     *
     * @param base bevanda base
     * @param aggiunta bevanda aggiuntiva
     * @param zucchero quantita' zucchero
     * @return nuova bevanda decorata
     */
    public Bevanda creaBevanda(Bevanda base, Bevanda aggiunta, int zucchero) {
        BevandaFactory bf = new BevandaFactory();
        return bf.creaBevanda(base, aggiunta, zucchero);
    }

    /**
     * Genera un report dei consumi mensili raggruppati per bevanda.
     *
     * @return stringa con il report
     */
    public String reportConsumi() {
        Map<String, Map<String, Double>> raggruppati = new HashMap<>();

        for (Map.Entry<String, Double> entry : consumi.entrySet()) {
            String key = entry.getKey();
            double quantita = entry.getValue();

            String[] parts = key.split("_", 2);
            if (parts.length < 2) continue;
            String meseAnno = parts[0];
            String bevanda = parts[1];

            raggruppati
                .computeIfAbsent(meseAnno, k -> new HashMap<>())
                .merge(bevanda, quantita, Double::sum);
        }

        StringBuilder sb = new StringBuilder("REPORT CONSUMI MENSILI:\n\n");
        for (String mese : raggruppati.keySet()) {
            sb.append(mese).append(":\n");
            for (Map.Entry<String, Double> entry : raggruppati.get(mese).entrySet()) {
                sb.append("   ")
                  .append(entry.getKey())
                  .append(" ")
                  .append(String.format("%.2f", entry.getValue()))
                  .append(" L,\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Visualizza le scorte disponibili in formato leggibile.
     *
     * @return stringa con le scorte
     */
    public String visualizzaScorte() {
        StringBuilder sb = new StringBuilder("SCORTE DISPONIBILI:\n\n");
        for (String nome : scorte.keySet()) {
            JSONObject obj = scorte.getJSONObject(nome);
            sb.append("- ")
              .append(nome)
              .append(": ")
              .append(String.format("%.2f", obj.getDouble("Quantita")))
              .append(" L\n");
        }
        return sb.toString();
    }

    /**
     * Restituisce la lista delle bevande disponibili.
     *
     * @return lista di bevande
     */
    public List<Bevanda> getBevandeDisponibili() {
        return this.bevande;
    }

    /**
     * Restituisce una chiavetta dato l'ID.
     *
     * @param id identificativo chiavetta
     * @return chiavetta trovata, null se non esiste
     */
    public Chiavetta getChiavettaById(String id) {
        if (chiavette == null) return null;
        for (Chiavetta c : chiavette) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Restituisce la lista delle chiavette.
     *
     * @return lista chiavette
     */
    public List<Chiavetta> getChiavette() {
        return this.chiavette;
    }

    /**
     * Rimuove una chiavetta dal sistema.
     *
     * @param c chiavetta da rimuovere
     */
    public void rimuoviChiavetta(Chiavetta c) {
        this.chiavette.remove(c);
    }

    /** Getter scorte JSON */
    public JSONObject getScorte() {
        return scorte;
    }

    /** Setter scorte JSON */
    public void setScorte(JSONObject scorte) {
        this.scorte = scorte;
    }

    /** Salva le scorte su file */
    public void salvaScorte() {
        FileManager.salvaScorte(bevande, scorte);
    }

    /** Carica le scorte da file */
    private void caricaDaFile() {
        FileManager.caricaScorte(bevande, prezzi, scorte);
    }

    /** Salva i consumi su file */
    private void salvaConsumi() {
        FileManager.salvaConsumi(consumi);
    }

    /** Carica i consumi da file */
    private void caricaConsumi() {
        this.consumi = FileManager.caricaConsumi();
    }

    /** Carica le chiavette da file */
    private void caricaChiavette() {
        try {
            this.chiavette = ChiavetteManager.caricaChiavette();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Salva le chiavette su file */
    public void salvaChiavette() {
        try {
            ChiavetteManager.salvaChiavette(chiavette);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea una nuova chiavetta.
     *
     * @param id identificativo
     * @param saldoIniziale saldo iniziale
     * @return nuova chiavetta creata
     */
    public Chiavetta creaNuovaChiavetta(String id, double saldoIniziale) {
        try {
            return ChiavetteManager.creaNuovaChiavetta(chiavette, id, saldoIniziale);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
