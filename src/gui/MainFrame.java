package gui;

import javax.swing.*;
import sistema.Distributore;

/**
 * Finestra principale dell'applicazione che permette di selezionare la modalita' di utilizzo:
 * utente o amministratore.
 * Contiene due pulsanti:
 * <ul>
 *   <li>Modalita' Utente: apre la finestra {@link UserFrame}</li>
 *   <li>Modalita' Admin: richiede la password e apre la finestra {@link AdminFrame}</li>
 * </ul>

 * @author Francesco Barbato
 * @version 2.0
 */
public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore che inizializza la finestra principale con i pulsanti di selezione modalita'.
     */
    public MainFrame() {
        setTitle("Seleziona Modalita'");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnUtente = new JButton("Modalita' Utente");
        btnUtente.setBounds(60, 20, 160, 30);
        btnUtente.addActionListener(e -> {
            new UserFrame().setVisible(true);
            dispose();
        });
        add(btnUtente);

        JButton btnAdmin = new JButton("Modalita' Admin");
        btnAdmin.setBounds(60, 60, 160, 30);
        btnAdmin.addActionListener(e -> {
            String password = JOptionPane.showInputDialog(this, "Inserisci password amministratore:");
            if ((password != null && password.equals("admin123"))) {
                System.out.println("Accesso Admin OK");
                AdminFrame admin = new AdminFrame();
                admin.setVisible(true);
                admin.checkScorte();
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Password errata");
            }
        });
        add(btnAdmin);
    }

    /**
     * Metodo main che avvia l'applicazione.
     * Inizializza il distributore e mostra la finestra principale.
     * 
     * @param args Argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Distributore.getInstance();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
