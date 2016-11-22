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
		
		setSize(734, 119);
		
		oreFilter = new IntFilter();
		anFilter = new IntFilter();
		
		oreFilter.setMaxLength(3);
		anFilter.setMaxLength(1);
		
		JPanel panel = new JPanel();
		panel.setBounds(44, 31, 646, 56);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblDenumire = new JLabel("Denumire:");
		lblDenumire.setBounds(0, 3, 49, 14);
		panel.add(lblDenumire);
		
		txtDenumire = new JTextField();
		txtDenumire.setBounds(69, 0, 238, 20);
		panel.add(txtDenumire);
		txtDenumire.setColumns(10);
		
		JLabel lblNumeScurt = new JLabel("Nume scurt:");
		lblNumeScurt.setBounds(0, 39, 58, 14);
		panel.add(lblNumeScurt);
		
		txtNumeScurt = new JTextField();
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
		lblOreLaborator.setBounds(317, 39, 69, 14);
		panel.add(lblOreLaborator);
		
		txtOreLaborator = new JTextField();
		txtOreLaborator.setBounds(394, 36, 30, 20);
		panel.add(txtOreLaborator);
		txtOreLaborator.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreLaborator.setColumns(10);
		
		JLabel lblOreSeminar = new JLabel("Ore seminar:");
		lblOreSeminar.setBounds(434, 39, 62, 14);
		panel.add(lblOreSeminar);
		
		txtOreSeminar = new JTextField();
		txtOreSeminar.setBounds(506, 36, 30, 20);
		panel.add(txtOreSeminar);
		txtOreSeminar.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreSeminar.setColumns(10);
		
		JLabel lblOreProiect = new JLabel("Ore proiect:");
		lblOreProiect.setBounds(548, 21, 58, 14);
		panel.add(lblOreProiect);
		
		txtOreProiect = new JTextField();
		txtOreProiect.setBounds(616, 18, 30, 20);
		panel.add(txtOreProiect);
		txtOreProiect.setHorizontalAlignment(SwingConstants.CENTER);
		txtOreProiect.setColumns(10);
		AbstractDocument oreProiectDoc = (AbstractDocument) txtOreProiect.getDocument();
		AbstractDocument oreSeminarDoc = (AbstractDocument) txtOreSeminar.getDocument();
		AbstractDocument oreLaboratorDoc = (AbstractDocument) txtOreLaborator.getDocument();
		
		AbstractDocument oreCursDoc = (AbstractDocument) txtOreCurs.getDocument();
		
		AbstractDocument anDoc = (AbstractDocument) txtAn.getDocument();
		anDoc.setDocumentFilter(anFilter);
		oreCursDoc.setDocumentFilter(oreFilter);
		oreSeminarDoc.setDocumentFilter(oreFilter);
		oreLaboratorDoc.setDocumentFilter(oreFilter);
		oreProiectDoc.setDocumentFilter(oreFilter);

	}
	
	public void setFields(String denumire, String an, String oreCurs, String oreLaborator, 
			String oreSeminar, String oreProiect, String numeScurt) {
		txtDenumire.setText(denumire);
		txtAn.setText(String.valueOf(an));
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
