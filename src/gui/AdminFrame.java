package gui;

import javax.swing.*;
import bevande.*;
import sistema.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * Finestra per la modalita' amministratore del distributore automatico.
 * Permette di:
 * <ul>
 *   <li>Visualizzare e aggiornare le scorte delle bevande</li>
 *   <li>Impostare o modificare il prezzo delle bevande</li>
 *   <li>Visualizzare il report dei consumi mensili</li>
 *   <li>Visualizzare, aggiungere o rimuovere aggiunte per le bevande</li>
 *   <li>Gestire le chiavette elettroniche</li>
 * </ul>
 */
@SuppressWarnings("unused")
public class AdminFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> comboBevande;
    private JTextField txtNuovoPrezzo;
    private JTextArea textAreaOutput;
    private JTextField quantita;
    Distributore distributore=Distributore.getInstance();
    List<Bevanda> bevande=distributore.getBevandeDisponibili();
    /**
     * Costruttore che inizializza la finestra e tutti i componenti grafici.
     */
    public AdminFrame() {
        setTitle("Modalita' Amministratore");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        JLabel lblBevanda = new JLabel("Bevanda:");
        lblBevanda.setBounds(30, 20, 100, 20);
        getContentPane().add(lblBevanda);
        comboBevande = new JComboBox<>();
        for(Bevanda b:bevande)
        {
        	comboBevande.addItem(b.getName());
        }
        
        comboBevande.setBounds(140, 20, 200, 25);
        getContentPane().add(comboBevande);

        JButton btnAggiungiScorta = new JButton("Aggiungi Scorta");
        btnAggiungiScorta.setBounds(64, 56, 150, 30);
        getContentPane().add(btnAggiungiScorta);

        JLabel lblPrezzo = new JLabel("Nuovo Prezzo (euro):");
        lblPrezzo.setBounds(350, 110, 120, 20);
        getContentPane().add(lblPrezzo);

        txtNuovoPrezzo = new JTextField();
        txtNuovoPrezzo.setBounds(254, 108, 86, 25);
        getContentPane().add(txtNuovoPrezzo);

        JButton btnImpostaPrezzo = new JButton("Imposta Prezzo");
        btnImpostaPrezzo.setBounds(64, 105, 150, 30);
        getContentPane().add(btnImpostaPrezzo);

        JButton btnReport = new JButton("Report Consumi");
        btnReport.setBounds(10, 160, 150, 50);
        getContentPane().add(btnReport);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 349, 232, 90);
        getContentPane().add(scrollPane);
        textAreaOutput = new JTextArea();
        scrollPane.setViewportView(textAreaOutput);
        textAreaOutput.setEditable(false);
        
        JButton btnVisualizzaScorte = new JButton("Visualizza Scorte");
        btnVisualizzaScorte.setBounds(170, 160, 150, 50);
        getContentPane().add(btnVisualizzaScorte);
        
        quantita = new JTextField();
        quantita.setBounds(254, 56, 86, 25);
        getContentPane().add(quantita);
        quantita.setColumns(10);
        
        JButton btnVisualizzaAggiunte = new JButton("Visualizza Aggiunte");
        btnVisualizzaAggiunte.setBounds(324, 160, 150, 50);
        getContentPane().add(btnVisualizzaAggiunte);
        JButton btnAggiungiAggiunta = new JButton("Aggiungi Aggiunta");
        btnAggiungiAggiunta.setBounds(34, 235, 180, 30);
        getContentPane().add(btnAggiungiAggiunta);

        JComboBox<String> comboBoxNuoveAggiunte = new JComboBox<String>();
        comboBoxNuoveAggiunte.setBounds(242, 238, 200, 25);
        getContentPane().add(comboBoxNuoveAggiunte);
        
        JLabel labelLitri = new JLabel("L");
        labelLitri.setBounds(350, 56, 120, 20);
        getContentPane().add(labelLitri);
        
        JButton btnEliminaAggiunte = new JButton("Elimina Aggiunta");
        btnEliminaAggiunte.setBounds(34, 293, 180, 30);
        getContentPane().add(btnEliminaAggiunte);
        
        JComboBox<String> comboBoxElimina = new JComboBox<String>();
        comboBoxElimina.setBounds(242, 293, 200, 25);
        getContentPane().add(comboBoxElimina);
        //Aggiorno le combo box appena entro in modalita' admin
        aggiornaEliminaAggiunte(comboBoxElimina, (String)comboBevande.getSelectedItem());
        aggiornaNuoveAggiunte(comboBoxNuoveAggiunte,(String) comboBevande.getSelectedItem());
        
        JButton btnChiavette = new JButton("Gestione Chiavette");
        btnChiavette.setBounds(274, 351, 180, 30);
        getContentPane().add(btnChiavette);
   

        // Azioni
      
        //aggiungi scorta
        btnAggiungiScorta.addActionListener(e -> {
            String first = (String) comboBevande.getSelectedItem();
            Bevanda selezionata=null;
            Iterator<Bevanda> it=distributore.getBevandeDisponibili().iterator();
            while(it.hasNext())
            {
            	selezionata=it.next();
            	if(selezionata.getName().equalsIgnoreCase(first))
            		break;
            }
            double parsedQuantita=Double.parseDouble(quantita.getText());
            distributore.aggiungiScorta(selezionata, parsedQuantita);
            textAreaOutput.setText("Aggiunta "+parsedQuantita +"L di: " + selezionata.getName());
        });
        /*i due metodi qui sotto aggiornano i combo nuova aggiunta ed elimina aggiunta ogni volta
        che viene selezionata una nuova bevanda
        */
        comboBevande.addActionListener(e -> {
            aggiornaNuoveAggiunte(comboBoxNuoveAggiunte, (String) comboBevande.getSelectedItem());
        });
        comboBevande.addActionListener(e->{
        	aggiornaEliminaAggiunte(comboBoxElimina, (String)comboBevande.getSelectedItem());
        });
        //visualizza scorte
        btnVisualizzaScorte.addActionListener(e -> {
        	textAreaOutput.setText(distributore.visualizzaScorte());
        	
        });
        //imposta prezzo
        btnImpostaPrezzo.addActionListener(e -> {
            try {
                double prezzo = Double.parseDouble(txtNuovoPrezzo.getText());
                String first = (String) comboBevande.getSelectedItem();
                Bevanda selezionata=null;
                Iterator<Bevanda> it=distributore.getBevandeDisponibili().iterator();
                while(it.hasNext())
                {
                	selezionata=it.next();
                	if(selezionata.getName().equalsIgnoreCase(first))
                		break;
                }
                distributore.impostaPrezzo(selezionata, prezzo);
                textAreaOutput.setText("Prezzo aggiornato: " + selezionata.getName() + " -> " + prezzo + " euro");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Prezzo non valido.");
            }
        });
        //report mensile
        btnReport.addActionListener(e -> {
            new ConsumiFrame().setVisible(true);
        });
        //visualizza le aggiunte per bevanda
        btnVisualizzaAggiunte.addActionListener(e->{
        	String first=(String)comboBevande.getSelectedItem();
        	Iterator<Bevanda> it=distributore.getBevandeDisponibili().iterator();
        	Bevanda selezionata=null;
        	while(it.hasNext())
        	{
        		Bevanda b=it.next();
        		if(b.getName().equalsIgnoreCase(first))
        		{
        			selezionata=b;
        			break;
        		}
        	}
        	StringBuilder output=new StringBuilder();
        	for(String s:selezionata.getPossibiliaggiunte())
        	{
        		if(!s.equalsIgnoreCase("nessuna"))
        			output.append(s+"\n");
        	}
        	textAreaOutput.setText(output.toString());
        });
        //aggiungi una nuova aggiunta per bevanda (non possibile per zucchero, limone e acqua calda
        btnAggiungiAggiunta.addActionListener(e -> {
            String base = (String) comboBevande.getSelectedItem();
            String aggiunta = (String) comboBoxNuoveAggiunte.getSelectedItem();
            //check fatto in anticipo in modo da evitare di chiamare i metodi successivi
            if(base.equalsIgnoreCase("zucchero")||base.equalsIgnoreCase("limone")||
        			base.equalsIgnoreCase("acqua calda"))
        	{
            	//textAreaOutput.setText("Non puoi aggiungere a "+base);
        		return;
        	}
            if (base != null && aggiunta != null) {
            	Iterator<Bevanda> it=distributore.getBevandeDisponibili().iterator();
            	Bevanda bev=null;
            	while(it.hasNext())
            	{
            		Bevanda b=it.next();
            		if(b.getName().equalsIgnoreCase(base))
            		{
            			bev=b;
            			break;
            		}
            	}
                bev.aggiungiNuovaAggiunta(aggiunta);
               	textAreaOutput.setText("Aggiunta \"" + aggiunta + "\" a \"" + base + "\" con successo.");
               	// ricarico le opzioni e salvo
               	aggiornaNuoveAggiunte(comboBoxNuoveAggiunte, base); 
               	aggiornaEliminaAggiunte(comboBoxElimina, (String)comboBevande.getSelectedItem());
               	distributore.salvaScorte();
                	
                }
            
        });
        //elimina una aggiunta per bevanda (non possibile per zucchero, limone e acqua calda
        btnEliminaAggiunte.addActionListener(e->{
        	//logica come aggiungiAggiunta
        	String base = (String) comboBevande.getSelectedItem();
        	String aggiunta = (String) comboBoxElimina.getSelectedItem();
        	if(base.equalsIgnoreCase("zucchero")||base.equalsIgnoreCase("limone")||
        			base.equalsIgnoreCase("acqua calda"))
        	{
        		//textAreaOutput.setText("Non puoi eliminare da "+base);
        		return;
        	}
        	if(base!=null&&aggiunta!=null&&!aggiunta.equalsIgnoreCase("nessuna"))
        	{
        		Iterator<Bevanda> it=distributore.getBevandeDisponibili().iterator();
            	Bevanda bev=null;
            	while(it.hasNext())
            	{
            		Bevanda b=it.next();
            		if(b.getName().equalsIgnoreCase(base))
            		{
            			bev=b;
            			break;
            		}
            	}
        		bev.eliminaAggiunta(aggiunta);
            	textAreaOutput.setText("Aggiunta \"" + aggiunta + "\" eliminata da \"" + base + "\" con successo.");
        		aggiornaNuoveAggiunte(comboBoxNuoveAggiunte, base); 
            	aggiornaEliminaAggiunte(comboBoxElimina, (String)comboBevande.getSelectedItem());
            	distributore.salvaScorte();
        	}
        	
        });
        btnChiavette.addActionListener(e->{
        	new AdminChiavetteFrame().setVisible(true);
        });
       

    }
    
    /**
     * Recupera la lista delle possibili nuove aggiunte per una bevanda specifica,
     * escludendo quelle gia' presenti e le bevande non aggiungibili.
     * 
     * @param base il nome della bevanda di riferimento
     * @return lista dei nomi delle nuove aggiunte disponibili
     */
    private List<String> getNuoveAggiunte(String base)
    {
    	List<Bevanda> tutte=distributore.getBevandeDisponibili();
    	List<String> nomiTutte=new ArrayList<>();
    	for(Bevanda t:tutte)
    		nomiTutte.add(t.getName());
    	Iterator<Bevanda> it=distributore.getBevandeDisponibili().iterator();
    	Bevanda bev=null;
    	while(it.hasNext())
    	{
    		Bevanda b=it.next();
    		if(b.getName().equalsIgnoreCase(base))
    		{
    			bev=b;
    			break;
    		}
    	}

    	List<String> possibili=bev.getPossibiliaggiunte();
    	List<String> daAggiungere=new ArrayList<>(nomiTutte);
    	daAggiungere.removeAll(possibili);
    	daAggiungere.remove("Zucchero");
    	daAggiungere.remove("Acqua Calda");
    	daAggiungere.remove(base);
    	return daAggiungere;
    }
   
    
    /**
     * Aggiorna una JComboBox con le nuove aggiunte disponibili per una bevanda.
     * 
     * @param combo la JComboBox da aggiornare
     * @param base il nome della bevanda selezionata
     */
    private void aggiornaNuoveAggiunte(JComboBox<String> combo, String base) {
        combo.removeAllItems();
    	if(base.equals("Zucchero")||base.equals("Limone")||base.equals("Acqua Calda"))
    		return;
        List<String> nuoveAggiunte = getNuoveAggiunte(base);
        for (String s : nuoveAggiunte) {
            combo.addItem(s);
        }
    }
    
    /**
     * Aggiorna una JComboBox con le aggiunte gia' presenti per una bevanda.
     * 
     * @param combo la JComboBox da aggiornare
     * @param base il nome della bevanda selezionata
     */
    private void aggiornaEliminaAggiunte(JComboBox<String> combo, String base) {
        combo.removeAllItems();

    	if(base.equals("Zucchero")||base.equals("Limone")||base.equals("Acqua Calda"))
    		return;
        List<String> AggiuntePresenti = getAggiuntePresenti(base);
        for (String s : AggiuntePresenti) {
        	if(s.equalsIgnoreCase("nessuna"))
        		continue;
            combo.addItem(s);
        }
    }
    
    
    /**
     * Restituisce la lista delle aggiunte presenti per una bevanda.
     * 
     * @param base il nome della bevanda di riferimento
     * @return lista delle aggiunte gia' presenti
     */
    private List<String> getAggiuntePresenti(String base)
    {
    	Iterator<Bevanda> it=distributore.getBevandeDisponibili().iterator();
    	Bevanda bev=null;
    	while(it.hasNext())
    	{
    		Bevanda b=it.next();
    		if(b.getName().equalsIgnoreCase(base))
    		{
    			bev=b;
    			break;
    		}
    	}
    	return bev.getPossibiliaggiunte();
    }
    
    /**
     * Controlla le scorte di tutte le bevande e mostra un messaggio
     * se qualche bevanda risulta sotto la soglia minima.
     */
    public void checkScorte()
    {
    	for (Bevanda b : bevande) {
            double residuo = distributore.getScorta(b);
            if (residuo < Distributore.SOGLIA) {
            	JOptionPane.showMessageDialog(this, b.getName()+" sotto scorta");
            }
        }
    }
}
