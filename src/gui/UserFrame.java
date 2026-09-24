package gui;

import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import bevande.*;
import pagamento.*;
import sistema.Distributore;

/**
 * Finestra per la modalita' utente del distributore automatico.
 * Permette di:
 * <ul>
 *   <li>Selezionare una bevanda e un'eventuale aggiunta</li>
 *   <li>Selezionare il numero di zuccheri</li>
 *   <li>Scegliere il metodo di pagamento (Contanti, Chiavetta, Carta di Credito)</li>
 *   <li>Preparare la bevanda e visualizzare il risultato nella text area</li>
 *   <li>Ricaricare o creare nuove chiavette</li>
 * </ul>
 */
public class UserFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> comboBevanda;
    private JComboBox<String> comboAggiunta;
    private JComboBox<String> comboPagamento;
    private JSlider sliderZucchero;
    private JTextArea textArea;
    private JButton btnPrepara, btnRicarica;
    private JTextArea txtrListinoPrezziCaff;
    Distributore distributore=Distributore.getInstance();
    
    /**
     * Costruttore che inizializza la finestra e tutti i componenti grafici.
     */
    public UserFrame() {
        setTitle("Distributore Automatico");
        setBounds(100, 100, 600, 530);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblTipo = new JLabel("Bevanda:");
        lblTipo.setBounds(30, 20, 80, 20);
        getContentPane().add(lblTipo);
        comboBevanda = new JComboBox<>();
        for(Bevanda b:distributore.getBevandeDisponibili())
        	comboBevanda.addItem(b.getName());
        comboBevanda.removeItem("Zucchero");
        comboBevanda.removeItem("Limone");
        comboBevanda.setBounds(120, 20, 150, 20);
        getContentPane().add(comboBevanda);
        JLabel lblAggiunta = new JLabel("Aggiunta:");
        lblAggiunta.setBounds(30, 50, 80, 20);
        getContentPane().add(lblAggiunta);

        comboAggiunta = new JComboBox<>();
        comboAggiunta.setBounds(120, 50, 150, 20);
        getContentPane().add(comboAggiunta);

        JLabel lblZucchero = new JLabel("Zucchero:");
        lblZucchero.setBounds(30, 80, 80, 20);
        getContentPane().add(lblZucchero);

        sliderZucchero = new JSlider(0, 5, 0);
        sliderZucchero.setPaintTicks(true);
        sliderZucchero.setPaintLabels(true);
        sliderZucchero.setMajorTickSpacing(1);
        sliderZucchero.setBounds(120, 80, 200, 50);
        getContentPane().add(sliderZucchero);

        JLabel lblPagamento = new JLabel("Pagamento:");
        lblPagamento.setBounds(30, 140, 80, 20);
        getContentPane().add(lblPagamento);

        comboPagamento = new JComboBox<String>();
        comboPagamento.addItem("Contanti");
        comboPagamento.addItem("Chiavetta");
        comboPagamento.addItem("Carta di Credito");
        comboPagamento.setBounds(120, 140, 150, 20);
        getContentPane().add(comboPagamento);

        btnPrepara = new JButton("Prepara");
        btnPrepara.setBounds(30, 180, 100, 30);
        getContentPane().add(btnPrepara);

        btnRicarica = new JButton("Ricarica Chiavetta");
        btnRicarica.setBounds(150, 180, 170, 30);
        getContentPane().add(btnRicarica);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(30, 342, 520, 132);
        getContentPane().add(scrollPane);
        JButton btnCreaChiavetta = new JButton("Crea Chiavetta");
        btnCreaChiavetta.setBounds(150, 232, 170, 30);
        getContentPane().add(btnCreaChiavetta);
        //listino prezzi 
        txtrListinoPrezziCaff = new JTextArea();
        for(Bevanda b:distributore.getBevandeDisponibili())
        	//Zucchero e Limone non possono essere selezionate come bevande singole
        	if(!(b instanceof Zucchero)&&!(b instanceof Limone))
        		txtrListinoPrezziCaff.append(b.getName()+" "+b.getPrezzo()+"\n");
        txtrListinoPrezziCaff.append("AGGIUNTE\n");
        for(Bevanda b:distributore.getBevandeDisponibili())
        	//i prezzi delle aggiunte sono la meta' del prezzo base tranne che per zucchero e limone
        	if(!(b instanceof Zucchero)&&!(b instanceof Limone))
        		txtrListinoPrezziCaff.append(b.getName()+" "+b.getPrezzo()/2+"\n");
        	else
        		txtrListinoPrezziCaff.append(b.getName()+" "+b.getPrezzo()+"\n");
        txtrListinoPrezziCaff.setBounds(358, 16, 192, 314);
        getContentPane().add(txtrListinoPrezziCaff);
        
        

        comboBevanda.addItemListener(e -> aggiornaAggiunte());
        btnPrepara.addActionListener(e -> preparaBevanda());
        btnRicarica.addActionListener(e -> mostraDialogRicarica());
        btnCreaChiavetta.addActionListener(e -> mostraDialogNuovaChiavetta());
        aggiornaAggiunte();
    }
 

    /**
     * Aggiorna la JComboBox delle aggiunte in base alla bevanda selezionata.
     */
    private void aggiornaAggiunte() {
        String selezionata = (String)comboBevanda.getSelectedItem();
        comboAggiunta.removeAllItems();
        //recupero le aggiunte per la bevanda selezionata
        Iterator<Bevanda> it=distributore.getBevandeDisponibili().iterator();
        Bevanda b=null;
        while(it.hasNext())
        {
        	Bevanda tmp=it.next();
        	if(tmp.getName().equalsIgnoreCase(selezionata))
        		{
        			b=tmp;
        			break;
        		}
        }
        List<String> l=b.getPossibiliaggiunte();
        for(String i:l)
        {
        	comboAggiunta.addItem(i);
        }
    }
    
    /**
     * Prepara la bevanda selezionata dall'utente, gestisce il pagamento
     * e aggiorna l'area di testo con il risultato.
     */
    private void preparaBevanda() {
        String first = (String) comboBevanda.getSelectedItem();//base
        String second = (String) comboAggiunta.getSelectedItem();//aggiunta
        int zucchero = sliderZucchero.getValue();
        String metodo = (String) comboPagamento.getSelectedItem();
        Bevanda base=null,aggiunta=null,bevanda;
        Iterator<Bevanda> it=distributore.getBevandeDisponibili().iterator();
        while(it.hasNext())
        {
        	Bevanda b=it.next();
        	if(b.getName().equalsIgnoreCase(first))
        	{
        		base=b;
        		break;
        	}
        }
        if(!second.equals("Nessuna"))
        	{
        		it=distributore.getBevandeDisponibili().iterator();
        		while(it.hasNext())
                {
                	Bevanda b=it.next();
                	if(b.getName().equalsIgnoreCase(second))
                	{
                		aggiunta=b;
                		break;
                	}
                }
        	}
        if (distributore.sottoScorta(base)) {
            textArea.setText("Scorta insufficiente per: " + base);
            return;
        }

        if (aggiunta != null && distributore.sottoScorta(aggiunta)) {
            textArea.setText("Scorta insufficiente per: " + aggiunta);
            return;
        }
        bevanda = distributore.creaBevanda(base, aggiunta, zucchero);
        PaymentStrategy pagamento=null;
        StringBuilder output = new StringBuilder();
        Chiavetta chiavetta=null;
        switch (metodo) {
        case "Chiavetta":
            String idChiavetta = JOptionPane.showInputDialog(this, "Inserisci ID Chiavetta:");
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
        
        
        if (pagamento.paga(bevanda.getPrezzo())) {
            output.append(bevanda.toString())
                  .append("\n")
                  .append(bevanda.prepara())
                  .append("\nPagato con: ").append(pagamento.getMetodo());

            if (pagamento instanceof ContantiPayment) {
                double resto = ((ContantiPayment) pagamento).getResto();
                output.append("\nResto: ").append(String.format("%.2f", resto)).append(" euro");
            }
            if (pagamento instanceof ChiavettaPayment) {
                output.append(chiavetta.toString());
                distributore.salvaChiavette();
            }
        } else {
            output.append("Pagamento non riuscito.");
        }
        distributore.eroga(bevanda);
        textArea.setText(output.toString());
    }

    /**
     * Mostra un dialog per ricaricare una chiavetta esistente.
     */
    private void mostraDialogRicarica() {
        String id = JOptionPane.
        		showInputDialog(this, "Inserisci ID Chiavetta:\nes 001,002,..");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID non valido.");
            return;
        }
        id="CHV"+id;
        Chiavetta c = distributore.getChiavettaById(id.trim());

        if (c == null) {
            JOptionPane.showMessageDialog(this, "Chiavetta con ID " + id + " non trovata.");
            return;
        }

        String[] opzioni = {"5", "10", "20", "50"};
        String scelta = (String) JOptionPane.showInputDialog(
            this,
            "Inserisci banconota per la ricarica:",
            "Ricarica Chiavetta",
            JOptionPane.PLAIN_MESSAGE,
            null,
            opzioni,
            "5"
        );

        if (scelta != null) {
            try {
                double importo = Double.parseDouble(scelta);
                c.ricarica(importo);
                distributore.salvaChiavette();
                JOptionPane.showMessageDialog(this, 
                    "Chiavetta " + id + " ricaricata con " + importo + " euro.\nSaldo attuale: " +
                c.getSaldo() + " euro");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Importo non valido.");
            }
        }
    }

    /**
     * Mostra un dialog per l'inserimento dei dati della carta di credito.
     * 
     * @return {@code true} se i dati sono validi e l'utente ha confermato, 
     * {@code false} altrimenti
     */
    private boolean dialogoCartaCredito() {
        JTextField numeroCarta = new JTextField();
        JTextField scadenza = new JTextField();
        JTextField cvv = new JTextField();

        Object[] message = {
            "Numero Carta (16 cifre):", numeroCarta,
            "Data Scadenza (MM/AA):", scadenza,
            "CVV (3 cifre):", cvv
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Inserisci dati Carta di Credito", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String num = numeroCarta.getText().trim();
            String scad = scadenza.getText().trim();
            String cv = cvv.getText().trim();

            return num.matches("\\d{16}") && cv.matches("\\d{3}") && !scad.isEmpty();
        } else {
            return false;
        }
    }
    
    /**
     * Mostra un dialog per creare una nuova chiavetta con saldo iniziale.
     */
    private void mostraDialogNuovaChiavetta() {
        String id = Chiavetta.generaIDLibero(distributore.getChiavette());
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID non valido.");
            return;
        }
        if(distributore.getChiavettaById(id)!=null)
        {
            JOptionPane.showMessageDialog(this, "Chiavetta con ID " + id + "  esistente!");
            return;
        }
        String[] opzioni = {"0","5", "10", "20", "50"};
        String saldoStr = (String) JOptionPane.showInputDialog(
            this,
            "Inserisci banconota per il saldo iniziale:",
            "Ricarica Chiavetta",
            JOptionPane.PLAIN_MESSAGE,
            null,
            opzioni,
            "5"
        );

        try {
            double saldo = Double.parseDouble(saldoStr);
            Chiavetta nuova=null;
			nuova=distributore.creaNuovaChiavetta(id, saldo);


            if (nuova == null) {
                JOptionPane.showMessageDialog(this, "Chiavetta con ID " + id + " esistente!");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Chiavetta creata!\nID: " + id + "\nSaldo: " + saldo + " euro");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Saldo non valido.");
        }
    }

}
