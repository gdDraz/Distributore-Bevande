package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import sistema.Distributore;
/**
 * il frame che viene aperto dall'AdminFrame per visualizzare i consumi mensili
 * @see AdminFrame
 */
public class ConsumiFrame extends JFrame {

	private static final long serialVersionUID = 281427442042414580L;
	private JPanel contentPane;
	private JTextArea textAreaOutput;
	/**
     * Costruttore che inizializza la finestra e tutti i componenti grafici.
     */
	public ConsumiFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		textAreaOutput = new JTextArea();
        scrollPane.setViewportView(textAreaOutput);
        textAreaOutput.setEditable(false);
        String report = Distributore.getInstance().reportConsumi();
        textAreaOutput.setText(report);
	}

}
