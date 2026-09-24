package sistema;

import org.json.*;

import bevande.Bevanda;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Classe utility per la gestione dei file di persistenza del distributore.
 * <p>
 * Si occupa di salvare e caricare le scorte di bevande e i consumi mensili
 * su file JSON e binari.
 * </p>
 */
public class FileManager {

    /** File JSON in cui salvare le scorte delle bevande. */
    public static final String FILE_SCORTE = "scorte.json";

    /** File binario in cui salvare i consumi mensili delle bevande. */
    public static final String FILE_CONSUMI = "consumi.bin";

    // ======================= SCORTE =======================

    /**
     * Carica le scorte delle bevande da file JSON e aggiorna le strutture interne.
     * Vengono aggiornati:
     * <ul>
     *     <li>Lista di bevande</li>
     *     <li>Mappa dei prezzi</li>
     *     <li>JSONObject delle scorte</li>
     * </ul>
     *
     * @param bevande lista di bevande da popolare
     * @param prezzi mappa dei prezzi da aggiornare
     * @param scorte oggetto JSON da aggiornare con quantita' e prezzi
     */
    public static void caricaScorte(List<Bevanda> bevande, Map<String, Double> prezzi, JSONObject scorte) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_SCORTE)));
            JSONObject root = new JSONObject(content);
            JSONArray array = root.getJSONArray("Bevande");

            bevande.clear();
            prezzi.clear();
            scorte.clear();

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String tipo = obj.getString("Tipo");
                double prezzo = obj.getDouble("Prezzo");
                double quantita = obj.getDouble("Quantita");

                prezzi.put(tipo, prezzo);

                Bevanda b = bevande.stream()
                        .filter(x -> x.getName().equalsIgnoreCase(tipo))
                        .findFirst()
                        .orElseGet(() -> {
                            try {
                                Class<?> clazz = Class.forName("bevande." + tipoToClasse(tipo));
                                Bevanda nuova = (Bevanda) clazz.getDeclaredConstructor(double.class).newInstance(prezzo);
                                bevande.add(nuova);
                                return nuova;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        });

                if (b != null && obj.has("Aggiunte")) {
                    JSONArray aggiunte = obj.getJSONArray("Aggiunte");
                    for (int j = 0; j < aggiunte.length(); j++) {
                        String aggiunta = aggiunte.getString(j);
                        if (!aggiunta.equalsIgnoreCase("Nessuna")) {
                            b.aggiungiNuovaAggiunta(aggiunta);
                        }
                    }
                }

                JSONObject copia = new JSONObject();
                copia.put("Prezzo", prezzo);
                copia.put("Quantita", quantita);
                if (obj.has("Aggiunte")) copia.put("Aggiunte", obj.getJSONArray("Aggiunte"));
                scorte.put(tipo, copia);
            }

        } catch (IOException e) {
            System.out.println("Errore nella lettura del file JSON: " + e.getMessage());
        }
    }

    /**
     * Salva le scorte delle bevande su file JSON.
     *
     * @param bevande lista di bevande da salvare
     * @param scorte oggetto JSON contenente quantita' e prezzi delle bevande
     */
    public static void salvaScorte(List<Bevanda> bevande, JSONObject scorte) {
        JSONArray bevandeArray = new JSONArray();

        for (Bevanda bevanda : bevande) {
            String nome = bevanda.getName();
            JSONObject info = scorte.optJSONObject(nome);
            if (info == null) continue;

            JSONObject obj = new JSONObject();
            obj.put("Tipo", nome);
            obj.put("Prezzo", info.optDouble("Prezzo", bevanda.getPrezzo()));
            obj.put("Quantita", info.optDouble("Quantita", 0));

            List<String> aggiunte = bevanda.getPossibiliaggiunte();
            if (aggiunte != null && !aggiunte.isEmpty()) {
                obj.put("Aggiunte", new JSONArray(aggiunte));
            } else {
                obj.put("Aggiunte", new JSONArray().put("Nessuna"));
            }

            bevandeArray.put(obj);
        }

        JSONObject root = new JSONObject();
        root.put("Bevande", bevandeArray);

        try (FileOutputStream fos = new FileOutputStream(FILE_SCORTE)) {
            fos.write(root.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ======================= CONSUMI =======================

    /**
     * Carica la mappa dei consumi mensili dal file binario.
     *
     * @return mappa contenente i consumi (chiave: "YYYY-MM_Bevanda", valore: litri)
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Double> caricaConsumi() {
        File f = new File(FILE_CONSUMI);
        if (!f.exists()) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (Map<String, Double>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Salva la mappa dei consumi mensili sul file binario.
     *
     * @param consumi mappa dei consumi da salvare
     */
    public static void salvaConsumi(Map<String, Double> consumi) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_CONSUMI))) {
            oos.writeObject(consumi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converte il nome di una bevanda nel nome della classe corrispondente,
     * rimuovendo eventuali spazi.
     *
     * @param tipo nome della bevanda
     * @return nome della classe corrispondente
     */
    private static String tipoToClasse(String tipo) {
        return tipo.replaceAll("\\s+", ""); // rimuove spazi
    }
}
