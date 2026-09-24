package sistema;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import pagamento.Chiavetta;

/**
 * Gestisce le operazioni di persistenza delle {@link Chiavetta} su file JSON.
 * <p>
 * Include metodi per salvare, caricare e creare nuove chiavette, mantenendo
 * aggiornato l'ID massimo generato.
 * </p>
 */
public class ChiavetteManager {

    /** Percorso del file JSON in cui salvare le chiavette. */
    public static final String FILE_CHIAVETTE = "chiavette.json";

    /**
     * Salva la lista di chiavette sul file JSON {@link #FILE_CHIAVETTE}.
     *
     * @param chiavette la lista di chiavette da salvare
     * @throws IOException se si verifica un errore di scrittura sul file
     */
    public static void salvaChiavette(List<Chiavetta> chiavette) throws IOException {
        JSONArray array = new JSONArray();
        for (Chiavetta c : chiavette) {
            JSONObject obj = new JSONObject();
            obj.put("id", c.getId());
            obj.put("saldo", c.getSaldo());
            array.put(obj);
        }
        JSONObject root = new JSONObject();
        root.put("Chiavette", array);

        try (FileWriter fw = new FileWriter(FILE_CHIAVETTE)) {
            fw.write(root.toString(4)); // 4 = indentazione
        }
    }

    /**
     * Carica le chiavette dal file JSON {@link #FILE_CHIAVETTE}.
     * <p>
     * Aggiorna anche l'ID massimo generato in {@link Chiavetta}.
     * </p>
     *
     * @return la lista delle chiavette caricate
     * @throws IOException se il file non esiste o non leggibile
     */
    public static List<Chiavetta> caricaChiavette() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(FILE_CHIAVETTE)));
        JSONObject root = new JSONObject(content);
        JSONArray array = root.getJSONArray("Chiavette");
        List<Chiavetta> chiavette = new ArrayList<>();

        int maxId = 0;
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Chiavetta c = new Chiavetta(obj.getString("id"), obj.getDouble("saldo"));
            chiavette.add(c);

            // estrai solo la parte numerica dopo "CHV"
            String idStr = c.getId().replace("CHV", "");
            try {
                int idNum = Integer.parseInt(idStr);
                if (idNum > maxId) maxId = idNum;
            } catch (NumberFormatException e) {
                // ignora se non numerico
            }
        }
        Chiavetta.setLastID(maxId); // imposta lastID all'ultimo ID massimo trovato
        return chiavette;
    }

    /**
     * Crea una nuova chiavetta con saldo iniziale e la aggiunge alla lista.
     * <p>
     * L'ID viene generato automaticamente tramite {@link Chiavetta#generaIDLibero}.
     * La lista di chiavette viene poi salvata su file.
     * </p>
     *
     * @param chiavette     lista di chiavette a cui aggiungere la nuova; se null, viene creata una nuova lista
     * @param id            parametro ignorato, l'ID viene generato automaticamente
     * @param saldoIniziale saldo iniziale della nuova chiavetta
     * @return la chiavetta appena creata
     * @throws IOException se si verifica un errore di scrittura sul file
     */
    public static Chiavetta creaNuovaChiavetta(List<Chiavetta> chiavette, String id, double saldoIniziale) throws IOException {
        if (chiavette == null) {
            chiavette = new ArrayList<>();
        }
        String idGenerato = Chiavetta.generaIDLibero(chiavette);
        Chiavetta nuova = new Chiavetta(idGenerato, saldoIniziale);
        chiavette.add(nuova);
        salvaChiavette(chiavette);
        return nuova;
    }
}
