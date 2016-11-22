package gui_system;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import Utils.Functions;

public class DisciplinaCtrlPanel extends JPanel {
	private JTextField txtDenumire;
	private JTextField txtNumeScurt;
	private JTextField txtAn;
	private JTextField txtOreCurs;
	private JTextField txtOreLaborator;
	private JTextField txtOreSeminar;
	private JTextField txtOreProiect;

	/**
	 * Create the panel.
	 */
	public DisciplinaCtrlPanel() {
		setLayout(null);
		
		setSize(584, 119);
		
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
		txtAn.setBounds(290, 31, 30, 20);
		add(txtAn);
		txtAn.setColumns(10);
		
		JLabel lblOreCurs = new JLabel("Ore curs:");
		lblOreCurs.setBounds(330, 34, 46, 14);
		add(lblOreCurs);
		
		txtOreCurs = new JTextField();
		txtOreCurs.setBounds(402, 31, 30, 20);
		add(txtOreCurs);
		txtOreCurs.setColumns(10);
		
		JLabel lblOreLaborator = new JLabel("Ore laborator:");
		lblOreLaborator.setBounds(213, 70, 69, 14);
		add(lblOreLaborator);
		
		txtOreLaborator = new JTextField();
		txtOreLaborator.setColumns(10);
		txtOreLaborator.setBounds(290, 67, 30, 20);
		add(txtOreLaborator);
		
		JLabel lblOreSeminar = new JLabel("Ore seminar:");
		lblOreSeminar.setBounds(330, 70, 62, 14);
		add(lblOreSeminar);
		
		txtOreSeminar = new JTextField();
		txtOreSeminar.setColumns(10);
		txtOreSeminar.setBounds(402, 67, 30, 20);
		add(txtOreSeminar);
		
		JLabel lblOreProiect = new JLabel("Ore proiect:");
		lblOreProiect.setBounds(444, 52, 58, 14);
		add(lblOreProiect);
		
		txtOreProiect = new JTextField();
		txtOreProiect.setColumns(10);
		txtOreProiect.setBounds(512, 49, 30, 20);
		add(txtOreProiect);

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
	

}
