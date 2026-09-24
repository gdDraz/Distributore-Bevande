package gui;

import java.util.List;
import java.util.Comparator;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import pagamento.Chiavetta;
import sistema.Distributore;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Finestra per la gestione chiavette dell'admin
 */
public class AdminChiavetteFrame extends JFrame {

	private static final long serialVersionUID = -2976475550369426662L;
	private JPanel contentPane;
	Distributore distributore=Distributore.getInstance();
	
	/**
     * Costruttore che inizializza la finestra e tutti i componenti grafici.
     */
	public AdminChiavetteFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		JButton btnEliminaChiavette = new JButton("Elimina Chiavetta");
		btnEliminaChiavette.setBounds(45, 30, 150, 30);
		contentPane.add(btnEliminaChiavette);
		
		JComboBox<String> comboBoxChiavette = new JComboBox<String>();
		comboBoxChiavette.setBounds(250, 30, 150, 30);
		contentPane.add(comboBoxChiavette);
		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 116, 370, 124);
        getContentPane().add(scrollPane);
        JTextArea textAreaOutput = new JTextArea();
        scrollPane.setViewportView(textAreaOutput);
        textAreaOutput.setEditable(false);
        aggiornaTextArea(textAreaOutput,distributore.getChiavette());
        aggiornaCombo(comboBoxChiavette,distributore.getChiavette());
        btnEliminaChiavette.addActionListener(e->{
        	String selected=(String)comboBoxChiavette.getSelectedItem();
        	distributore.rimuoviChiavetta(distributore.getChiavettaById(selected));
        	this.aggiornaCombo(comboBoxChiavette,distributore.getChiavette());
        	this.aggiornaTextArea(textAreaOutput, this.distributore.getChiavette());
        	distributore.salvaChiavette();
        });
        	 
	}
	
	/**
     * Recupera le chiavette presenti e le ordina nella textArea
     * @param textarea la textarea da aggiornare
     * @param chiavette lista delle chiavette presenti
     */
	private void aggiornaTextArea(JTextArea textarea,List<Chiavetta> chiavette)
	{
		textarea.setText("");
	    chiavette.sort(Comparator.comparing(Chiavetta::getId));
		for(Chiavetta c:chiavette)
		{
			textarea.append(c.toString()+"\n");
		}
	}
	
	/**
     * Recupera le chiavette presenti e le ordina nel comboBox
     * @param comboBoxChiavette il comboBox da aggiornare
     * @param chiavette lista delle chiavette presenti
     */
	private void aggiornaCombo(JComboBox<String> comboBoxChiavette,List<Chiavetta> chiavette)
	{
		comboBoxChiavette.removeAllItems();
	    chiavette.sort(Comparator.comparing(Chiavetta::getId));
		for(Chiavetta c:distributore.getChiavette())
        {
        	comboBoxChiavette.addItem(c.getId());
        }
	}
}
