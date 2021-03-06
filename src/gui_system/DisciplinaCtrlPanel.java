package gui_system;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import Utils.Functions;
import javax.swing.SwingConstants;

public class DisciplinaCtrlPanel extends JPanel {
	private JTextField txtDenumire, txtNumeScurt, 
		txtAn, txtOreCurs, txtOreLaborator, txtOreSeminar, 
		txtOreProiect, txtSemestru;
	
	private IntFilter oreFilter, anFilter, semestruFilter;

	/**
	 * Create the panel.
	 */
	public DisciplinaCtrlPanel() {
		setLayout(null);
		
		setSize(734, 119);
		
		oreFilter = new IntFilter();
		anFilter = new IntFilter();
		semestruFilter = new IntFilter();
		
		oreFilter.setMaxLength(3);
		anFilter.setMaxLength(1);
		semestruFilter.setMaxLength(1);
		
		JPanel panel = new JPanel();
		panel.setBounds(44, 31, 646, 56);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblDenumire = new JLabel("Denumire:");
		lblDenumire.setBounds(0, 3, 49, 14);
		panel.add(lblDenumire);
		
		txtDenumire = new JTextField();
		txtDenumire.setHorizontalAlignment(SwingConstants.CENTER);
		txtDenumire.setBounds(69, 0, 238, 20);
		panel.add(txtDenumire);
		txtDenumire.setColumns(10);
		
		JLabel lblNumeScurt = new JLabel("Nume scurt:");
		lblNumeScurt.setBounds(0, 39, 58, 14);
		panel.add(lblNumeScurt);
		
		txtNumeScurt = new JTextField();
		txtNumeScurt.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumeScurt.setBounds(69, 36, 238, 20);
		panel.add(txtNumeScurt);
		txtNumeScurt.setColumns(10);
		
		JLabel lblAn = new JLabel("An:");
		lblAn.setBounds(317, 3, 17, 14);
		panel.add(lblAn);
		
		txtAn = new JTextField();
		txtAn.setBounds(394, 0, 30, 20);
		panel.add(txtAn);
		txtAn.setHorizontalAlignment(SwingConstants.CENTER);
		txtAn.setColumns(10);
		
		JLabel lblOreCurs = new JLabel("Ore curs:");
		lblOreCurs.setBounds(434, 3, 46, 14);
		panel.add(lblOreCurs);
		
		txtOreCurs = new JTextField();
		txtOreCurs.setBounds(506, 0, 30, 20);
		panel.add(txtOreCurs);
		txtOreCurs.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreCurs.setColumns(10);
		
		JLabel lblOreLaborator = new JLabel("Ore laborator:");
		lblOreLaborator.setBounds(434, 39, 69, 14);
		panel.add(lblOreLaborator);
		
		txtOreLaborator = new JTextField();
		txtOreLaborator.setBounds(506, 36, 30, 20);
		panel.add(txtOreLaborator);
		txtOreLaborator.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreLaborator.setColumns(10);
		
		JLabel lblOreSeminar = new JLabel("Ore seminar:");
		lblOreSeminar.setBounds(546, 39, 62, 14);
		panel.add(lblOreSeminar);
		
		txtOreSeminar = new JTextField();
		txtOreSeminar.setBounds(614, 36, 30, 20);
		panel.add(txtOreSeminar);
		txtOreSeminar.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreSeminar.setColumns(10);
		
		JLabel lblOreProiect = new JLabel("Ore proiect:");
		lblOreProiect.setBounds(546, 3, 58, 14);
		panel.add(lblOreProiect);
		
		txtOreProiect = new JTextField();
		txtOreProiect.setBounds(614, 0, 30, 20);
		panel.add(txtOreProiect);
		txtOreProiect.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreProiect.setColumns(10);
		
		JLabel lblSemestru = new JLabel("Semestru:");
		lblSemestru.setBounds(317, 39, 49, 14);
		panel.add(lblSemestru);
		
		txtSemestru = new JTextField();
		txtSemestru.setHorizontalAlignment(SwingConstants.CENTER);
		txtSemestru.setBounds(394, 36, 30, 20);
		panel.add(txtSemestru);
		txtSemestru.setColumns(10);
		AbstractDocument oreProiectDoc = (AbstractDocument) txtOreProiect.getDocument();
		AbstractDocument oreSeminarDoc = (AbstractDocument) txtOreSeminar.getDocument();
		AbstractDocument oreLaboratorDoc = (AbstractDocument) txtOreLaborator.getDocument();
		
		AbstractDocument oreCursDoc = (AbstractDocument) txtOreCurs.getDocument();
		
		AbstractDocument anDoc = (AbstractDocument) txtAn.getDocument();
		AbstractDocument semestruDoc = (AbstractDocument) txtSemestru.getDocument();
		anDoc.setDocumentFilter(anFilter);
		semestruDoc.setDocumentFilter(semestruFilter);
		oreCursDoc.setDocumentFilter(oreFilter);
		oreSeminarDoc.setDocumentFilter(oreFilter);
		oreLaboratorDoc.setDocumentFilter(oreFilter);
		oreProiectDoc.setDocumentFilter(oreFilter);

	}
	
