package gui_system;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

public class RapDiscSearchCtrlPanel extends JPanel {

	private JTextField txtDenumire;
	private JTextField txtAn;
	private JTextField txtSemestru;
	
	private IntFilter anFilter, semestruFilter;
	private JPanel panel;
	
	public RapDiscSearchCtrlPanel() {
		setLayout(null);
		//setPreferredSize(new Dimension(397, 81));
		setBounds(26, 278, 370, 53);
		
		anFilter = new IntFilter();
		semestruFilter = new IntFilter();
		anFilter.setMaxLength(1);
		semestruFilter.setMaxLength(1);
		
		panel = new JPanel();
		panel.setBounds(14, 2, 342, 48);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblDenumire = new JLabel("Denumire");
		lblDenumire.setBounds(0, 3, 45, 14);
		panel.add(lblDenumire);
		
		txtDenumire = new JTextField();
		txtDenumire.setBounds(55, 0, 287, 20);
		panel.add(txtDenumire);
		txtDenumire.setHorizontalAlignment(SwingConstants.CENTER);
		txtDenumire.setColumns(10);
		
		JLabel lblAn = new JLabel("An");
		lblAn.setBounds(0, 31, 29, 14);
		panel.add(lblAn);
		
		txtAn = new JTextField();
		txtAn.setBounds(55, 28, 111, 20);
		panel.add(txtAn);
		txtAn.setHorizontalAlignment(SwingConstants.CENTER);
		txtAn.setColumns(10);
		
		JLabel lblSemestru = new JLabel("Semestru");
		lblSemestru.setBounds(175, 31, 46, 14);
		panel.add(lblSemestru);
		
		txtSemestru = new JTextField();
		txtSemestru.setBounds(231, 28, 111, 20);
		panel.add(txtSemestru);
		txtSemestru.setHorizontalAlignment(SwingConstants.CENTER);
		txtSemestru.setColumns(10);
		AbstractDocument semestruDoc = (AbstractDocument) txtSemestru.getDocument();
		
		AbstractDocument anDoc = (AbstractDocument) txtAn.getDocument();
		anDoc.setDocumentFilter(anFilter);
		semestruDoc.setDocumentFilter(semestruFilter);
	}
	
	public String getDenumire() {
		return txtDenumire.getText();
	}
	
	public int getAn() {
		if (txtAn.getText().isEmpty())
			return -1;
		else
			return Integer.parseInt(txtAn.getText());
	}
	
	public int getSemestru() {
		if (txtSemestru.getText().isEmpty())
			return -1;
		else
			return Integer.parseInt(txtSemestru.getText());
	}

}
