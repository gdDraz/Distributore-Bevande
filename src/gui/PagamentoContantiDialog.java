package gui;
import javax.swing.*;
import java.awt.*;

/**
 * il dialog che viene aperto dall'UserFrame per pagare con i contanti
 * @see AdminFrame
 */
public class PagamentoContantiDialog extends JDialog {

	private static final long serialVersionUID = 8151010216991029894L;
	private double totale = 0.0;
    private boolean confermato = false;

    private JLabel lblTotale;
    private JComboBox<String> comboTagli;
    
    /**
     * Costruttore che inizializza la finestra e tutti i componenti grafici.
     */
    public PagamentoContantiDialog(JFrame parent) {
        super(parent, "Pagamento in contanti", true);
        setSize(320, 200);
        setLayout(new BorderLayout(8, 8));
        setLocationRelativeTo(parent);

        // Tagli disponibili 
        String[] tagli = new String[] {"0.05", "0.10", "0.20", "0.50", "1.00", "2.00"};

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(tagli);
        comboTagli = new JComboBox<String>(model);

        lblTotale = new JLabel("Totale inserito: 0.00 euro", SwingConstants.CENTER);

        JPanel panelBottoni = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton btnInserisci = new JButton("Inserisci");
        JButton btnConferma = new JButton("Conferma");
        JButton btnAnnulla = new JButton("Annulla");
        panelBottoni.add(btnInserisci);
        panelBottoni.add(btnConferma);
        panelBottoni.add(btnAnnulla);

        btnInserisci.addActionListener(e -> {
            String sel = (String) comboTagli.getSelectedItem();
            if (sel != null) {
                try {
                    double moneta = Double.parseDouble(sel.replace(',', '.'));
                    totale += moneta;
                    lblTotale.setText("Totale inserito: " + String.format("%.2f", totale) + " euro");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Valore non valido: " + sel);
                }
            }
        });

        btnConferma.addActionListener(e -> {
            confermato = true;
            setVisible(false);
        });

        btnAnnulla.addActionListener(e -> {
            confermato = false;
            totale = 0.0;
            setVisible(false);
        });

        add(comboTagli, BorderLayout.NORTH);
        add(lblTotale, BorderLayout.CENTER);
        add(panelBottoni, BorderLayout.SOUTH);
    }

    /**
     * Inserimento dei contanti
     * @return totale inserito
     */
    public Double mostraDialogo() {
        setVisible(true);
        return confermato ? Double.valueOf(totale) : null;
    }
}