	public void setFields(String denumire, String an, String semestru, String oreCurs, String oreLaborator, 
			String oreSeminar, String oreProiect, String numeScurt) {
		txtDenumire.setText(denumire);
		txtAn.setText(String.valueOf(an));
		txtSemestru.setText(String.valueOf(semestru));
		txtOreCurs.setText(String.valueOf(oreCurs));
		txtOreLaborator.setText(String.valueOf(oreLaborator));
		txtOreSeminar.setText(String.valueOf(oreSeminar));
		txtOreProiect.setText(String.valueOf(oreProiect));
		txtNumeScurt.setText(numeScurt);
		
	}
	
	public Object[] getFields(boolean allowNull) {
		if(!allowNull) {
			if (txtDenumire.getText().length() == 0 || txtAn.getText().length() == 0 || txtOreCurs.getText().length() == 0
					|| txtOreLaborator.getText().length() == 0 || txtOreSeminar.getText().length() == 0
					|| txtOreProiect.getText().length() == 0 || txtNumeScurt.getText().length() == 0)
				return null;
		}
		
		Object result[] = { txtDenumire.getText(), Functions.tryParseInt(txtAn.getText()), Functions.tryParseInt(txtOreCurs.getText()),
				Functions.tryParseInt(txtOreLaborator.getText()), Functions.tryParseInt(txtOreSeminar.getText()),
				Functions.tryParseInt(txtOreProiect.getText()), txtNumeScurt.getText()
		};
		
		return result;
	}
	
	public String getDenumire() {
		return txtDenumire.getText();
	}

	public String getNumeScurt() {
		return txtNumeScurt.getText();
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

	public int getOreCurs() {
		if (txtOreCurs.getText().isEmpty())
			return -1;
		else
			return Integer.parseInt(txtOreCurs.getText());
	}

	public int getOreLaborator() {
		if (txtOreLaborator.getText().isEmpty())
			return -1;
		else
			return Integer.parseInt(txtOreLaborator.getText());
	}

	public int getOreSeminar() {
		if (txtOreSeminar.getText().isEmpty())
			return -1;
		else
			return Integer.parseInt(txtOreSeminar.getText());
	}

	public int getOreProiect() {
		if (txtOreProiect.getText().isEmpty())
			return -1;
		else
			return Integer.parseInt(txtOreProiect.getText());
	}

	public void resetFields() {
		txtDenumire.setText("");
		txtAn.setText("");
		txtSemestru.setText("");
		txtOreCurs.setText("");
		txtOreLaborator.setText("");
		txtOreSeminar.setText("");
		txtOreProiect.setText("");
		txtNumeScurt.setText("");
	}
}
