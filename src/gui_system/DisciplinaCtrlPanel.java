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
		txtOreProiect;
	
	private IntFilter oreFilter, anFilter;

	/**
	 * Create the panel.
	 */
	public DisciplinaCtrlPanel() {
		setLayout(null);
		
		setSize(584, 119);
		
		oreFilter = new IntFilter();
		anFilter = new IntFilter();
		
		oreFilter.setMaxLength(3);
		anFilter.setMaxLength(1);
		
		JLabel lblDenumire = new JLabel("Denumire:");
		lblDenumire.setBounds(32, 34, 49, 14);
		add(lblDenumire);
		
		txtDenumire = new JTextField();
		txtDenumire.setBounds(101, 31, 102, 20);
		add(txtDenumire);
		txtDenumire.setColumns(10);
		
		JLabel lblNumeScurt = new JLabel("Nume scurt:");
		lblNumeScurt.setBounds(32, 70, 58, 14);
		add(lblNumeScurt);
		
		txtNumeScurt = new JTextField();
		txtNumeScurt.setBounds(101, 67, 102, 20);
		add(txtNumeScurt);
		txtNumeScurt.setColumns(10);
		
		JLabel lblAn = new JLabel("An:");
		lblAn.setBounds(213, 34, 17, 14);
		add(lblAn);
		
		txtAn = new JTextField();
		txtAn.setHorizontalAlignment(SwingConstants.CENTER);
		txtAn.setBounds(290, 31, 30, 20);
		add(txtAn);
		txtAn.setColumns(10);
		
		JLabel lblOreCurs = new JLabel("Ore curs:");
		lblOreCurs.setBounds(330, 34, 46, 14);
		add(lblOreCurs);
		
		txtOreCurs = new JTextField();
		txtOreCurs.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreCurs.setBounds(402, 31, 30, 20);
		add(txtOreCurs);
		txtOreCurs.setColumns(10);
		
		JLabel lblOreLaborator = new JLabel("Ore laborator:");
		lblOreLaborator.setBounds(213, 70, 69, 14);
		add(lblOreLaborator);
		
		txtOreLaborator = new JTextField();
		txtOreLaborator.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreLaborator.setColumns(10);
		txtOreLaborator.setBounds(290, 67, 30, 20);
		add(txtOreLaborator);
		
		JLabel lblOreSeminar = new JLabel("Ore seminar:");
		lblOreSeminar.setBounds(330, 70, 62, 14);
		add(lblOreSeminar);
		
		txtOreSeminar = new JTextField();
		txtOreSeminar.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreSeminar.setColumns(10);
		txtOreSeminar.setBounds(402, 67, 30, 20);
		add(txtOreSeminar);
		
		JLabel lblOreProiect = new JLabel("Ore proiect:");
		lblOreProiect.setBounds(444, 52, 58, 14);
		add(lblOreProiect);
		
		txtOreProiect = new JTextField();
		txtOreProiect.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreProiect.setColumns(10);
		txtOreProiect.setBounds(512, 49, 30, 20);
		add(txtOreProiect);
		
		AbstractDocument anDoc = (AbstractDocument) txtAn.getDocument();
		anDoc.setDocumentFilter(anFilter);
		
		AbstractDocument oreCursDoc = (AbstractDocument) txtOreCurs.getDocument();
		oreCursDoc.setDocumentFilter(oreFilter);
		AbstractDocument oreSeminarDoc = (AbstractDocument) txtOreSeminar.getDocument();
		oreSeminarDoc.setDocumentFilter(oreFilter);
		AbstractDocument oreLaboratorDoc = (AbstractDocument) txtOreLaborator.getDocument();
		oreLaboratorDoc.setDocumentFilter(oreFilter);
		AbstractDocument oreProiectDoc = (AbstractDocument) txtOreProiect.getDocument();
		oreProiectDoc.setDocumentFilter(oreFilter);

	}
	
	public void setFields(String denumire, int an, int oreCurs, int oreLaborator, 
			int oreSeminar, int oreProiect, String numeScurt) {
		txtDenumire.setText(denumire);
		txtAn.setText(String.valueOf(an));
		txtOreCurs.setText(String.valueOf(oreCurs));
		txtOreLaborator.setText(String.valueOf(oreLaborator));
		txtOreSeminar.setText(String.valueOf(oreSeminar));
		txtOreProiect.setText(String.valueOf(oreProiect));
		txtNumeScurt.setText(String.valueOf(numeScurt));
		
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
	
	public void resetFields() {
		txtDenumire.setText("");
		txtAn.setText("");
		txtOreCurs.setText("");
		txtOreLaborator.setText("");
		txtOreSeminar.setText("");
		txtOreProiect.setText("");
		txtNumeScurt.setText("");
	}
	

}
