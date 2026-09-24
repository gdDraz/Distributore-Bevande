CORSO DI PROGRAMMAZIONE III
PROGETTO ESAME

Traccia - Distributore Bevande
Si vuole simulare un distributore automatico di Bevande. Il distributore ha 6 
tipologie di bevande: caffè, thè, latte, camomilla, cioccolata e acqua calda. Per ogni
tipologia sono previste delle sottotipologie (e.g., caffè e latte, latte e caffè, caffè e 
cioccolato, thè e latte, . . .). L’utente può scegliere anche la quantità di zucchero da 
erogare.
Scrivere un programma per la gestione del distributore. L’accesso deve avvenire 
sia in modalità amministratore che in modalità utente.
L’amministratore può effettuare le seguenti operazioni:
• periodicamente aggiungere bevande alla scorta. Il sistema controlla 
automaticamente se la bevanda è sotto scorta (minore di 1 litro) 
• definire il prezzo per ogni tipo di bevanda 
• fare un report sui consumi mensili delle diverse tipologie di bevande 
• aggiungere una nuova tipologia di bevanda partendo da quelle già esistenti 
(e.g., thè con limone) 
L’utente può effettuare le seguenti operazioni:
• scegliere, prelevare e pagare una bevanda. Il pagamento può avvenire 
secondo le modalità: contanti (5, 10, 20, 50 centesimi, 1 e 2 euro), chiavetta 
ricaricabile o carta di credito 
• ricaricare una chiavetta inserendo contanti (5,10,20,50 euro) 
La password per l’accesso alla modalità admin è “admin123” ed è hardcodata

---

1) DIAGRAMMA DELLE CLASSI

---

1.1) Focus package Bevande
(nel caso di poca leggiblità si consiglia il file diagrammi/ClassDiagramFinal.png)

---

2) Design pattern
Nello sviluppo del codice sono stati usati i seguenti design pattern:
•
Factory Pattern
•
Singleton
•
Strategy
•
Decorator
•
Facade

---

