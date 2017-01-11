package gui_system;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Services.GrupaService;
import Services.SubgrupaService;
import entity.Grupa;
import entity.Subgrupa;

public class RapStudentSearchCtrlPanel extends JPanel {
	private JTextField txtNume;
	private JComboBox<String> cbGrupa, cbSubgrupa;
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public RapStudentSearchCtrlPanel() {
		setLayout(null);
		setBounds(26, 278, 370, 53);
		
		panel = new JPanel();
		panel.setBounds(14, 2, 342, 48);
		add(panel);
		panel.setLayout(null);
		//setPreferredSize(new Dimension(397, 81));
		
		JLabel lblNume = new JLabel("Nume");
		lblNume.setBounds(0, 3, 29, 14);
		panel.add(lblNume);
		
		txtNume = new JTextField();
		txtNume.setBounds(39, 0, 303, 20);
		panel.add(txtNume);
		txtNume.setHorizontalAlignment(SwingConstants.CENTER);
		txtNume.setColumns(10);
		
		JLabel lblGrupa = new JLabel("Grupa");
		lblGrupa.setBounds(0, 31, 29, 14);
		panel.add(lblGrupa);
		
		cbGrupa = new JComboBox<String>();
		cbGrupa.setBounds(39, 28, 120, 20);
		panel.add(cbGrupa);
		
		JLabel lblSubgrupa = new JLabel("Subgrupa");
		lblSubgrupa.setBounds(166, 31, 46, 14);
		panel.add(lblSubgrupa);
		
		cbSubgrupa = new JComboBox<String>();
		cbSubgrupa.setBounds(222, 28, 120, 20);
		panel.add(cbSubgrupa);
	}
	
	public String getNume() {
		return txtNume.getText();
	}
	
	public String getGrupaText() {
		return (String)cbGrupa.getSelectedItem();
	}
	
	public String getSubgrupaText() {
		return (String)cbSubgrupa.getSelectedItem();
	}
	
	public void initLoadGrupa() {
		cbGrupa.addItem("");
		List<Grupa> list = GrupaService.getAllFromGrupa();
		
		for (Grupa grupa : list)
			cbGrupa.addItem(grupa.getNume());
	}
	
	public void initLoadSubgrupa() {
		cbSubgrupa.addItem("");
		List<Subgrupa> list = SubgrupaService.getAllFromSubgrupa();
		
		for (Subgrupa subgrupa : list)
			cbSubgrupa.addItem(subgrupa.getNume());
	}

}