2.1) Factory Pattern
Il Factory Pattern viene usato per creare le sottotipologie di Bevanda scelta a 
runtime, senza mostrare al client la logica di instanzazione.
public class BevandaFactory {
    public Bevanda creaBevanda(Bevanda base, Bevanda aggiunta, int zucchero) {
        Bevanda result = base;
        if (aggiunta != null && !aggiunta.getClass().equals(base.getClass())) {
            switch (aggiunta.getName())
            {
            case "Caffe":
            {
            result=new CaffeDecorator(result);
            break;
            }
            case "Latte":
            {
            result=new LatteDecorator(result);
            break;
            }
            case "Camomilla":
            {
            result=new CamomillaDecorator(result);
            break;
            }
            case "Cioccolata":
            {
            result=new CioccolataDecorator(result);
            break;
            }
            case "Limone":
            {
            result=new LimoneDecorator(result);
            break;
            }
            case "The":
            {
            result=new TheDecorator(result);
            break;
            }
            default:
            throw new IllegalArgumentException("Bevanda sconosciuta 
"+aggiunta.getName());
            }
        }
        if (zucchero > 0) {
            result = new ZuccheroDecorator(result, zucchero);
        }
        return result;
    }

---

public class Distributore {
public Bevanda creaBevanda(Bevanda base,Bevanda aggiunta,int zucchero)
    
{
    
BevandaFactory bf=new BevandaFactory();
    
return bf.creaBevanda(base, aggiunta, zucchero);
    
}
}
private void preparaBevanda() {
        ..
        bevanda = distributore.creaBevanda(base, aggiunta, zucchero);
  ..
}

---

2.2) Singleton
Il Singleton viene usato per creare una singola istanza di Distributore a cui tutte le 
classi accedono.
public class Distributore {
    private static Distributore instance;
    ..
    private Distributore() {
    
bevande = new ArrayList<>();
    
prezzi = new HashMap<>();
    
scorte = new JSONObject();
    
consumi=new HashMap<>();
       inizializzaScorte();
    }
    public static Distributore getInstance() {
        if (instance == null) {
            instance = new Distributore();
        }
        return instance;
    }

---

2.3) Strategy
Strategy viene usato per la scelta del metodo di pagamento. Questo permette la 
scelta a runtime del metodo tra carta,contanti e chiavetta.
public interface PaymentStrategy {
boolean paga(double importo);
String getMetodo();
}
public class ContantiPayment implements PaymentStrategy {
    private double inserito;
    private double resto;
    public ContantiPayment(double inserito) {
        this.inserito = inserito;
    }
    @Override
    public boolean paga(double importo) {
        if (inserito >= importo) {
            resto = inserito - importo;
            return true;
        }
        return false;
    }
    public double getResto() {
        return resto;
    }
    @Override
    public String getMetodo() {
        return "Contanti";
    }
}
public class ChiavettaPayment implements PaymentStrategy {
    private Chiavetta chiavetta;
    public ChiavettaPayment(Chiavetta chiavetta) {
        this.chiavetta = chiavetta;
    }
    @Override
    public boolean paga(double importo) {
        return chiavetta.preleva(importo);
    }
    @Override
    public String getMetodo() {
        return "Chiavetta";
    }
}
public class CartaCreditoPayment implements PaymentStrategy {
    @Override
    public boolean paga(double importo) {
        System.out.println("Transazione in corso...");
        return true;
    }
    @Override
    public String getMetodo() {
        return "Carta di Credito";
    }

---

}
  PaymentStrategy pagamento=null;
        Chiavetta chiavetta=null;
        switch (metodo) {
        case "Chiavetta":
            String idChiavetta = JOptionPane.showInputDialog(this, "Inserisci ID 
Chiavetta:");
            idChiavetta="CHV"+idChiavetta;
            Chiavetta trovata = distributore.getChiavettaById(idChiavetta);
            if (trovata == null) {
                textArea.setText("Chiavetta non trovata!");
                return;
            }
            pagamento = new ChiavettaPayment(trovata);
            chiavetta = trovata; 
            break;
        case "Carta di Credito":
            if (!dialogoCartaCredito()) {
                textArea.setText("Pagamento con carta annullato o dati errati.");
                return;
            }
            pagamento = new CartaCreditoPayment();
            break;
        case "Contanti":
            PagamentoContantiDialog dialog = new PagamentoContantiDialog(this);
            Double totale = dialog.mostraDialogo();
            if (totale == null) {
                JOptionPane.showMessageDialog(this, "Pagamento annullato.");
                return;
            }
            pagamento = new ContantiPayment(totale.doubleValue());
            break;
        default:
            throw new IllegalArgumentException("Metodo pagamento non riconosciuto");
    }

---

2.4) Decorator
Il pattern Decorator è usato per “decorare” a runtime le Bevande. In questo modo 
laddove venga scelta un’aggiunta la Factory assegnerà l’aggiunta corretta.
package bevande;
public abstract class BevandaDecorator extends Bevanda {
private static final long serialVersionUID = 4000980280877744886L;
protected Bevanda bevanda;
protected Bevanda aggiunta;
public BevandaDecorator(Bevanda bevanda, Bevanda aggiunta) {
super(bevanda.name + " con " + aggiunta.name, bevanda.price +  
aggiunta.price/2);
        this.bevanda = bevanda;
        this.aggiunta=aggiunta;
    }
@Override
public String prepara() {
return this.bevanda.prepara()+" con "+this.aggiunta.name;
}
@Override
public String toString() {
    return name;
}
public Bevanda getBevanda()
{
return bevanda;
}
public Bevanda getAggiunta()
{
return aggiunta;
}
}
esempio di Decorator:
package bevande;
import sistema.Distributore;
public class CioccolataDecorator extends BevandaDecorator {
private static final long serialVersionUID = -7354474797044625331L;
/*
 * prendo il prezzo inizializzato nel distributore
 * @param Bevanda
 */
public CioccolataDecorator(Bevanda bevanda) {
super(bevanda, new 
Cioccolata(Distributore.getInstance().getPrezzo("Cioccolata")));
// TODO Auto-generated constructor stub
}
}

---

2.5) Facade
Facade fornisce in Distributore un’interfaccia semplificata ad un’insieme di 
interfacce del sottosistema come ad esempio la gestione delle chiavette.
public class Distributore {
...
public void salvaScorte() {
        FileManager.salvaScorte(bevande, scorte);
    }
    private void caricaDaFile() {
    
FileManager.caricaScorte(bevande, prezzi, scorte);
    }
    private void salvaConsumi() {
        FileManager.salvaConsumi(consumi);
    }
    private void caricaConsumi() {
        this.consumi=FileManager.caricaConsumi();
    }
    
   
    /*i seguenti metodi caricano e salvano le chiavette da file
     * @see Chiavetta
     * */
    private void caricaChiavette() {
    
try {
this.chiavette=ChiavetteManager.caricaChiavette();
} catch (IOException e) {
e.printStackTrace();
}
}
    public void salvaChiavette() {
        try {
            ChiavetteManager.salvaChiavette(chiavette);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * @param String
     * @param double
     * @return Chiavetta
     * @see Chiavetta
     */
    public Chiavetta creaNuovaChiavetta(String id, double saldoIniziale) {
        try {
return ChiavetteManager.creaNuovaChiavetta(chiavette, id, 
saldoIniziale);
} catch (IOException e) {
e.printStackTrace();
return null;
}
}